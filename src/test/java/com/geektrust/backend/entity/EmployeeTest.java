package com.geektrust.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.geektrust.backend.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    private Employee employee;

    @BeforeEach
    public void setUp(){
        employee = new Employee("EMPLOYEE1", "EMPLOYEE1@GMAIL.COM", "OFFERING-JAVA-INSTRUCTOR1");
        String courseRegistrationId = "REG-COURSE-EMPLOYEE1-JAVA";
        employee.setCourseRegistrationId(courseRegistrationId);
    }
    
    @Test
    public void testGetters(){
        assertEquals("EMPLOYEE1",employee.getEmployeeName());
        assertEquals("EMPLOYEE1@GMAIL.COM",employee.getEmail());
        assertEquals("OFFERING-JAVA-INSTRUCTOR1", employee.getCourseOfferingId());
    }

    @Test
    public void testSetAndGetRegsistrationId(){
        
        assertEquals("REG-COURSE-EMPLOYEE1-JAVA",employee.getCourseRegistrationId());
    }

    @Test
    public void testToString(){
        String expected = "Employee[CourseRegistrationId = REG-COURSE-EMPLOYEE1-JAVA, EmployeeName = EMPLOYEE1, EmailId = EMPLOYEE1@GMAIL.COM, CourseOfferingId = OFFERING-JAVA-INSTRUCTOR1]";
        assertEquals(expected,employee.toString());
    }
}
