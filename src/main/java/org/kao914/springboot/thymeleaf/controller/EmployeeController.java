package org.kao914.springboot.thymeleaf.controller;

import org.kao914.springboot.thymeleaf.entity.Employee;
import org.kao914.springboot.thymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String getEmployees(Model model) {
        // get employees from database
        List<Employee> employees = employeeService.findAll();
        // add employees to the spring model
        model.addAttribute("employees", employees);

        return "/employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        // create model attribute to bind form data
        Employee employee = new Employee();

        model.addAttribute("employee", employee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
        // get the employee from the service
        Employee employee = employeeService.findById(id);
        // set employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save the employees
        employeeService.save(employee);
        // use a redirect to prevent duplicate submissions
        // Post/Redirect/Get pattern
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id) {
        // delete the employee
        employeeService.deleteById(id);
        // redirect to /employees/list
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String searchEmployee(@RequestParam("employeeName") String name, Model model) {

        List<Employee> employees = employeeService.searchBy(name);

        model.addAttribute("employees", employees);

        return "/employees/list-employees";
    }
}
