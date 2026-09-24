package com.example.Repository_Layer;

import com.example.model.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Profile("dev")
@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void save (Employee emp){
    employees.add(emp);
}

    @Override
    public Employee findById (int id){
    for(Employee emp : employees){
        if(id == emp.getId()){
            return  emp ;
        }
    }
    return null ;
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    @Override
    public  void print(){
        System.out.println("in dev");
    }
    @PostConstruct
    public void init() {
        System.out.println("InMemoryEmployeeRepository initialized");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("InMemoryEmployeeRepository destroyed");
    }
}
