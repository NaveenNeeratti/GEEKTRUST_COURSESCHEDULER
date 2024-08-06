package com.geektrust.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp(){
        Map<String,Employee> employeeMap = new HashMap<>();
        Employee employee1 = new Employee("Employee1", "employee1@gmail.com", "OFFERING-JAVA-INSTRUCTOR1");
        Employee employee2 = new Employee("Employee2", "employee2@gmail.com", "OFFERING-JAVA-INSTRUCTOR1");
        Employee employee3 = new Employee("Employee3", "employee3@gmail.com", "OFFERING-PYTHON-INSTRUCTOR2");
        Employee employee4 = new Employee("Employee4", "employee4@gmail.com", "OFFERING-PYTHON-INSTRUCTOR2");
        Employee employee5 = new Employee("Employee5", "employee5@gmail.com", "OFFERING-DATA SCIENCE-INSTRUCTOR3");
        Employee employee6 = new Employee("Employee6", "employee6@gmail.com","OFFERING-DATA SCIENCE-INSTRUCTOR3");
        employee1.setCourseRegistrationId("REG-COURSE-EMPLOYEE1-JAVA");
        employee2.setCourseRegistrationId("REG-COURSE-EMPLOYEE2-JAVA");
        employee3.setCourseRegistrationId("REG-COURSE-EMPLOYEE3-PYTHON");
        employee4.setCourseRegistrationId("REG-COURSE-EMPLOYEE4-PYTHON");
        employee5.setCourseRegistrationId("REG-COURSE-EMPLOYEE5-DATA SCIENCE");
        employee6.setCourseRegistrationId("REG-COURSE-EMPLOYEE6-DATA SCIENCE");

        employeeMap.put("REG-COURSE-EMPLOYEE1-JAVA",employee1);
        employeeMap.put("REG-COURSE-EMPLOYEE2-JAVA",employee2);
        employeeMap.put("REG-COURSE-EMPLOYEE3-PYTHON",employee3);
        employeeMap.put("REG-COURSE-EMPLOYEE4-PYTHON",employee4);
        employeeMap.put("REG-COURSE-EMPLOYEE5-DATA SCIENCE",employee5);
        employeeMap.put("REG-COURSE-EMPLOYEE6-DATA SCIENCE",employee6);

        employeeRepository = new EmployeeRepository(employeeMap);
    }

    @Test
    public void findById(){
        Optional<Employee> employee = employeeRepository.findById("REG-COURSE-EMPLOYEE6-DATA SCIENCE");

        assertEquals("Employee6",employee.get().getEmployeeName());
        assertEquals("employee6@gmail.com", employee.get().getEmail());
        assertEquals("OFFERING-DATA SCIENCE-INSTRUCTOR3", employee.get().getCourseOfferingId());
    }

    @Test
    public void save(){
        String courseName = "Java";
        String employeeName = "employee7";
        String emailId = "employee7@gmail.com";
        String courseOfferingId = "OFFERING-JAVA-INSTRUCTOR1";

        String expectedEmployeeRegistrationId = "REG-COURSE-EMPLOYEE7-JAVA";
        Employee employee = new Employee(employeeName, emailId, courseOfferingId);

        String actualEmployeeRegistrationId = employeeRepository.save(employee, courseName);
        assertEquals(expectedEmployeeRegistrationId, actualEmployeeRegistrationId);
        assertEquals(expectedEmployeeRegistrationId, employeeRepository.findById(expectedEmployeeRegistrationId).get().getCourseRegistrationId());
    }
    
    @Test
    public void findAll(){
    List<Employee> employees = employeeRepository.findAll();
    
    assertEquals(6, employees.size());
    assertTrue(employees.stream().anyMatch(e -> e.getEmployeeName().equals("Employee1")));
    assertTrue(employees.stream().anyMatch(e -> e.getEmployeeName().equals("Employee2")));
    assertTrue(employees.stream().anyMatch(e -> e.getCourseRegistrationId().equals("REG-COURSE-EMPLOYEE3-PYTHON")));
    assertTrue(employees.stream().anyMatch(e -> e.getCourseRegistrationId().equals("REG-COURSE-EMPLOYEE5-DATA SCIENCE")));
    assertTrue(employees.stream().anyMatch(e -> e.getCourseOfferingId().equals("OFFERING-JAVA-INSTRUCTOR1")));
    assertTrue(employees.stream().anyMatch(e -> e.getCourseOfferingId().equals("OFFERING-DATA SCIENCE-INSTRUCTOR3")));
    }

    @Test
    public void findAllByCourseOfferingId() {
        String testCourseOfferingId = "OFFERING-JAVA-INSTRUCTOR1";
        List<Employee> employees = employeeRepository.findAllByCourseOfferingId(testCourseOfferingId);
        assertNotNull(employees);

        assertEquals(2, employees.size());

        assertTrue(employees.stream().allMatch(e -> e.getCourseOfferingId().equals(testCourseOfferingId)));

    }

}
