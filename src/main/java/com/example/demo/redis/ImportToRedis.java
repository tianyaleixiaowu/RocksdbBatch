package com.example.demo.redis;

import com.example.demo.CommonUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

public class ImportToRedis implements Runnable {
    private StringRedisTemplate stringRedisTemplate;
    private Map<String, String> map;

    public ImportToRedis(StringRedisTemplate redisTemplate, Map<String, String> map) {
        this.stringRedisTemplate = redisTemplate;
        this.map = map;
    }

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("进入线程");
        stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                for (String key : map.keySet()) {
                    stringRedisTemplate.opsForValue().set(CommonUtil.md5(key), map.get(key));
                }
                return null;
            }
        });
        System.out.println(Thread.currentThread().getName() + "插入耗时：" + (System.currentTimeMillis() - time));
    }
}
