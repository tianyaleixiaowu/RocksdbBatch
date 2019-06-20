package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
@RestController
@RequestMapping
public class IndexController {
    //@Resource
    //private DbStore dbStore;
    @Resource
    private InsertService insertService;
    @Resource
    private FetchService fetchService;

    @RequestMapping("fetch")
    public Map<String, String> fetch(String content) {
        String[] array = content.split(",");
        List<String> list = Arrays.asList(array);

        return fetchService.fetch(list);
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
