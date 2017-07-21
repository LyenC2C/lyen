package redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * redis支持数据类型，
 */
public class Redis_Client {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("Server is running: " + jedis.ping());
        System.out.println(jedis.keys("*"));
        /**
         * 字符串
         */
        jedis.set("lyen", "22");
        String str = jedis.get("lyen");
        /**
         * 列表
         */
        jedis.lpush("myList","Lyen","HZT","What");
        jedis.rpush("myList","good");
        jedis.lpush("myList","oo");
        //取出所有值
        List<String> s =  jedis.lrange("myList",0,4);
        for(String ss : s){
            System.out.print(ss+"  ");
        }


    }

}
