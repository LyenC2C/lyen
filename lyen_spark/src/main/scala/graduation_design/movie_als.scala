package graduation_design

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 17-3-27.
  */
object movie_als {

  val conf = new SparkConf().setAppName("movie_als").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val rawData = sc.textFile("/home/lyen/graduation_design/ml-100k/u.data")

  /**
    * 用户物品推荐
    *
    */
  val rawRatings = rawData.map(_.split("\t").take(3))
  //rows:10w record
  //print(rawRatings.count())
  import org.apache.spark.mllib.recommendation.ALS
  import org.apache.spark.mllib.recommendation.Rating
  import org.apache.spark.mllib.recommendation.MatrixFactorizationModel

  val ratings = rawRatings.map { case Array(user, movie, rating) => Rating(user.toInt, movie.toInt, rating.toDouble) }
  val model = ALS.train(ratings, 50, 10, 0.01)
  //  print(model.userFeatures.count()) => 943
  //  print(model.productFeatures.count()) => 1682
  val predictedRating = model.predict(789, 123)
  val userId = 789
  val k = 10
  val topKrecs = model.recommendProducts(userId, k)
  //  println(topKrecs.mkString("\n"))
  val movies = sc.textFile("/home/lyen/graduation_design/ml-100k/u.item")
  val titles = movies.map(_.split("\\|").take(2)).map { case Array(movieId, title) => (movieId.toInt, title) }.collectAsMap()
  val movieForUser = ratings.keyBy(_.user).lookup(789)
  //println(movieForUser.size)
  //  movieForUser.sortBy(-_.rating).take(10).map(rating => (titles(rating.product), rating.rating)).foreach(println)
  //  movieForUser.map(rating => (titles(rating.product), rating.rating)).foreach(println)
  //  topKrecs.map(rating => (titles(rating.product), rating.rating)).foreach(println)
  /**
    * 物品推荐
    */

  import org.jblas.DoubleMatrix

  def consineSimilarity(v1: DoubleMatrix, v2: DoubleMatrix): Double = {
    v1.dot(v2) / (v1.norm2() * v2.norm2())
  }

  /*
     val itemId = 567
     val itemFactor = model.productFeatures.lookup(itemId).head
     val itemVector = new DoubleMatrix(itemFactor)
     print (consineSimilarity(itemVector,itemVector))
   */
  val itemId = 567
  val itemFactor = model.productFeatures.lookup(itemId).head
  val itemVector = new DoubleMatrix(itemFactor)
  val item_sims = model.productFeatures.map { case (id, factor) =>
    val factorVector = new DoubleMatrix(factor)
    val sim = consineSimilarity(factorVector, itemVector)
    (id, sim)
  }
  val item_sortedSims = item_sims.top(k)(Ordering.by[(Int, Double), Double] { case (id, similarity) => similarity })
  //  println(sortedSims.take(10).mkString("\n"))
  //  println(titles(itemId))
  val item_sortedSims2 = item_sims.top(k + 1)(Ordering.by[(Int, Double), Double] { case (id, similsrity) => similsrity })
  //  println(sortedSims2.slice(1,11).map{case (id,sim) => (titles(id),sim)}.mkString("\n"))

  /*
    用户推荐
   */
  val userFactor = model.userFeatures.lookup(userId).head
  val userVector = new DoubleMatrix(userFactor)
  val user_sims = model.userFeatures.map { case (id, factor) =>
    val factorVector = new DoubleMatrix(factor)
    val sim = consineSimilarity(factorVector, userVector)
    (id, sim)
  }
  val user_sortedSims = user_sims.top(k)(Ordering.by[(Int, Double), Double] { case (id, similarity) => similarity })
  //  println(sortedSims.take(10).mkString("\n"))
  //  println(titles(itemId))
  val user_sortedSims2 = user_sims.top(k + 1)(Ordering.by[(Int, Double), Double] { case (id, similsrity) => similsrity })
  //  println(user_sortedSims2.slice(1, 11).map { case (id, sim) => (id, sim) }.mkString("\n"))
  /*
  val actualRating = movieForUser.take(1)(0)
  val predictedRating_ = model.predict(789,actualRating.product)
  val squaredError = Math.pow(predictedRating_ - actualRating.rating,2.0)
  */

