package com.volkan.locking_mechanism.service;

import com.volkan.locking_mechanism.model.Employee;
import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Transactional
public interface EmployeeService {

    Employee findByIdWithPessimisticLock(Long id);

    Employee findByIdWithOptimisticLock(Long id);

    Employee findById(Long id);

    List<Employee> findAll();

    void insertEmployee(EmployeeRequest employeeRequest);

    void updateEmployee(long id, EmployeeRequest employeeRequest);

    void deleteEmployeeById(long id);

    void deleteAllEmployee();

    Employee findByAge(int age);

}
