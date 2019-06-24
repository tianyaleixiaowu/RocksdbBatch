package com.example.demo.controller;

import com.example.demo.CommonUtil;
import com.example.demo.config.HttpUtil;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
    @Resource
    private HttpUtil httpUtil;

    @RequestMapping("fetch")
    public Object fetch(String content) {
        String[] array = content.split(",");
        List<String> list = Arrays.asList(array);

        return fetchService.fetch(list);
    }

    @RequestMapping("batch")
    public Object batch(String content) {
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

    @RequestMapping("test")
    public Object test(Long begin) {
        Long time = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();

        for (long i = begin; i < begin + 10000L; i++) {
            String md5 = CommonUtil.md5(i + "");
            stringBuilder.append(md5).append(",");
        }
        System.out.println("开始了");
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap();
        valueMap.add("content", stringBuilder.toString());
        Object object = httpUtil.build("http://172.16.1.224:8080/fetch", valueMap);

        System.out.println(System.currentTimeMillis() - time);
        return object;
    }
}
