package com.volkan.locking_mechanism.controller;


import com.volkan.locking_mechanism.model.Employee;
import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import com.volkan.locking_mechanism.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EntityManagerFactory entityManagerFactory;


    @PostMapping("/insert")
    public void insertEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.insertEmployee(employeeRequest);
    }

    @GetMapping("/get/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @GetMapping("/getAge/{age}")
    public Employee findByAge(@PathVariable int age ) {
        return employeeService.findByAge(age);
    }

    @GetMapping("/getWithLocking/{id}")
    public Employee findEmployeeByIdWithLocking(@PathVariable Long id) {
        return employeeService.findByIdWithPessimisticLock(id);
    }

    @GetMapping("/getWithOptimisticLocking/{id}")
    public Employee findEmployeeByIdWithOptimisticLocking(@PathVariable Long id) {
        return employeeService.findByIdWithOptimisticLock(id);
    }

    @GetMapping("/getAll")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }

    @PutMapping("/update/{id}")
    public void updateEmployee(@PathVariable Long id, EmployeeRequest request) {
         employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllEmployees() {
        employeeService.deleteAllEmployee();
    }

}
