package com.example.demo.redis;

import com.example.demo.CommonUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuweifeng wrote on 2019/6/20.
 */
public class KeyTool {
    public static int KEY_COUNT = 2<<25;

    /**
     * 计算redis的hash key
     */
    public static String hashKey(String key) {
        int code = key.hashCode() % KEY_COUNT;
        if (code < 0) {
            code = -code;
        }

        return "" + code;
    }

    public static String newKey(String key) {
        //return key.substring(0, 8);
        return key.hashCode() + "";
    }

    /**
     * BKDR算法
     */
    public static int BKDRHash(String str) {
        // 31 131 1313 13131 131313 etc.. 找个质数
        int seed = 131;
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    public static void main(String[] args) {
        System.out.println(KEY_COUNT);

        System.out.println(CommonUtil.md5("1").hashCode());

        Set<Integer> strings = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            strings.add(BKDRHash(CommonUtil.md5(i + "")));
        }
        System.out.println(strings.iterator().next());
        //System.out.println(KEY_COUNT);
        List<String> list = new ArrayList<>();

        //for (int i = 0; i < 10000; i++) {
        //    strings.add(newKey(CommonUtil.md5(i + "")));
        //}
        //System.out.println(strings.size());
        //Map<String, Map<String, String>> map = new HashMap<>();
        //for (String phone : list) {
        //    String md5 = CommonUtil.md5(phone);
        //    //hash的key
        //    String hashKey = KeyTool.hashKey(md5);
        //    //在hash里面的key
        //    String realKey = KeyTool.newKey(md5);
        //    if (map.get(hashKey) == null) {
        //        Map<String, String> inMap = new HashMap<>();
        //        map.put(hashKey, inMap);
        //    }
        //    map.get(hashKey).put(realKey, phone);
        //}
        //System.out.println();
    }
}
