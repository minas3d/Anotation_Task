package com.example.Service_Layer;
import com.example.model.Employee;
import java.util.List;

public interface EmployeeService {

   public void addEmployee(Employee employee);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    void giveRaise(int id, double percentage);
    void printProperties();
}
