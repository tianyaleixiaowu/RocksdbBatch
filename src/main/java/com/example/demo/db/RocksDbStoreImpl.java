package com.example.demo.db;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.db.DbInitConfig.TOTAL_ROCKS;

/**
 * rocksDB对于存储接口的实现
 *
 * @author wuweifeng wrote on 2018/3/13.
 */
//@Component
public class RocksDbStoreImpl implements DbStore {

    @Resource
    private List<RocksDB> rocksDBS;

    private RocksDB getRocksDB(String key) {
        int code = key.hashCode() % TOTAL_ROCKS;

        return getRocksDB(code);
    }

    private RocksDB getRocksDB(int code) {
        if (code < 0) {
            code = -code;
        }
        return rocksDBS.get(code);
    }

    @Override
    public void put(String key, String value) {
        try {
            RocksDB rocksDB = getRocksDB(key);
            rocksDB.put(key.getBytes(Const.CHARSET), value.getBytes(Const.CHARSET));
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        try {
            byte[] bytes = getRocksDB(key).get(key.getBytes(Const.CHARSET));
            if (bytes != null) {
                return new String(bytes, Const.CHARSET);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> multiGet(List<String> keys) {
        try {
            MultiValueMap<Integer, String> multiValueMap = new LinkedMultiValueMap<>();

            for (String single : keys) {
                int code = single.hashCode() % 10;
                multiValueMap.add(code, single);
            }

            Map<String, String> results = new HashMap<>(keys.size());

            for (int key : multiValueMap.keySet()) {
                List<String> oneList = ((LinkedMultiValueMap<Integer, String>) multiValueMap).get(key);
                List<byte[]> oneKeyList = new ArrayList<>();
                for (String s : oneList) {
                    oneKeyList.add(s.getBytes(Const.CHARSET));
                }

                Map<byte[], byte[]> valueMap = getRocksDB(key).multiGet(oneKeyList);

                Map<String, String> oneResult = new HashMap<>(oneList.size());
                for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
                    oneResult.put(new String(entry.getKey()), new String(entry.getValue()));
                }

                results.putAll(oneResult);
            }

            return results;
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, String> multiGetFromOne(List<String> keys) {
        try {
            int code = keys.get(0).hashCode() % 10;

            Map<String, String> results = new HashMap<>(keys.size());

            List<byte[]> oneKeyList = new ArrayList<>();
            for (String s : keys) {
                oneKeyList.add(s.getBytes(Const.CHARSET));
            }

            Map<byte[], byte[]> valueMap = getRocksDB(code).multiGet(oneKeyList);

            for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
                results.put(new String(entry.getKey()), new String(entry.getValue()));
            }

            return results;
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove(String key) {
        try {
            getRocksDB(key).delete(getRocksDB(key).get(key.getBytes(Const.CHARSET)));
        } catch (RocksDBException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
