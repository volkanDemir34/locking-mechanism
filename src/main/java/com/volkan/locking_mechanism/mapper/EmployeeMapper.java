package com.volkan.locking_mechanism.mapper;

import com.volkan.locking_mechanism.model.Employee;
import com.volkan.locking_mechanism.model.dto.EmployeeRequest;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {

    Employee sourceToDestination(EmployeeRequest source);
}
