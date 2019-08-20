package com.example.demo.db;

import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuweifeng wrote on 2018/3/13.
 */
@Configuration
public class DbInitConfig {

    private Options options() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        //为压缩的输入，打开RocksDB层的预读取
        options.setCompactionReadaheadSize(128 * SizeUnit.KB);
        options.setNewTableReaderForCompactionInputs(true);

        Filter bloomFilter = new BloomFilter(10);
        final BlockBasedTableConfig tableConfig = new BlockBasedTableConfig();
        tableConfig.setBlockCacheSize(64 * SizeUnit.KB)
                .setFilter(bloomFilter)
                .setCacheNumShardBits(6)
                .setBlockSizeDeviation(5)
                .setBlockRestartInterval(10)
                .setCacheIndexAndFilterBlocks(true)
                .setHashIndexAllowCollision(false)
                .setBlockCacheCompressedSize(64 * SizeUnit.KB)
                .setBlockCacheCompressedNumShardBits(10);

        options.setTableFormatConfig(tableConfig);

        return options;
    }

    @Bean
    public RocksDB rocksDB() {
        try {
            return RocksDB.open(options(), "./phone-md5-file");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
