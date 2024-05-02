package hope;

import hope.connection.RedisCommandLine;

public class RedisKeyStatisticsMain {
    public static void main(String[] args) {
        RedisCommandLine.connect(args);
    }
}