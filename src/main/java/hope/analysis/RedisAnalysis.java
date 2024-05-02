package hope.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class RedisAnalysis {

    public static void updateTopKHeap(Object[] item, PriorityQueue<Object[]> minHeap, int heapSize) {
        minHeap.offer(item);
        if (minHeap.size() > heapSize) {
            minHeap.poll();
        }
    }

    public static void updateStatistics(Object[] item, Map<String, Map<String, Object>> prefixStatisticsMap) {
        byte[] key = (byte[]) item[0];
        byte[] keyType = (byte[]) item[1];
        int memory = (int) item[2];
        long ttl = (long) item[3];

        if (contains(key, (byte) ':')) {
            byte[] keyPrefix = getKeyPrefix(key);
            String keyPrefixStr = new String(keyPrefix);
            if (!prefixStatisticsMap.containsKey(keyPrefixStr)) {
                Map<String, Object> stats = new HashMap<>();
                stats.put("count", 0);
                stats.put("total_size", 0);
                stats.put("max_ttl", -1L);
                stats.put("type_count", new HashMap<String, Integer>());
                prefixStatisticsMap.put(keyPrefixStr, stats);
            }
            Map<String, Object> stats = prefixStatisticsMap.get(keyPrefixStr);
            stats.put("count", (int) stats.get("count") + 1);
            stats.put("total_size", (int) stats.get("total_size") + memory);
            stats.put("max_ttl", Math.max((long) stats.get("max_ttl"), ttl));

            Map<String, Integer> typeCount = (Map<String, Integer>) stats.get("type_count");
            String keyTypeStr = new String(keyType);

            if (!typeCount.containsKey(keyTypeStr)) {
                typeCount.put(keyTypeStr, 0);
            }

            typeCount.put(keyTypeStr, typeCount.get(keyTypeStr) + 1);
        }
    }

    private static String formatMemorySize(Integer size) {
        if (size < 1024) {
            return String.format("%.2f B", (double) size);
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", (double) size / 1024);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (double) size / (1024 * 1024));
        } else {
            return String.format("%.2f GB", (double) size / (1024 * 1024 * 1024));
        }
    }

    private static boolean contains(byte[] bytes, byte b) {
        for (byte b1 : bytes) {
            if (b1 == b) {
                return true;
            }
        }
        return false;
    }

    private static byte[] getKeyPrefix(byte[] key) {
        int index = -1;
        for (int i = 0; i < key.length; i++) {
            if (key[i] == (byte) ':') {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return key;
        } else {
            byte[] prefix = new byte[index];
            System.arraycopy(key, 0, prefix, 0, index);
            return prefix;
        }
    }
}
