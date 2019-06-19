package com.example.demo.db;

import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * @author wuweifeng wrote on 2018/3/13.
 */
@Configuration
public class DbInitConfig {
    @Value("${rock.readOnly}")
    private Boolean readOnly;

    private Options options() {
        //RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        //为压缩的输入，打开RocksDB层的预读取
        options.setCompactionReadaheadSize(128 * SizeUnit.KB);
        options.setNewTableReaderForCompactionInputs(true);

        Filter bloomFilter = new BloomFilter(10);
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

        return options;
    }

    @Primary
    @Bean(name = "rocksDB0")
    public RocksDB rocksDB() {
        try {

            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB0");
            } else {
                return RocksDB.open(options(), "./rocksDB0");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB1")
    public RocksDB rocksDB1() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB1");
            } else {
                return RocksDB.open(options(), "./rocksDB1");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB2")
    public RocksDB rocksDB2() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB2");
            } else {
                return RocksDB.open(options(), "./rocksDB2");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB3")
    public RocksDB rocksDB3() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB3");
            } else {
                return RocksDB.open(options(), "./rocksDB3");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB4")
    public RocksDB rocksDB4() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB4");
            } else {
                return RocksDB.open(options(), "./rocksDB4");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB5")
    public RocksDB rocksDB5() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB5");
            } else {
                return RocksDB.open(options(), "./rocksDB5");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB6")
    public RocksDB rocksDB6() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB6");
            } else {
                return RocksDB.open(options(), "./rocksDB6");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB7")
    public RocksDB rocksDB7() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB7");
            } else {
                return RocksDB.open(options(), "./rocksDB7");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB8")
    public RocksDB rocksDB8() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB8");
            } else {
                return RocksDB.open(options(), "./rocksDB8");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB9")
    public RocksDB rocksDB9() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./rocksDB9");
            } else {
                return RocksDB.open(options(), "./rocksDB9");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
