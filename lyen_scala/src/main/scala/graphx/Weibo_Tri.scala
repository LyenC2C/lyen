//package graphx
//
//import org.apache.spark.graphx.PartitionStrategy
//import org.apache.spark.graphx.GraphLoader
//import org.apache.spark.SparkConf
//import org.apache.spark.SparkContext
//
///**
//  * Created by lyen on 16-9-5.
//  */
//object Weibo_Tri extends App {
//  val conf = new SparkConf().setAppName("graphx").setMaster("local[*]")
//  val sc = new SparkContext(conf)
//  val stime = System.currentTimeMillis();
//
//  def method = {
//    /*
//   * 从一些文本文件中构建一个图,限制这个图包含重要的关系和用户,并且在子图上运行TriangleCount,最后返回与top用户相关的属性.
//   */
//    //解析用户关系文件并生成图
//    val userReGraph = GraphLoader.edgeListFile(sc, "/home/lyen/userrelation.txt", true).partitionBy(PartitionStrategy.RandomVertexCut).cache()
//    //加载用户数据并解析为用户id和用户名属性列表的元组
//    val usersData = sc.textFile("/home/lyen/user.txt").cache()
//    val usersDataFormat = usersData.map { x => val tmp = x.split("\t", 2); (tmp(0).toLong, (tmp(1))) }.cache
//    //添加用户属性
//    val graph = userReGraph.outerJoinVertices(usersDataFormat) {
//      case (id, sds, Some(userName)) => userName
//    }.cache()
//    //生成只包含用户id和用户名的子图
//    val subgraph = graph.subgraph(vpred = (vid, attr) => attr.size > 0).cache()
//    //使用TriangleCount算法计算每个用户id的三角计数值
//    val pagerankGraph = subgraph.triangleCount().vertices.cache()
//    //将三角计数值加入用户图的属性
//    val userInfoWithPageRank = subgraph.outerJoinVertices(pagerankGraph) {
//      case (id, userName, Some(pr)) => (pr, userName)
//      case (id, userName, None) => (0.0, userName)
//    }.cache()
//
//    //获得排名前20的用户的id,三角计数值,用户名
//    val result = userInfoWithPageRank.vertices.top(20)(Ordering.by(_._2._1.asInstanceOf[Int]))
//    //格式化数据:去掉用户id保留用户名和三角计数值
//    result.map(x => (x._2._2, x._2._1)).foreach(println)
//  }
//
//  method
//  val etime = System.currentTimeMillis();
//  val cost = etime - stime;
//  println(cost)
//
//}
