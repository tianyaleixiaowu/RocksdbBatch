package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2018/8/2.
 */
@Component
public class HttpUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RestTemplate restTemplate;

    public String build(String url, MultiValueMap<String, String> postParameters) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(postParameters, httpHeaders);
        String attr = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                String.class)
                .getBody();
        //logger.info("解码返回的结果是：" + attr);
        return attr;
    }

}
