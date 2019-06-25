package com.example.demo;

import com.example.demo.config.HttpUtil;
import com.example.demo.controller.InsertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ApplicationEventPublisher publisher;
    @Resource
    private InsertService insertService;
    @Resource
    private HttpUtil httpUtil;

    @Test
    public void contextLoads() throws Exception {
        //insertService.insert("a", "b");
        //insertService.insertAll();

        int batchSize = 50000;


        //Long time = System.currentTimeMillis();
        //
        //StringBuilder stringBuilder = new StringBuilder();
        //
        //for (long i = 13100300000L; i < 13100305000L; i++) {
        //    String md5 = CommonUtil.md5(i + "");
        //    stringBuilder.append(md5).append(",");
        //}
        //System.out.println("开始了");
        //MultiValueMap<String, String> valueMap = new LinkedMultiValueMap();
        //valueMap.add("content", stringBuilder.toString());
        //httpUtil.build("http://172.16.1.224:8080/fetch", valueMap);
        //
        //System.out.println(System.currentTimeMillis() - time);
        //insertService.insert(134L);
        //insertService.insertAll();

        //ExecutorService executorService = Executors.newFixedThreadPool(50);
        //
        //Long time = System.currentTimeMillis();
        //List<String> queryList = new ArrayList<>(10000);
        //for (int i = 0; i < 100000; i++) {
        //    queryList.add(dbStore.get(CommonUtil.md5(i + "")));
        //}
        //
        //System.out.println("查10万:" + (System.currentTimeMillis() - time));
        //
        //int[] array = {134, 135, 136, 137, 138, 139, 147, 148, 150, 151, 152, 157, 158, 159, 165, 172, 178, 182, 183,
        //        184, 187, 188, 198,
        //        130, 131, 132, 145, 146, 155, 156, 166, 171, 175, 176, 185, 186, 133, 149, 153, 173, 174, 177, 180,
        //        181, 189, 199, 170};
        //for (int i = 0; i < array.length; i++) {
        //    //134
        //    int temp = array[i];
        //    List<String> list = new ArrayList<>(100000000);
        //    for (long j = temp * 100000000; j < temp * 100000000 + 99999999; j++) {
        //        list.add(i + "");
        //        executorService.execute(new Thread(new ImportToRocks(dbStore, list)));
        //    }
        //}
        //
        //System.out.println("开始了");
        //int batchSize = 10000;

        //int threadCount;
        //if (keys.size() % batchSize == 0) {
        //    threadCount = keys.size() / batchSize;
        //} else {
        //    threadCount = keys.size() / batchSize + 1;
        //}

    }

}
