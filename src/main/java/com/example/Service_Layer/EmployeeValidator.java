package com.example.Service_Layer;

import com.example.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator
{
   public void validate(Employee emp ) {

       if (emp.getName() == null) {
       throw new InvalidEmployeeException("name in valid to be  null");
       }
       if (emp.getSalary() <0 ) {
           throw new InvalidEmployeeException("Salary  in valid to be  Negative ");
       }
       if (emp.getDepartment() == null) {
           throw new InvalidEmployeeException("Department in valid to be  null");
       }

   }


}
