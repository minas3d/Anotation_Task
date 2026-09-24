package com.example.Service_Layer;

import com.example.audit.AuditLogger;
import com.example.model.Employee;
import com.example.Repository_Layer.EmployeeRepository;
import com.example.notify.NotificationManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EmployeeServiceImpl implements  EmployeeService{
    private final EmployeeRepository employeeRepository ;
    private final EmployeeValidator  employeeValidator ;
    private final NotificationManager notificationManager;
    private final ObjectProvider<AuditLogger> auditLoggerProvider;

    @Value("${company.name}")
    private String companyName;

    @Value("${company.currency}")
    private String currency;

    @Value("${notification.retry-count}")
    private int notificationRetryCount;

    @Value("${raise.max-percentage}")
    private double maxRaisePercentage;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeValidator employeeValidator, NotificationManager notificationManager, ObjectProvider<AuditLogger> auditLoggerProvider) {
        this.employeeRepository = employeeRepository;
        this.employeeValidator = employeeValidator;
        this.notificationManager = notificationManager;
        this.auditLoggerProvider = auditLoggerProvider;
    }

    @PostConstruct
    public void init() {
        System.out.println("EmployeeService initialized");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("EmployeeService destroyed");
    }
    @Override
    public void addEmployee (Employee emp){
        try {
            employeeRepository.save(emp);
        }
        catch (InvalidEmployeeException ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public Employee getEmployeeById (int id){
        for(Employee emp : employeeRepository.findAll()){
            if(id == emp.getId()){
                return  emp ;
            }
        }
        return null ;
    }
    @Override
    public List <Employee > getAllEmployees()
    {
        return employeeRepository.findAll();
    };

    @Override
  public void giveRaise(int id, double percentage) {
        Employee employee = employeeRepository.findById(id);

        if (employee != null) {
            double currentSalary = employee.getSalary();

            double raise = currentSalary * percentage / 100;

            employee.setSalary(currentSalary + raise);
        }
    };

    @Override
    public void printProperties() {
        System.out.println("  company.name  = " + companyName);
        System.out.println("  company.currency  = " + currency);
        System.out.println("  notification.retry-count = " + notificationRetryCount);
        System.out.println("  raise.max-percentage   = " + maxRaisePercentage);
    }

}
