package com.example.demo.redis;

import com.example.demo.MyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
public class RedisPickTool implements Runnable {
    private StringRedisTemplate stringRedisTemplate;
    private List<String> keys;
    private ApplicationEventPublisher publisher;
    private CountDownLatch downLatch;

    public RedisPickTool(StringRedisTemplate stringRedisTemplate, List<String> keys, ApplicationEventPublisher
            publisher, CountDownLatch downLatch) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.keys = keys;
        this.publisher = publisher;
        this.downLatch = downLatch;
    }

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("进入线程：" + Thread.currentThread().getName());
        List<Object> phones = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                //for (int i = 0; i < keys.size(); i++) {
                //    stringRedisTemplate.opsForValue().get(keys.get(i));
                //}
                stringRedisTemplate.opsForValue().multiGet(keys);
                return null;
            }
        });
        publisher.publishEvent(new MyEvent(phones));
        downLatch.countDown();
        System.out.println(Thread.currentThread().getName() + "读取1万耗时：" + (System.currentTimeMillis() - time) + "毫秒");
    }
}
