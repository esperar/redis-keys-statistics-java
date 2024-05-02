package hope.connection;

import org.apache.commons.cli.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnector {

    static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    static JedisPool pool = null;

    public static void connect(String host, int port, String password) {

        pool = new JedisPool(jedisPoolConfig, host, port, 1000);
        Jedis jedis = null;

        if(password != null && !password.isEmpty()) {
            jedis = pool.getResource();
            jedis.auth(password);
        }

        if(isConnectSuccess(jedis.ping())) {
            System.out.println("Success to connect Redis");
        } else {
            System.out.println("Failed to connect Redis");
        }
    }

    private static boolean isConnectSuccess(String ping) {
        return ping.equals("PONG");
    }
}
