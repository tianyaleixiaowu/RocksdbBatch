package com.example.demo.redis;

import com.example.demo.CommonUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.zip.CRC32;

/**
 * @author wuweifeng wrote on 2019/6/20.
 */
public class KeyTool {
    public static int KEY_COUNT = 2<<24;

    /**
     * 计算redis的hash key
     */
    public static String hashKey(String key) {
        CRC32 crc32 = new CRC32();
        crc32.update(key.getBytes());
        return crc32.getValue() % KEY_COUNT + "";
    }

    public static String newKey(String key) {
        return BKDRHash(key) + "";
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

    public static byte[] getBucketId(String md5, Integer bit) {
        byte[] md = md5.getBytes();
        // 因为一个字节中只有7位能够表示成单字符
        byte[] r = new byte[(bit - 1) / 7 + 1];

        int a = (int) Math.pow(2, bit % 7) - 2;
        md[r.length - 1] = (byte) (md[r.length - 1] & a);
        System.arraycopy(md, 0, r, 0, r.length);
        for (int i = 0; i < r.length; i++) {
            if (r[i] < 0) {
                r[i] &= 127;
            }
        }
        return r;
    }

    public static void main(String[] args) {
        //8589934592
        System.out.println(KEY_COUNT);
        Set<String> strings = new HashSet<>();

        //for (long i = 13010000000L; i < 13020000000L; i++) {
        //    strings.add(hashKey(CommonUtil.md5(i + "")));
        //}

        System.out.println(strings.size());

        System.out.println(hashKey(CommonUtil.md5("13100000001")));
        System.out.println(hashKey(CommonUtil.md5("15528964253")));

        System.out.println(BKDRHash(CommonUtil.md5("13100000001")));
        System.out.println(BKDRHash(CommonUtil.md5("15528964253")));
    }
}
