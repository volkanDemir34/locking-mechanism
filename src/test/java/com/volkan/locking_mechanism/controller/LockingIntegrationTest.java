package com.volkan.locking_mechanism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import com.volkan.locking_mechanism.service.EmployeeService;
import com.volkan.locking_mechanism.util.ThreadUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@EnableCaching
class LockingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService service;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    CacheManager cacheManager;

    @Test
    void insertEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Volkan");
        employeeRequest.setAge(30);
        mockMvc.perform(post("/employee/insert").
                contentType("application/json").
                content(objectMapper.writeValueAsString(employeeRequest))
        ).andExpect(status().isOk());
        service.findById(1l);
        service.findByAge(30);
        service.findByAge(30);
        Assertions.assertEquals("Volkan", service.findById(1l).getName());

    }

    @Test
    void pessimisticLockTest() {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setName("Volkan");
        employeeRequest.setAge(30);
        service.insertEmployee(employeeRequest);
        employeeRequest.setAge(31);
        CompletableFuture.runAsync(() -> {
            ThreadUtil.sleep(2000);
            service.updateEmployee(1, employeeRequest);

        });
        Assertions.assertThrows(ObjectOptimisticLockingFailureException.class,
                () -> service.findByIdWithOptimisticLock(1l));

    }
}