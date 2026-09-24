package com.example;

import com.example.config.AppConfig;
import com.example.Service_Layer.EmployeeService;
import com.example.Service_Layer.EmployeeServiceImpl;
import com.example.audit.AuditLogger;
import com.example.model.Employee;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppConfig.class);
        context.refresh();

        EmployeeService employeeService = context.getBean(EmployeeService.class);

        System.out.println("\n===== ADD VALID EMPLOYEE =====");
        Employee employee1 = new Employee(1, "Mina", "IT", 10000.0);
        employeeService.addEmployee(employee1);

        System.out.println("\n===== ADD INVALID EMPLOYEE (blank name) =====");
        try {
            Employee invalidEmployee = new Employee(2, "   ", "IT", 8000.0);
            employeeService.addEmployee(invalidEmployee);
        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
        }

        System.out.println("\n===== ADD INVALID EMPLOYEE (negative salary) =====");
        try {
            Employee invalidEmployee = new Employee(3, "Sara", "HR", -500.0);
            employeeService.addEmployee(invalidEmployee);
        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
        }

        System.out.println("\n===== GIVE VALID RAISE (10%, within limit) =====");
        employeeService.giveRaise(1, 10);
        System.out.println("New salary: " + employeeService.getEmployeeById(1).getSalary());

        System.out.println("\n===== GIVE EXCEEDED RAISE (30%, above raise.max-percentage) =====");
        try {
            employeeService.giveRaise(1, 30);
        } catch (Exception e) {
            System.out.println("Raise rejected: " + e.getMessage());
        }

        System.out.println("\n===== PROTOTYPE AUDIT LOGGER (requested directly from the container) =====");
        AuditLogger logger1 = context.getBean(AuditLogger.class);
        AuditLogger logger2 = context.getBean(AuditLogger.class);
        AuditLogger logger3 = context.getBean(AuditLogger.class);
        System.out.println("logger1 == logger2 : " + (logger1 == logger2));
        System.out.println("logger2 == logger3 : " + (logger2 == logger3));
        System.out.println("(each addEmployee/giveRaise call above also silently pulled its own fresh "
                + "AuditLogger via ObjectProvider - see the [AUDIT] lines)");

        System.out.println("\n===== ALL EMPLOYEES =====");
        employeeService.getAllEmployees().forEach(System.out::println);

        System.out.println("\n===== @VALUE PROPERTIES =====");
        employeeService.printProperties();

        System.out.println("\n===== CLOSING CONTEXT =====");
        context.close();
        System.out.println("Context closed.");
    }
}