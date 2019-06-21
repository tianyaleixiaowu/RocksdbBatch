package com.example.demo.controller;

import com.example.demo.redis.ImportToRedis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
@Service
public class InsertService {
    //@Resource
    //private DbStore dbStore;
    @Value("${thread.count}")
    private Integer threadCount;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private ExecutorService executorService;


    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(8);
    }

    @SuppressWarnings("AlibabaThreadPoolCreation")
    public void insert(long number) {
        System.out.println("--------------------------------------当前号段" + number);

        //每次每个线程写入10万个
        int batchSize = 100000;

        //这一亿个数，分多少次写入
        int loopCount = 100000000 / batchSize;

        CountDownLatch countDownLatch = new CountDownLatch(loopCount);
        for (int i = 0; i < loopCount; i++) {
            List<String> tempList = new ArrayList<>();
            for (long j = number + batchSize * i; j < number + (batchSize * (i + 1)); j++) {
                tempList.add(j + "");
            }

            executorService.execute(new ImportToRedis(stringRedisTemplate, tempList, countDownLatch));
        }

        try {
            countDownLatch.await();
            System.out.println("-----------------------------插入完毕-------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertAll() {
        // 13000000000L, 13100000000L, 13200000000L, 13300000000L, 
        long[] array = {
               13400000000L, 13500000000L,
                13600000000L, 13700000000L, 13800000000L, 13900000000L, 14500000000L, 14600000000L,
                14700000000L, 14800000000L, 14900000000L, 15000000000L, 15100000000L, 15200000000L,
                15300000000L,  15500000000L, 15600000000L, 15700000000L, 15800000000L, 15900000000L,
                16500000000L, 16600000000L, 17000000000L, 17100000000L, 17200000000L, 17300000000L,
                17400000000L, 17500000000L, 17600000000L, 17700000000L, 17800000000L, 18600000000L,
                18700000000L,  18800000000L, 18900000000L, 19800000000L, 19900000000L};
        CountDownLatch countDownLatch = new CountDownLatch(array.length);
        for (long num : array) {
            insert(num);
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
            System.out.println("-----------------------------插入完毕-------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