  /*
   MSE
   */
  val userProducts = ratings.map { case Rating(user, product, rating) => (user, product) }
  val predictions = model.predict(userProducts).map { case Rating(user, product, rating) => ((user, product), rating) }
  val ratingAndPredictions = ratings.map {
    case Rating(user, product, rating) => ((user, product), rating)
  }.join(predictions)
  val MSE = ratingAndPredictions.map {
    case ((user, product), (actual, predicted)) => math.pow((actual - predicted), 2)
  }.reduce(_ + _) / ratingAndPredictions.count

  //均方差
  //  println("Means Squared Error =" + MSE)

  val RMSE = math.sqrt(MSE)
  //均方根误差
  //  println("Root Means Squared Error = " + RMSE)

  //APK
  def avgPrecisionK(actual: Seq[Int], predicted: Seq[Int], k: Int): Double = {
    val predK = predicted.take(k)
    var score = 0.0
    var numHits = 0.0
    for ((p, i) <- predK.zipWithIndex) {
      if (actual.contains(p)) {
        numHits += 1.0
        score += numHits / (i.toDouble + 1.0)
      }
    }
    if (actual.isEmpty) {
      1.0
    } else {
      score / math.min(actual.size, k).toDouble
    }
  }

  val actualMovies = movieForUser.map(_.product)
  val predictedMovies = topKrecs.map(_.product)
  val apk10 = avgPrecisionK(actualMovies, predictedMovies, 10)
  print(apk10)
  //MAPK
  val itemFactors = model.productFeatures.map { case (id, factor) => factor }.collect()
  val itemMatrix = new DoubleMatrix(itemFactors)
  println(itemMatrix.rows, itemMatrix.columns)
  val imBrocast = sc.broadcast(itemMatrix)
  val allRecods = model.userFeatures.map { case (userId, array) =>
    val userVector = new DoubleMatrix(array)
    val scores = imBrocast.value.mmul(userVector)
    val sortedWithId = scores.data.zipWithIndex.sortBy(-_._1)
    val recommandedIds = sortedWithId.map(_._2 + 1).toSeq
    (userId, recommandedIds)
  }
  val userMovies = ratings.map { case Rating(user, product, rating) => (user, product) }.groupBy(_._1)
  val mapk = allRecods.join(userMovies).map { case (userId, (predicted, actualWithIds)) =>
    val actual = actualWithIds.map(_._2).toSeq
    avgPrecisionK(actual, predicted, k)
  }.reduce(_ + _) / allRecods.count
  println("Means Average Precision as k = " + mapk)

  /**
    * MLLIB 内置的评估函数
    */
  //1.RMSE && MSE
  import org.apache.spark.mllib.evaluation.RegressionMetrics

  val predictedAndTrue = ratingAndPredictions.map { case ((ser, product), (predicted, actual)) => (predicted, actual) }
  val regessionMetrics = new RegressionMetrics(predictedAndTrue)
  println("Mean Squared Error = " + regessionMetrics.meanSquaredError)
  println("Root Mean Squared Error = " + regessionMetrics.rootMeanSquaredError)
  //2.MAP
  import org.apache.spark.mllib.evaluation.RankingMetrics

  val predictedAndTrueForRanking = allRecods.join(userMovies).map { case (userid, (predicted, actualWithIds)) =>
    val actual = actualWithIds.map(_._2)
    (predicted.toArray, actual.toArray)
  }
  val rankingMetrics = new RankingMetrics(predictedAndTrueForRanking)
  println("Mean Average Precision = " + rankingMetrics.meanAveragePrecision)

  val mapk2000 = allRecods.join(userMovies).map { case (userId, (predicted, actualWithIds)) =>
    val actual = actualWithIds.map(_._2).toSeq
    avgPrecisionK(actual, predicted, k)
  }.reduce(_ + _) / allRecods.count
  println("Means Average Precision as k = 2000 " + mapk2000)

}
