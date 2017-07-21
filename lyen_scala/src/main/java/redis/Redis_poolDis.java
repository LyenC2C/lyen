package redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyen on 16-9-9.
 */
public class Redis_poolDis {

    private static ShardedJedisPool pool;
    private static JedisPoolConfig config;
    private static List<JedisShardInfo> listShardInfo;
    private static String host_A = "localhost";
    private static int port_A = 6379;
    private static String host_B = "localhost";
    private static int port_B = 6379;
    private static int index = 1;

    static {
        config = new JedisPoolConfig();
        config.setMaxIdle(1000 * 60);//对象最大空闲时间
        config.setMaxWaitMillis(1000 * 10);//获取对象时最大等待时间
        config.setTestOnBorrow(true);
        listShardInfo = new ArrayList<JedisShardInfo>(2);
        JedisShardInfo shardInfo_A = new JedisShardInfo(host_A, port_A);
        shardInfo_A.setPassword("redis.lyen");
        JedisShardInfo shardInfo_B = new JedisShardInfo(host_B, port_B);
        shardInfo_B.setPassword("redis.lyen");
        listShardInfo.add(shardInfo_A);
        listShardInfo.add(shardInfo_B);
        //传入连接池配置、分布式redis服务器主机信息、分片规则（存储到哪台redis服务器）
        pool = new ShardedJedisPool(config, listShardInfo, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    public static String generateKey() {

        return String.valueOf(Thread.currentThread().getId()) + "_" + (index++);

    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String key = generateKey();
            ShardedJedis jds = null;
            try {
                jds = pool.getResource();
                System.out.println(key + ":" + jds.getShard(key).getClient().getHost());
                System.out.println(jds.set(key, "1111"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
