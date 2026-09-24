package com.example.Repository_Layer;

import com.example.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    public void save (Employee emp);
    public Employee findById (int id);
    public List <Employee > findAll();
    public  void print();
}
