package com.example.demo.controller;

import com.example.demo.FetchFromRocks;
import com.example.demo.db.DbStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuweifeng wrote on 2019/6/19.
 */
@Service
public class FetchService {
    @Resource
    private DbStore dbStore;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Integer code(String key) {
        int code = key.hashCode() % 10;
        if (code < 0) {
            code = -code;
        }
        return code;
    }

    public Map<String, String> batch(List<String> list) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(list.size());

        MultiValueMap<Integer, String> multiValueMap = new LinkedMultiValueMap<>();
        for (String s : list) {
            multiValueMap.add(code(s), s);
        }

        CountDownLatch countDownLatch = new CountDownLatch(multiValueMap.keySet().size());
        for (Integer code : multiValueMap.keySet()) {
            System.out.println("-------valueSize------:" + multiValueMap.get(code).size());
            executorService.execute(new FetchFromRocks(dbStore, ((LinkedMultiValueMap<Integer, String>)
                    multiValueMap).get(code), map, countDownLatch));
        }

        ////每次每个线程读5千个
        //int batchSize = 5000;
        ////这一亿个数，分多少次写入
        //int loopCount;
        //if (list.size() % batchSize == 0) {
        //    loopCount = list.size() / batchSize;
        //} else {
        //    loopCount = list.size() / batchSize + 1;
        //}
        //
        //CountDownLatch countDownLatch = new CountDownLatch(loopCount);
        //for (int i = 0; i < loopCount; i++) {
        //
        //    List<String> tempList;
        //    if (batchSize * (i + 1) > list.size()) {
        //        tempList = list.subList(batchSize * i, list.size());
        //    } else {
        //        tempList = list.subList(batchSize * i, batchSize * (i + 1));
        //    }
        //
        //    executorService.execute(new FetchFromRocks(dbStore, tempList, map, countDownLatch));
        //}
        try {
            countDownLatch.await();
            System.out.println("共返回：" + map.keySet().size() + "个数据");
            return map;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }


}
