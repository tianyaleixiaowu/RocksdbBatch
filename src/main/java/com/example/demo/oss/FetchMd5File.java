package com.example.demo.oss;

import com.example.demo.config.OssUtil;
import com.example.demo.db.DbStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Set;

/**
 * @author wuweifeng wrote on 2019-08-20.
 */
@Component
public class FetchMd5File {
    @Resource
    private OssUtil ossUtil;
    @Resource
    private DbStore dbStore;
    @Resource
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 */1 * * * ?")
    private void fetchTodayFiles() throws IOException {
        Set<String> todayFiles = ossUtil.list();
        for (String file : todayFiles) {
            if (file.contains("real")) {
                continue;
            }
            //file:  2018-09-01/1.txt
            if (dbStore.get(file) == null) {
                ossUtil.dealFile(file);
                dbStore.put(file, "ok");

                int begin = file.indexOf("/");
                int end = file.indexOf(".");
                //id就是model的id
                String id = file.substring(begin + 1, end);
                build("http://core.maimenggroup.com/zuuldmp/core/model/.complete?id=" + id);
            }
        }

    }

    private void build(String url) {
        restTemplate.getForObject(url, String.class);
    }
}
