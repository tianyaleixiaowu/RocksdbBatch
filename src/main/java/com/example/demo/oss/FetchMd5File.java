package com.example.demo.oss;

import com.example.demo.config.OssUtil;
import com.example.demo.db.DbStore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "0 */1 * * * ?")
    private void fetchTodayFiles() throws IOException {
        Set<String> todayFiles = ossUtil.list();
        for (String file : todayFiles) {
            if(dbStore.get(file) == null) {
                ossUtil.dealFile(file);
                dbStore.put(file, "ok");
            }
        }

    }
}
