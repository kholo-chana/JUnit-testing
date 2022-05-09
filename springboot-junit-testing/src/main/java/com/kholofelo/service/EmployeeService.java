package com.kholofelo.service;

import com.kholofelo.entity.Employee;
import com.kholofelo.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee) throws ResourceNotFoundException;
    List<Employee> getAllEmployee();
    Optional<Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee updateEmployee);
    void deleteEmployee(long id);

}
