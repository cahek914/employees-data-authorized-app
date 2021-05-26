package org.kao914.springboot.thymeleaf.service;

import org.kao914.springboot.thymeleaf.repository.EmployeeRepository;
import org.kao914.springboot.thymeleaf.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<Employee> searchBy(String request) {

        String name = request.trim();

        List<Employee> result = null;

        if (name.length() > 0) {
             result = employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(name, name);
        }
        else {
            result = findAll();
        }
        return result;
    }

    @Override
    public Employee findById(int id) {

        Optional<Employee> result = employeeRepository.findById(id);

        Employee employee = null;

        if (result.isPresent()) {
            employee = result.get();
        }
        else {
            throw new RuntimeException("Employee with id " + id + " was not found!");
        }
        return employee;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
