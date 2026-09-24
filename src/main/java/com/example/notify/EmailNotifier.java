package com.example.notify;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class EmailNotifier implements Notifier{
    @Override


    public  void send(String message){
        System.out.println("EmailNotifier" + message);
    }

}
