package datatframe

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * Created by lyen on 17-2-4.
  */
object Query extends App {
  val conf = new SparkConf().setAppName("query").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  val jdbcDF = sqlContext.read.format("jdbc").options(Map("url" -> "jdbc:mysql://rm-2ze2x1h29sd9was49o.mysql.rds.aliyuncs.com:3306/db_bg_statits?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;failOverReadOnly=false",
    "driver" -> "com.mysql.jdbc.Driver",
    "dbtable" -> "mac_info",
    "user" -> "jfwx", "password" -> "Jfwx608aviup")).load()
  jdbcDF.show()
}
