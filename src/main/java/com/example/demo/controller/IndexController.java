package com.example.demo.controller;

import com.example.demo.db.DbStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
@RestController
@RequestMapping
public class IndexController {
    @Resource
    private DbStore dbStore;
    @Resource
    private InsertService insertService;
    @Resource
    private FetchService fetchService;

    @RequestMapping("fetch")
    public Map<String, String> query(String content) {
        String[] array = content.split(",");
        Map<String, String> map = new HashMap<>();
        for (String s : array) {
            map.put(s, dbStore.get(s));
        }
        return map;
    }

    @RequestMapping("batch")
    public Map<String, String> batch(String content) {
        String[] array = content.split(",");
        List<String> list = Arrays.asList(array);

        return fetchService.batch(list);
    }

    @RequestMapping("insert")
    public String insert(long number) {
        insertService.insert(number);

        return "1";
    }

    @RequestMapping("insertAll")
    public String insertAll() {
        insertService.insertAll();
        return "1";
    }
}
