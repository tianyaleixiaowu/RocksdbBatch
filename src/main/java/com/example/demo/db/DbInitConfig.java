package com.example.demo.db;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 配置启用哪个db，部分Windows机器用不了rocksDB，可以选择levelDB
 * @author wuweifeng wrote on 2018/3/13.
 */
@Configuration
public class DbInitConfig {

    @Primary
    @Bean(name = "rocksDB0")
    public RocksDB rocksDB() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);

        try {
            return RocksDB.open(options, "./rocksDB0");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB1")
    public RocksDB rocksDB1() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB1");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB2")
    public RocksDB rocksDB2() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB2");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB3")
    public RocksDB rocksDB3() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB3");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB4")
    public RocksDB rocksDB4() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB4");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB5")
    public RocksDB rocksDB5() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB5");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB6")
    public RocksDB rocksDB6() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB6");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB7")
    public RocksDB rocksDB7() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB7");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB8")
    public RocksDB rocksDB8() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB8");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "rocksDB9")
    public RocksDB rocksDB9() {
        RocksDB.loadLibrary();
        Options options = new Options().setCreateIfMissing(true);
        options.setMaxBackgroundCompactions(16);
        options.setNewTableReaderForCompactionInputs(true);
        try {
            return RocksDB.open(options, "./rocksDB9");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
