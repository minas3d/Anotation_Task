package com.example.Repository_Layer;

import com.example.model.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Repository
@Profile("prod")

public class FileBackedEmployeeRepository implements EmployeeRepository {

    private static final String FILE_PATH = "employees-data.csv";

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void save(Employee employee) {
        employees.add(employee);
        appendToFile(employee);
    }

    @Override
    public Employee findById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    private void appendToFile(Employee employee) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(employee.getId() + "," + employee.getName() + "," +
                    employee.getDepartment() + "," + employee.getSalary() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("[FileBackedEmployeeRepository] could not write to " + FILE_PATH + ": " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length == 4) {
                    employees.add(new Employee(
                            Integer.parseInt(parts[0].trim()),
                            parts[1].trim(),
                            parts[2].trim(),
                            Double.parseDouble(parts[3].trim())
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("[FileBackedEmployeeRepository] could not read " + FILE_PATH + ": " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        loadFromFile();
        System.out.println("[lifecycle] FileBackedEmployeeRepository initialized (prod profile), loaded "
                + employees.size() + " employee(s) from " + FILE_PATH);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[lifecycle] FileBackedEmployeeRepository destroyed");
    }
    @Override
    public  void print(){
        System.out.println("in prod");
    }
}
