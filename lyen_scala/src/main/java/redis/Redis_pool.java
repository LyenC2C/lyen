package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by lyen on 16-9-9.
 */
public class Redis_pool {
    private static JedisPool jedisPool;
    private static JedisPoolConfig config;
    private static Jedis jedis;

    public static JedisPool getJedisPool() {
        config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
        config.setMaxWaitMillis(100*1000);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config,"localhost",6379);
        return jedisPool;

    }

    public static void main(String[] args) {
        JedisPool jpool = getJedisPool();
        jedis = jpool.getResource();
        Set<String> keys = jedis.keys("*");
        for(String str:keys){
            System.out.print(str + "  ");
        }
    }
}
