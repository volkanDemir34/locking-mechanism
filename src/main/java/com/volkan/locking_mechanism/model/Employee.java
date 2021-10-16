package com.volkan.locking_mechanism.model;

import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NaturalId
    private int age;

    @Version
    private long version;

    public void setEmployeeRequest(EmployeeRequest employeeRequest) {
        this.name = employeeRequest.getName() == null ? this.name : employeeRequest.getName();
        this.age = employeeRequest.getAge() == 0 ? this.age : employeeRequest.getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}
