package com.example.notify;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)

public class PushNotifier implements Notifier{
    @Override
    public  void send(String message){
        System.out.println("PushNotifier" + message);
    }
}
