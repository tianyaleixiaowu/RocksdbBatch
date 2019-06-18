package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
public class Test {
    public static void main(String[] args) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 105; i ++) {
            keys.add(i + "");
        }
        int batchSize = 10;

        for (int i = 0; i < keys.size() / batchSize + 1; i++) {
            List<String> list;
            if (batchSize * (i + 1) > keys.size()) {
                list = keys.subList(batchSize * i, keys.size());
            } else {
               list = keys.subList(batchSize * i, batchSize * (i + 1));
            }
            System.out.println(list);

        }
    }
}
