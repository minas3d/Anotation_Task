package com.example.config;
import com.example.audit.AuditLogger;
import com.example.model.Employee;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.example")
@PropertySource("classpath:application.properties")

public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AuditLogger auditLogger() {
        return new AuditLogger();


    }
}