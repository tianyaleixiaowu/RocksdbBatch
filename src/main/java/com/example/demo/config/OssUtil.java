package com.example.demo.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.example.demo.redis.KeyTool;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wuweifeng wrote on 2019-08-20.
 */
@Component
public class OssUtil {
    @Resource
    private OSS oss;
    @Value("${aliyun.bucketName}")
    private String bucketName;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void upload(String remotePath, String localPath) {
        oss.putObject(bucketName, remotePath, new File(localPath));
    }

    public void dealFile(String fileName) throws IOException {
        File file = new File("./" + fileName.substring(0, 10));
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("./" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        //写入相应的文件
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),
                StandardCharsets.UTF_8));

        OSSObject ossObject = oss.getObject(new GetObjectRequest(bucketName, fileName));
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.length() < 32) {
                continue;
            }
            line = line.toLowerCase().trim();
            Object phone = phone(line.substring(0, 32));
            if (phone != null) {
                out.write(phone.toString() + line.substring(32));
                out.newLine();
            }
        }
        out.flush();
        out.close();
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();

        upload(fileName + "_real", file.getAbsolutePath());
    }

    public static void main(String[] args) {
        String s = "71592a13792094de7e11ff1e0e9ab5d7|score=0.49396";
        System.out.println(s.substring(0, 32));
        System.out.println(s.substring(32));
    }

    private Object phone(String md5) {
        //hash的key
        String hashKey = KeyTool.hashKey(md5);
        //在hash里面的key
        String realKey = KeyTool.newKey(md5);

        return stringRedisTemplate.opsForHash().get(hashKey, realKey);
    }


    /**
     * 查询文件夹
     */
    public Set<String> list() {
        Date date = new Date();
        String today = DateUtils.formatDate(date, "yyyy-MM-dd");
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(today + "/");

        // 递归列出fun目录下的所有文件。
        ObjectListing listing = oss.listObjects(listObjectsRequest);

        Set<String> strings = new HashSet<>();
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            String fileName = objectSummary.getKey();
            strings.add(fileName);
        }
        return strings;
    }
}
