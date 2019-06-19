package com.example.demo.db;

import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RocksDBSample {
    static {
        RocksDB.loadLibrary();
    }

    public static void main(final String[] args) {
        final String db_path = "/wuwf/rocksdb";

        System.out.println("RocksDBSample");
        final Options options = new Options();
        final Filter bloomFilter = new BloomFilter(10);

        final ReadOptions readOptions = new ReadOptions()
                .setFillCache(false);

        final Statistics stats = new Statistics();
        final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10);
        options.setCreateIfMissing(true)
                .setStatistics(stats)
                .setWriteBufferSize(8 * SizeUnit.KB)
                .setMaxWriteBufferNumber(3)
                .setMaxBackgroundCompactions(10)
                .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                .setCompactionStyle(CompactionStyle.UNIVERSAL);
        //自定义memtable和表格式
        //SkipList —— 默认的memtable
        //HashSkipList —— 只能与prefix_extractor工作。他把key放入基于key前缀的桶中。每个桶是一个skiplist。
        //HashLinkedList —— 只能与prefix_extractor工作。他把key放入基于key前缀的桶中。每个桶是一个linked list。
        options.setMemTableConfig(
                new HashSkipListMemTableConfig()
                        .setHeight(4)
                        .setBranchingFactor(4)
                        .setBucketCount(2000000));

        options.setMemTableConfig(
                new HashLinkedListMemTableConfig()
                        .setBucketCount(100000));

        options.setMemTableConfig(
                new VectorMemTableConfig().setReservedSize(10000));

        options.setMemTableConfig(new SkipListMemTableConfig());

        options.setTableFormatConfig(new PlainTableConfig());
        // Plain-Table requires mmap read
        options.setAllowMmapReads(true);

        options.setRateLimiter(rateLimiter);

        final BlockBasedTableConfig table_options = new BlockBasedTableConfig();
        table_options.setBlockCacheSize(64 * SizeUnit.KB)
                .setFilter(bloomFilter)
                .setCacheNumShardBits(6)
                .setBlockSizeDeviation(5)
                .setBlockRestartInterval(10)
                .setCacheIndexAndFilterBlocks(true)
                .setHashIndexAllowCollision(false)
                .setBlockCacheCompressedSize(64 * SizeUnit.KB)
                .setBlockCacheCompressedNumShardBits(10);


        options.setTableFormatConfig(table_options);

        try (final RocksDB db = RocksDB.open(options, db_path)) {
            db.put("hello".getBytes(), "world".getBytes());

            final byte[] value = db.get("hello".getBytes());

            final String str = db.getProperty("rocksdb.stats");
        } catch (final RocksDBException e) {
            System.out.format("[ERROR] caught the unexpected exception -- %s\n", e);
            assert (false);
        }

        try (final RocksDB db = RocksDB.open(options, db_path)) {
            db.put("hello".getBytes(), "world".getBytes());
            byte[] value = db.get("hello".getBytes());
            System.out.format("Get('hello') = %s\n",
                    new String(value));

            for (int i = 1; i <= 9; ++i) {
                for (int j = 1; j <= 9; ++j) {
                    db.put(String.format("%dx%d", i, j).getBytes(),
                            String.format("%d", i * j).getBytes());
                }
            }

            for (int i = 1; i <= 9; ++i) {
                for (int j = 1; j <= 9; ++j) {
                    System.out.format("%s ", new String(db.get(
                            String.format("%dx%d", i, j).getBytes())));
                }
                System.out.println("");
            }

            // write batch test
            try (final WriteOptions writeOpt = new WriteOptions()) {
                for (int i = 10; i <= 19; ++i) {
                    try (final WriteBatch batch = new WriteBatch()) {
                        for (int j = 10; j <= 19; ++j) {
                            batch.put(String.format("%dx%d", i, j).getBytes(),
                                    String.format("%d", i * j).getBytes());
                        }
                        db.write(writeOpt, batch);
                    }
                }
            }
            for (int i = 10; i <= 19; ++i) {
                for (int j = 10; j <= 19; ++j) {
                    assert (new String(
                            db.get(String.format("%dx%d", i, j).getBytes())).equals(
                            String.format("%d", i * j)));
                    System.out.format("%s ", new String(db.get(
                            String.format("%dx%d", i, j).getBytes())));
                }
                System.out.println("");
            }

            value = db.get("1x1".getBytes());
            assert (value != null);
            value = db.get("world".getBytes());
            assert (value == null);
            value = db.get(readOptions, "world".getBytes());
            assert (value == null);

            final byte[] testKey = "asdf".getBytes();
            final byte[] testValue =
                    "asdfghjkl;'?><MNBVCXZQWERTYUIOP{+_)(*&^%$#@".getBytes();
            db.put(testKey, testValue);
            byte[] testResult = db.get(testKey);
            assert (testResult != null);
            assert (Arrays.equals(testValue, testResult));
            assert (new String(testValue).equals(new String(testResult)));
            testResult = db.get(readOptions, testKey);
            assert (testResult != null);
            assert (Arrays.equals(testValue, testResult));
            assert (new String(testValue).equals(new String(testResult)));

            final byte[] insufficientArray = new byte[10];
            final byte[] enoughArray = new byte[50];
            int len;
            len = db.get(testKey, insufficientArray);
            assert (len > insufficientArray.length);
            len = db.get("asdfjkl;".getBytes(), enoughArray);
            assert (len == RocksDB.NOT_FOUND);
            len = db.get(testKey, enoughArray);
            assert (len == testValue.length);

            len = db.get(readOptions, testKey, insufficientArray);
            assert (len > insufficientArray.length);
            len = db.get(readOptions, "asdfjkl;".getBytes(), enoughArray);
            assert (len == RocksDB.NOT_FOUND);
            len = db.get(readOptions, testKey, enoughArray);
            assert (len == testValue.length);

            db.remove(testKey);
            len = db.get(testKey, enoughArray);
            assert (len == RocksDB.NOT_FOUND);

            // repeat the test with WriteOptions
            try (final WriteOptions writeOpts = new WriteOptions()) {
                writeOpts.setSync(true);
                writeOpts.setDisableWAL(true);
                db.put(writeOpts, testKey, testValue);
                len = db.get(testKey, enoughArray);
                assert (len == testValue.length);
                assert (new String(testValue).equals(
                        new String(enoughArray, 0, len)));
            }

            try {
                for (final TickerType statsType : TickerType.values()) {
                    if (statsType != TickerType.TICKER_ENUM_MAX) {
                        stats.getTickerCount(statsType);
                    }
                }
                System.out.println("getTickerCount() passed.");
            } catch (final Exception e) {
                System.out.println("Failed in call to getTickerCount()");
                assert (false); //Should never reach here.
            }

            try {
                for (final HistogramType histogramType : HistogramType.values()) {
                    if (histogramType != HistogramType.HISTOGRAM_ENUM_MAX) {
                        HistogramData data = stats.getHistogramData(histogramType);
                    }
                }
                System.out.println("getHistogramData() passed.");
            } catch (final Exception e) {
                System.out.println("Failed in call to getHistogramData()");
                assert (false); //Should never reach here.
            }

            try (final RocksIterator iterator = db.newIterator()) {

                boolean seekToFirstPassed = false;
                for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                    iterator.status();
                    assert (iterator.key() != null);
                    assert (iterator.value() != null);
                    seekToFirstPassed = true;
                }
                if (seekToFirstPassed) {
                    System.out.println("iterator seekToFirst tests passed.");
                }

                boolean seekToLastPassed = false;
                for (iterator.seekToLast(); iterator.isValid(); iterator.prev()) {
                    iterator.status();
                    assert (iterator.key() != null);
                    assert (iterator.value() != null);
                    seekToLastPassed = true;
                }

                if (seekToLastPassed) {
                    System.out.println("iterator seekToLastPassed tests passed.");
                }

                iterator.seekToFirst();
                iterator.seek(iterator.key());
                assert (iterator.key() != null);
                assert (iterator.value() != null);

                System.out.println("iterator seek test passed.");

            }
            System.out.println("iterator tests passed.");

            final List<byte[]> keys = new ArrayList<>();
            try (final RocksIterator iterator = db.newIterator()) {
                for (iterator.seekToLast(); iterator.isValid(); iterator.prev()) {
                    keys.add(iterator.key());
                }
            }

            Map<byte[], byte[]> values = db.multiGet(keys);
            assert (values.size() == keys.size());
            for (final byte[] value1 : values.values()) {
                assert (value1 != null);
            }

            values = db.multiGet(new ReadOptions(), keys);
            assert (values.size() == keys.size());
            for (final byte[] value1 : values.values()) {
                assert (value1 != null);
            }
        } catch (final RocksDBException e) {
            System.err.println(e);
        }
    }
}
