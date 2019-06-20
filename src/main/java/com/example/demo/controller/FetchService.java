package com.example.demo.controller;

import com.example.demo.CommonUtil;
import com.example.demo.redis.KeyTool;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuweifeng wrote on 2019/6/19.
 */
@Service
public class FetchService {
    //@Resource
    //private DbStore dbStore;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ExecutorService executorService = Executors.newFixedThreadPool(16);

    public Map<String, String> batch(List<String> list) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(list.size());

        for (String phone : list) {
            String md5 = CommonUtil.md5(phone);
            //hash的key
            String hashKey = KeyTool.hashKey(md5);
            //在hash里面的key
            String realKey = KeyTool.newKey(md5);


        }
        return null;
    }

    public Map<String, String> fetch(List<String> keys) {
        Map<String, String> totalMap = new HashMap<>(keys.size());
        int batchSize = 50000;

        List<Object> total = new ArrayList<>(keys.size());
        for (int i = 0; i < keys.size() / batchSize + 1; i++) {
            List<String> list;
            if (batchSize * (i + 1) > keys.size()) {
                list = keys.subList(batchSize * i, keys.size());
            } else {
                list = keys.subList(batchSize * i, batchSize * (i + 1));
            }

            List<Object> phones = fromRedis(list);
            total.add(phones);
        }

        for (int i = 0; i < total.size(); i++) {
            totalMap.put(keys.get(i), total.get(i).toString());
        }
        return totalMap;
    }

    private List<Object> fromRedis(List<String> list) {
        return stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (String md5 : list) {
                    //hash的key
                    String hashKey = KeyTool.hashKey(md5);
                    //在hash里面的key
                    String realKey = KeyTool.newKey(md5);

                    stringRedisTemplate.opsForHash().get(hashKey, realKey);
                }
                return null;
            }
        });
    }

    private Integer code(String key) {
        int code = key.hashCode() % 10;
        if (code < 0) {
            code = -code;
        }
        return code;
    }

    //public Map<String, String> rocksBatch(List<String> list) {
    //    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(list.size());
    //
    //    MultiValueMap<Integer, String> multiValueMap = new LinkedMultiValueMap<>();
    //    for (String s : list) {
    //        multiValueMap.add(code(s), s);
    //    }
    //
    //    CountDownLatch countDownLatch = new CountDownLatch(multiValueMap.keySet().size());
    //    for (Integer code : multiValueMap.keySet()) {
    //        System.out.println("-------valueSize------:" + multiValueMap.get(code).size());
    //        executorService.execute(new FetchFromRocks(dbStore, ((LinkedMultiValueMap<Integer, String>)
    //                multiValueMap).get(code), map, countDownLatch));
    //    }
    //
    //    try {
    //        countDownLatch.await();
    //        System.out.println("共返回：" + map.keySet().size() + "个数据");
    //        return map;
    //
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //
    //}


}
