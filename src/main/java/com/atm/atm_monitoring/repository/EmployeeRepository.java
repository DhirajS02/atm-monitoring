package com.atm.atm_monitoring.repository;

import com.atm.atm_monitoring.model.Employee;
import com.atm.atm_monitoring.model.Role;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Repository class for managing a list of {@link Employee} objects, simulating a database for
 * demo purposes. It stores a list of predefined banking employees with associated roles.
 */
@Repository
public class EmployeeRepository {

    /**
     * A list of employees that mimics the behavior of a database.
     * This list is initialized with sample employee data.
     */
    private final List<Employee> employees = new ArrayList<>();

    /**
     * Default constructor that initializes the repository with some predefined employees.
     * These employees represent the demo users of the banking system.
     */
    public EmployeeRepository() {
        // Create sample users and store them in the repository
        Employee employee1 = new Employee();
        employee1.setEmployeeCode("123456");
        employee1.setEmployeeName("Robert");
        employee1.setActivated(true);
        employee1.setPassword("password123456");
        employee1.setRoles(Set.of(new Role("ROLE_OWNER")));

        Employee employee2 = new Employee();
        employee2.setEmployeeCode("654321");
        employee2.setEmployeeName("Jane Smith");
        employee1.setActivated(true);
        employee2.setPassword("password654321");
        employee2.setRoles(Set.of(new Role("ROLE_USER")));

        employees.add(employee1);
        employees.add(employee2);
    }

    /**
     * Finds an employee by their employee code.
     *
     * @param employeeCode the unique code of the employee
     * @return the {@link Employee} with the matching employee code
     * @throws UsernameNotFoundException if no employee with the given code is found
     */
    public Employee findByEmployeeCode(String employeeCode) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeCode().equals(employeeCode))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found Associated With this JWT token"));
    }

}
