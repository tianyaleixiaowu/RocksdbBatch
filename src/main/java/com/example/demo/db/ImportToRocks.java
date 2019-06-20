package com.example.demo.db;

import com.example.demo.CommonUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wuwf
 */
public class ImportToRocks implements Runnable {
    private DbStore dbStore;
    private List<String> list;
    private  CountDownLatch countDownLatch;

    public ImportToRocks(DbStore dbStore, List<String> list, CountDownLatch countDownLatch) {
        this.dbStore = dbStore;
        this.list = list;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        Long time = System.currentTimeMillis();
        System.out.println("进入线程");
        for (String s : list) {
            dbStore.put(CommonUtil.md5(s), s);
        }
        System.out.println(Thread.currentThread().getName() + "插入耗时：" + (System.currentTimeMillis() - time));
        countDownLatch.countDown();
    }
}
