package com.example.demo.redis;

import com.example.demo.CommonUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ImportToRedis implements Runnable {
    private StringRedisTemplate stringRedisTemplate;
    private List<String> list;
    private CountDownLatch countDownLatch;

    public ImportToRedis(StringRedisTemplate redisTemplate, List<String> list, CountDownLatch countDownLatch) {
        this.stringRedisTemplate = redisTemplate;
        this.countDownLatch = countDownLatch;
        this.list = list;
    }

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("进入线程");

        //最多1万个key
        //Map<String, Map<String, String>> map = new HashMap<>(KeyTool.KEY_COUNT);
        //for (String phone : list) {
        //    String md5 = CommonUtil.md5(phone);
        //    //hash的key
        //    String hashKey = KeyTool.hashKey(md5);
        //    //在hash里面的key
        //    String realKey = KeyTool.newKey(md5);
        //    if (map.get(hashKey) == null) {
        //        Map<String, String> inMap = new HashMap<>();
        //        map.put(hashKey, inMap);
        //    }
        //    map.get(hashKey).put(realKey, phone);
        //}

        stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                //for (String key : map.keySet()) {
                //    stringRedisTemplate.opsForHash().putAll(key, map.get(key));
                //}
                for (String phone : list) {
                    String md5 = CommonUtil.md5(phone);
                    String hashKey = KeyTool.hashKey(md5);
                    String realKey = KeyTool.newKey(md5);
                    stringRedisTemplate.opsForHash().put(hashKey, realKey, phone);
                }
                return null;
            }
        });
        System.out.println(Thread.currentThread().getName() + "插入耗时：" + (System.currentTimeMillis() - time));
        countDownLatch.countDown();
    }
}
