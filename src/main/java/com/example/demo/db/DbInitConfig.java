package com.example.demo.db;

import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 
 * @author wuweifeng wrote on 2018/3/13.
 */
//@Configuration
public class DbInitConfig {
    @Value("${rock.readOnly}")
    private Boolean readOnly;

    public static int TOTAL_ROCKS = 10;

    private Options options() {
        //RocksDB.loadLibrary();
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

    @Primary
    @Bean(name = "RockBean0")
    public RocksDB rocksDB() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu0");
            } else {
                return RocksDB.open(options(), "./wu0");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean1")
    public RocksDB rocksDB1() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu1");
            } else {
                return RocksDB.open(options(), "./wu1");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean2")
    public RocksDB rocksDB2() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu2");
            } else {
                return RocksDB.open(options(), "./wu2");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean3")
    public RocksDB rocksDB3() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu3");
            } else {
                return RocksDB.open(options(), "./wu3");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean4")
    public RocksDB rocksDB4() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu4");
            } else {
                return RocksDB.open(options(), "./wu4");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean5")
    public RocksDB rocksDB5() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu5");
            } else {
                return RocksDB.open(options(), "./wu5");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean6")
    public RocksDB rocksDB6() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu6");
            } else {
                return RocksDB.open(options(), "./wu6");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean7")
    public RocksDB rocksDB7() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu7");
            } else {
                return RocksDB.open(options(), "./wu7");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean(name = "RockBean8")
    public RocksDB rocksDB8() {
        try {
            if (readOnly) {
                return RocksDB.openReadOnly(options(), "./wu8");
            } else {
                return RocksDB.open(options(), "./wu8");
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }

    //@Bean(name = "RockBean9")
    //public RocksDB rocksDB9() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu9");
    //        } else {
    //            return RocksDB.open(options(), "./wu9");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean10")
    //public RocksDB rocksDB10() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu10");
    //        } else {
    //            return RocksDB.open(options(), "./wu10");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean11")
    //public RocksDB rocksDB11() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu11");
    //        } else {
    //            return RocksDB.open(options(), "./wu11");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean12")
    //public RocksDB rocksDB12() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu12");
    //        } else {
    //            return RocksDB.open(options(), "./wu12");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean13")
    //public RocksDB rocksDB13() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu13");
    //        } else {
    //            return RocksDB.open(options(), "./wu13");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean14")
    //public RocksDB rocksDB14() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu14");
    //        } else {
    //            return RocksDB.open(options(), "./wu14");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean15")
    //public RocksDB rocksDB15() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu15");
    //        } else {
    //            return RocksDB.open(options(), "./wu15");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean16")
    //public RocksDB rocksDB16() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu16");
    //        } else {
    //            return RocksDB.open(options(), "./wu16");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean17")
    //public RocksDB rocksDB17() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu17");
    //        } else {
    //            return RocksDB.open(options(), "./wu17");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean18")
    //public RocksDB rocksDB18() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu18");
    //        } else {
    //            return RocksDB.open(options(), "./wu18");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean19")
    //public RocksDB rocksDB19() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu19");
    //        } else {
    //            return RocksDB.open(options(), "./wu19");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean20")
    //public RocksDB rocksDB20() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu20");
    //        } else {
    //            return RocksDB.open(options(), "./wu20");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean21")
    //public RocksDB rocksDB21() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu21");
    //        } else {
    //            return RocksDB.open(options(), "./wu21");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean22")
    //public RocksDB rocksDB22() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu22");
    //        } else {
    //            return RocksDB.open(options(), "./wu22");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean23")
    //public RocksDB rocksDB23() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu23");
    //        } else {
    //            return RocksDB.open(options(), "./wu23");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean24")
    //public RocksDB rocksDB24() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu24");
    //        } else {
    //            return RocksDB.open(options(), "./wu24");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean25")
    //public RocksDB rocksDB25() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu25");
    //        } else {
    //            return RocksDB.open(options(), "./wu25");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean26")
    //public RocksDB rocksDB26() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu26");
    //        } else {
    //            return RocksDB.open(options(), "./wu26");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean27")
    //public RocksDB rocksDB27() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu27");
    //        } else {
    //            return RocksDB.open(options(), "./wu27");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean28")
    //public RocksDB rocksDB28() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu28");
    //        } else {
    //            return RocksDB.open(options(), "./wu28");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean29")
    //public RocksDB rocksDB29() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu29");
    //        } else {
    //            return RocksDB.open(options(), "./wu29");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean30")
    //public RocksDB rocksDB30() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu30");
    //        } else {
    //            return RocksDB.open(options(), "./wu30");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
    //
    //@Bean(name = "RockBean31")
    //public RocksDB rocksDB31() {
    //    try {
    //        if (readOnly) {
    //            return RocksDB.openReadOnly(options(), "./wu31");
    //        } else {
    //            return RocksDB.open(options(), "./wu31");
    //        }
    //    } catch (RocksDBException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}
}
