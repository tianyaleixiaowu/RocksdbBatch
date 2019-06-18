package com.example.demo;

import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
public class MyEvent extends ApplicationEvent {

    public MyEvent(List<Object> source) {
        super(source);
    }
}
