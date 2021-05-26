package org.kao914.springboot.thymeleaf.repository;

import org.kao914.springboot.thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // add a method to sort by last name
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
    List<Employee> findAllByOrderByLastNameAsc();

    List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String firstName, String lastName);
}
