package com.example.demo;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wuweifeng wrote on 2019/6/17.
 */
@Component
public class ResultAction {

    @EventListener
    public void listen(MyEvent myEvent) {
        List<Object> phones = (List<Object>) myEvent.getSource();
        System.out.println("入库" + phones.size());
    }

}
