package com.example.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationManager {
    private List<Notifier> notifers ;
    List<Notifier> notifiers = new ArrayList<>();
    @Autowired
    public NotificationManager(List<Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    public void notifyAll(String message){
        for(Notifier not : notifiers) {
            not.send(message);
        }
    }
}
