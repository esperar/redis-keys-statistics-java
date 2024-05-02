package hope.connection;

import org.apache.commons.cli.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCommandLine {

    static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    static JedisPool pool = null;

    public static void connect(String[] args) {
        Options options = new Options();
        Option hostOption = new Option("h", "host", true, "Redis Server Host");
        Option portOption = new Option("p", "port", true, "Redis Server Port");
        Option passwordOption = new Option("P", "password", true, "Redis Server Password");

        options.addOption(hostOption);
        options.addOption(portOption);
        options.addOption(passwordOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Invalid Args..");
            printUsage();
        }

        String host = cmd.getOptionValue("host");
        int port = Integer.parseInt(cmd.getOptionValue("port"));
        String password = cmd.getOptionValue("password");

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

    private static void printUsage() {
        System.out.println(": rks --host <REDIS_HOST> --port <REDIS_PORT> [--password <REDIS_PASSWORD>]");
    }
}
