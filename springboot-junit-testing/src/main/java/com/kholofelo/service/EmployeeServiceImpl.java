package com.kholofelo.service;

import com.kholofelo.entity.Employee;
import com.kholofelo.exception.ResourceNotFoundException;
import com.kholofelo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) throws ResourceNotFoundException {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent()){
            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.empty();
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee) {
        return null;
    }

    @Override
    public void deleteEmployee(long id) {

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
