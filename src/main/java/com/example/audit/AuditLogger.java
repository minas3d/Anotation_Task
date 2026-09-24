package com.example.audit;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Scope("prototype")
public class AuditLogger {

    @PostConstruct
    public void init() {
        System.out.println("] AuditLogger instance created");
    }

    public void log(String message) {
        System.out.println("  [AUDIT] " + LocalDateTime.now() + " - " + message);
    }

    @PreDestroy
    public void destroy() {

        System.out.println("[lifecycle] AuditLogger instance destroyed");
    }
}