/**
  * Created by lyen on 15-7-23.
  */
// To make some of the examples work we will also need RDD


import org.apache.spark.graphx._
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.rdd.RDD

object LearnGraphx_1 {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("graphx").setMaster("local")
    val sc = new SparkContext(conf)
    val users: RDD[(VertexId, (String, Double))] = sc.parallelize(Array(
      (3L, ("rxin", 20D)),
      (7L, ("jgonzal", 19D)),
      (5L, ("franklin", 25D)),
      (2L, ("istoica", 17D)),
      (4L, ("peter", 40D))))

    val edges: RDD[Edge[String]] = sc.parallelize(Array(Edge(
      3L, 7L, "colleague"),
      Edge(5L, 3L, "advisor"),
      Edge(2L, 5L, "colleague"),
      Edge(5L, 7L, "pi"),
      Edge(4L, 2L, "student"),
      Edge(5L, 4L, "colleague")))
    val defaulUsers = ("user", 2.0)
    val graph = Graph(users, edges, defaulUsers)

    //求所有以5为源顶点的相邻点的平均年龄 ps:此场景可用于一个微博的博主的粉丝的平均年龄   方法1
    //graph.collectNeighbors(EdgeDirection.Out).mapValues(i=>i.map(_._2._2).sum/i.size).foreach(println)
    //求所有以5为源顶点的相邻点的平均年龄 ps:此场景可用于一个微博的博主的粉丝的平均年龄   方法2
    graph.aggregateMessages[(Double, Int)](sendMsg => {
      if (sendMsg.srcId == 5L) sendMsg.sendToSrc(sendMsg.dstAttr._2, 1)
    }, (a, b) => (a._1 + b._1, a._2 + b._2)).mapValues(a => a._1 / a._2) //.foreach(println)
    graph.pregel[Double](2.0)(
      (id, attr, newattr) => (attr._1, attr._2 + newattr),
      sendMsg => {
        if (sendMsg.srcId == 5L)
          Iterator((5L, sendMsg.dstAttr._2))
        else Iterator.empty
      },
      (a, b) => (a + b)
    ).vertices.foreach(println)




    ///*    filter
    //     graph.vertices.filter({case(id,(name,pos)) => pos == "postdoc"}).foreach(println)
    //     graph.vertices.filter(ver=>ver._2._2 == "postdoc").collect().foreach(println)
    //    graph.edges.filter(e=>e.srcId>e.dstId).foreach(println)
    //    graph.edges.filter({case Edge(srcId,dstId,property) => srcId>dstId}).foreach(println)
    // */
    /*   输出图
    graph.vertices.collect()foreach(println)
    graph.edges.collect().foreach(println)
    //triplets
    graph.triplets.collect().foreach(println)
    graph.triplets.collect.foreach(a=>println(a.srcAttr._1+" is "+a.dstAttr._1+"'"+a.attr))
    */

    //  属性操作  （mapVertices、 mapEdges 和 mapTriplets,即对顶点进行 map、对边进行 map、对 Triplets进行map操作）
    /*
    graph.mapEdges(ed=>ed.attr + "  Lyen").triplets.collect.foreach(a=>println(a.srcAttr._1+" is "+a.dstAttr._1+"'"+a.attr))
    graph.mapVertices((id,d)=>(d._1 +"kghdhg",d._2)).vertices.collect().foreach(println)
    graph.mapVertices((id,d)=>(id,d)).vertices.collect().foreach(println)
    */
    //Degerees inDegrees,outDegerees
    /*
    graph.inDegrees.foreach(println)
    graph.outDegrees.foreach(println)
    graph.degrees.foreach(println)
    */

    /*结构性操作 reverse subgraph mask groupEdges
def reverse: Graph[VD, ED]操作返回一个新的图，这个图的边的方向都是反转的
def subgraph(epred: EdgeTriplet[VD,ED] => Boolean,vpred: (VertexId, VD) => Boolean): Graph[VD, ED]
def mask[VD2, ED2](other: Graph[VD2, ED2]): Graph[VD, ED]
def groupEdges(merge: (ED, ED) => ED): Graph[VD,ED]
*/
    /*
    //subgraph操作 利用顶点和边的谓词（predicates），返回的图仅仅包含满足顶点谓词的顶点、满足边谓词的边以及满足顶点谓词的连接顶点（connect vertices）
     graph.triplets.map(triplet => triplet.srcAttr._1 + " is the " + triplet.attr + " of " + triplet.dstAttr._1).
      collect.foreach(println(_))
    // Remove missing vertices as well as the edges to connected to them
    val validGraph = graph.subgraph(vpred = (id, attr) => attr._2 != "Missing")//vpred限定名不可更改，subgraph固定语法
//    // The valid subgraph will disconnect users 4 and 5 by removing user 0
    validGraph.vertices.collect.foreach(println(_))
    validGraph.triplets.map(
      triplet => triplet.srcAttr._1 + " is the " + triplet.attr + " of " +
        triplet.dstAttr._1
    ).collect.foreach(println(_))
     */

    /*joinVertices测试
val test01:RDD[(VertexId,(String,String))] =  sc.parallelize(Array(
(3L, ("Lyen", "student")),
(7L, ("lel", "postdoc")),
(5L, ("len", "prof")),
(2L, ("sum", "prof")),
(4L, ("peter", "student"))))
val test02:RDD[(VertexId,(String,String))] =  sc.parallelize(Array(
(3L, ("Lyen", "student")),
(7L, ("lel", "postdoc")),
(5L, ("len", "prof")),
(2L, ("sum", "prof")),
(2L, ("sum", "prof")),
(4L, ("peter", "student"))))

val graph01 = Graph(test01,edges)
//第一种join方法outerJoin   (常用方法)   返回类型可以不同
//提高效率......如果即将join的另一张表没有重复的id则不需要这句，下边直接join（test02）
val uniqueCosts = graph01.vertices.aggregateUsingIndex[(String,String)](test02,(a,b)=>(a._1+b._1,a._2+b._2))
//
graph01.outerJoinVertices(uniqueCosts){
(_, oldAttr, outDegOpt) => outDegOpt match {
case Some(outDg) => (oldAttr._1 + outDg._1,oldAttr._2 + outDg._2)
case None => 0 // No outDegree means zero outDegree
}
}.vertices.collect().foreach(println)
// 第二种join方法 区别在与第一种返回的类型必须和以前的类型相同
/*
val uniqueCosts = graph01.vertices.aggregateUsingIndex[(String,String)](test02,(a,b)=>(a._1+b._1,a._2+b._2))
val tmp =  graph01.joinVertices(uniqueCosts)((id,oldAttr,newAtrr)=>(oldAttr._1 + newAtrr._1,oldAttr._2 + newAtrr._2))
tmp.vertices.collect().foreach(println)
*/
  joinVertices测试 */

  }
}