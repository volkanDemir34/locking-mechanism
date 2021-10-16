package com.volkan.locking_mechanism.repository;

import com.volkan.locking_mechanism.model.Employee;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Employee findAllById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    Employee getAllById(Long id);


    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    List<Employee> findAll();


    Employee findByAge(int age);


}
