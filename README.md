# Redis Keys Statistics
## Overview
`redis-keys-statistics` is a Java tool, designed for analyzing and reporting key usage statistics in Redis databases with exceptional speed and efficiency. By leveraging Lua scripting to reduce network I/O, it dramatically outperforms other open-source tools — tasks that typically take hours are completed in just minutes.

### Features
High Performance: Utilizes Lua scripting to minimize network I/O, delivering results significantly faster than traditional methods. Where some tools might take hours, redis-keys-statistics can complete the same task in a fraction of the time.
Efficient Key Scanning: Uses the Redis SCAN command for batch scanning of keys, further optimizing performance.
Memory Usage Statistics: Reports memory usage for keys in various units (B, KB, MB, GB).
Top N Largest Keys: Quickly identifies the largest keys for memory optimization.
Key Count by Type: Provides a breakdown of keys by type for better data structure insights.
Prefix-Based Analysis: Analyzes keys based on prefixes to understand namespace usage.
Cluster Support: Compatible with Redis clusters, including replica-only analysis.
Readable Output: Formatted and easy-to-read statistical tables using prettytable.

<br>

## Example Output

### Top 20 Largest Keys in Redis

```
+-------------------+------+---------+------------+-------+
| Key               | Type | Size    | Size Ratio |  TTL  |
+-------------------+------+---------+------------+-------+
| user_sessions:123 | hash | 1.2 MB  | 150% ↑     |  360  |
| cache:page:001    | zset | 900 KB  | 120% ↑     |  -1   |
| config:app        | hash | 800 KB  | 100% ↑     | 86400 |
| queue:jobs        | list | 600 KB  | 80% ↑      |  -1   |
| temp:data:456     | set  | 500 KB  | 60% ↑      | 1800  |
| ...               | ...  | ...     | ...        |  ...  |
+-------------------+------+---------+------------+-------+
```

### Key Count by Type
```
+--------+-------+
|  Type  | Count |
+--------+-------+
| hash   | 250   |
| zset   | 150   |
| list   | 100   |
| set    | 75    |
| string | 200   |
+--------+-------+
```

### Detailed Prefix Statistic

```
+-------------+-------+--------------+---------+-----------------+
| Prefix Name | Count | Average Size | Max TTL | Types           |
+-------------+-------+--------------+---------+-----------------+
| user        | 100   | 200 KB       | 3600    | - Type: hash    |
|             |       |              |         |   Count: 50     |
|             |       |              |         | - Type: string  |
|             |       |              |         |   Count: 50     |
| cache       | 80    | 150 KB       | -1      | - Type: zset    |
|             |       |              |         |   Count: 80     |
| config      | 20    | 100 KB       | 86400   | - Type: hash    |
|             |       |              |         |   Count: 20     |
| temp        | 150   | 50 KB        | 1800    | - Type: set     |
|             |       |              |         |   Count: 100    |
|             |       |              |         | - Type: list    |
|             |       |              |         |   Count: 50     |
+-------------+-------+--------------+---------+-----------------+
```

<br>

## Contributing
Contributions are welcome..!