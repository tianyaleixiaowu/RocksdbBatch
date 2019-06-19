package com.example.demo.db;

import java.util.List;
import java.util.Map;

/**
 * key-value型DB数据库操作接口
 * @author wuweifeng wrote on 2018/3/26.
 */
public interface DbStore {
    /**
     * 数据库key value
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    void put(String key, String value);

    /**
     * get By Key
     *
     * @param key
     *         key
     * @return value
     */
    String get(String key);

    Map<String, String> multiGet(List<String> key);


    Map<String, String> multiGetFromOne(List<String> key);
    /**
     * remove by key
     *
     * @param key
     *         key
     */
    void remove(String key);
}
