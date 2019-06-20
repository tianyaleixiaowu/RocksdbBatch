package com.example.demo.db;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author wuwf
 */
public class FetchFromRocks implements Runnable {
    private DbStore dbStore;
    private List<String> list;
    private CountDownLatch countDownLatch;
    private ConcurrentHashMap<String, String> map;


    public FetchFromRocks(DbStore dbStore, List<String> list, ConcurrentHashMap<String, String> map, CountDownLatch
            countDownLatch) {
        this.dbStore = dbStore;
        this.list = list;
        this.countDownLatch = countDownLatch;
        this.map = map;
    }

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("进入线程");
        map.putAll(dbStore.multiGetFromOne(list));
        System.out.println(Thread.currentThread().getName() + "读取耗时：" + (System.currentTimeMillis() - time));
        countDownLatch.countDown();
    }
}
