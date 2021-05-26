package org.kao914.springboot.thymeleaf.service;

import org.kao914.springboot.thymeleaf.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    List<Employee> searchBy(String request);

    Employee findById(int id);

    void save(Employee employee);

    void deleteById(int id);



}
