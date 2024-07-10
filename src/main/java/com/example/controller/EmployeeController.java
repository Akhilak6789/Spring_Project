package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Display employee form for adding new employee
    @GetMapping("/create")
    public String createEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form"; // Thymeleaf template name (employee-form.html)
    }

    // Display employee form for editing existing employee
    @GetMapping("/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
        } else {
            model.addAttribute("employee", new Employee()); // or handle not found case
        }
        return "employee-form"; // Thymeleaf template name (employee-form.html)
    }

    // Handle submission of employee form (add or update)
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.addOrUpdateEmployee(employee);
        return "redirect:/employees/create"; // Redirect to form after saving
    }

    // Delete employee (optional, remove if not needed)
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/create"; // Redirect to form after deleting
    }
}
