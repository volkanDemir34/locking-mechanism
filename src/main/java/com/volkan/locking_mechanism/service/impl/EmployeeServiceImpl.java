package com.volkan.locking_mechanism.service.impl;


import com.volkan.locking_mechanism.exception.CustomException;
import com.volkan.locking_mechanism.mapper.EmployeeMapper;
import com.volkan.locking_mechanism.model.Employee;
import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import com.volkan.locking_mechanism.repository.EmployeeRepository;
import com.volkan.locking_mechanism.service.EmployeeService;
import com.volkan.locking_mechanism.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;


    @Override
    public Employee findByIdWithPessimisticLock(Long id) {
        Employee employee = employeeRepository.findAllById(id);
        ThreadUtil.sleep(10000);

        return employee;
    }

    @Override
    public Employee findByIdWithOptimisticLock(Long id) {
        Employee employee = employeeRepository.getAllById(id);
        ThreadUtil.sleep(4000);
        return employee;
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new CustomException("Employee not found"));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void insertEmployee(EmployeeRequest employeeRequest) {
        employeeRepository.save(employeeMapper.sourceToDestination(employeeRequest));
    }

    @Override
    public void updateEmployee(long id, EmployeeRequest employeeRequest) {
        System.out.println("Update staring ");
        Employee employee = findById(id);
        employee.setEmployeeRequest(employeeRequest);
        employee.setId(id);
        employeeRepository.save(employee);

        System.out.println("Update ending ");


    }

    @Override
    public void deleteEmployeeById(long id) {
        Employee employee = findById(id);
        if (employee == null)
            throw new CustomException("Employee not found");
        else
            employeeRepository.deleteById(id);
    }

    @Override
    public void deleteAllEmployee() {
        employeeRepository.deleteAll();

    }

    @Override
    //@Cacheable(value = "age")
    public Employee findByAge(int age) {
        return employeeRepository.findByAge(age);
    }


}
