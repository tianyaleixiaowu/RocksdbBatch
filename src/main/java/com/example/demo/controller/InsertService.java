package com.example.demo.controller;

import com.example.demo.ImportToRocks;
import com.example.demo.db.DbStore;
import org.springframework.stereotype.Service;

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
    @Resource
    private DbStore dbStore;

    private ExecutorService executorService = Executors.newFixedThreadPool(64);

    @SuppressWarnings("AlibabaThreadPoolCreation")
    public void insert(long number) {
        System.out.println("--------------------------------------当前号段" + number);

        //每个号段一亿个数
        int totalCount = 100000000;

        //int totalCount = 1000000;
        //每次每个线程写入1万个
        int batchSize = 10000;

        number = number * totalCount;

        //这一亿个数，分多少次写入
        int loopCount = totalCount / batchSize;

        CountDownLatch countDownLatch = new CountDownLatch(loopCount);
        for (int i = 0; i < loopCount; i++) {
            List<String> tempList = new ArrayList<>();
            for (long j = number + batchSize * i; j < number + (batchSize * (i + 1)); j++) {
                tempList.add(j + "");
            }

            executorService.execute(new ImportToRocks(dbStore, tempList, countDownLatch));
        }

        try {
            countDownLatch.await();
            System.out.println("-----------------------------插入完毕-------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertAll() {
        //134L, 135L, 136L, 137L, 138L, 139L,147L, 148L, 150L, 151L, 152L, 157L, 158L, 159L, 165L, 172L, 178L, 182L,
        // 183L,
        //                184L, 187L, 188L, 198L,
        //                130L, 131L, 132L, 145L, 146L, 155L, 156L, 166L, 171L, 175L, 176L, 185L, 186L, 
        long[] array = {133L, 149L, 153L, 173L, 174L, 177L, 180L,
                181L, 189L, 199L, 170L};
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
