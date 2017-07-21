//package graphx
//
//import org.apache.spark.graphx.GraphLoader
//import org.apache.spark.{Logging, SparkConf, SparkContext}
//
///**
//  * Created by lyen on 16-9-5.
//  */
//object Weibo_pagerank extends App with Logging {
//  val conf = new SparkConf().setAppName("graphx").setMaster("local[*]")
//  val sc = new SparkContext(conf)
//  val stime = System.currentTimeMillis();
//  def method = {
//    /*
//   * 从一些文本文件中构建一个图,限制这个图包含重要的关系和用户,并且在子图上运行page-rank,最后返回与top用户相关的属性.
//   */
//    //解析用户关系文件并生成图
//    val userReGraph = GraphLoader.edgeListFile(sc, "/home/lyen/userrelation.txt")
//    //    val userReGraph = GraphLoader.edgeListFile(sc, "/home/lyen/data/weibo/userrelation.txt",true).partitionBy(PartitionStrategy.RandomVertexCut).cache()
//    //加载用户数据并解析为用户id和用户名属性列表的元组
//    val usersData = sc.textFile("/home/lyen/user3.txt")
//    val usersDataFormat = usersData.map { x => val tmp = x.split("\t", 2); (tmp(0).toLong, (tmp(1))) }.cache
//    //添加用户属性
//    val graph = userReGraph.outerJoinVertices(usersDataFormat) {
//      case (id, sds, Some(userName)) => userName
//    }
//    //生成只包含用户id和用户名的子图
//    val subgraph = graph.subgraph(vpred = (vid, attr) => attr.size > 0)
//    //获取图的入度和出度(入度表示用户的粉丝人数,出度表示用户的关注人数)
//    val in = graph.inDegrees
//    val out = graph.outDegrees
//    val inAndOut = in.join(out)
//    //使用PageRank算法计算每个用户id的rank值
//    //    val pagerankGraph = subgraph.pageRank(0.001).vertices
//    val pagerankGraph = subgraph.pageRank(0.001).vertices
//    //将rank值加入用户图的属性
//    val userInfoWithPageRank = subgraph.outerJoinVertices(pagerankGraph) {
//      case (id, userName, Some(pr)) => (pr, userName)
//      case (id, userName, None) => (0.0, userName)
//    }.cache()
//    //将入度和出度加入用户图的属性中
//    val userInfoWithPageRankAndInOut = userInfoWithPageRank.outerJoinVertices(inAndOut) {
//      case (id, attr, Some(inout)) => (attr._1, attr._2, inout._1, inout._2)
//      case (id, attr, None) => (attr._1, attr._2, 0, 0)
//    }.cache()
//
//    //获得排名前20的用户的id,rank值,用户名,以及其用户粉丝人数和关注人数
//    val result = userInfoWithPageRankAndInOut.vertices.top(20)(Ordering.by(_._2._1))
//    //    val result = userInfoWithPageRankAndInOut.vertices.top(20)(Ordering.by(_._2._1.asInstanceOf[Int])).foreach(println)
//    //格式化数据:去掉用户id保留用户名,以及其用户粉丝人数,关注人数和rank值
//    result.map(x => (x._2._2, x._2._3, x._2._4, x._2._1)).foreach(println)
//  }
//
//  method
//  val etime = System.currentTimeMillis();
//  val cost = etime - stime;
//  println(cost)
//}
