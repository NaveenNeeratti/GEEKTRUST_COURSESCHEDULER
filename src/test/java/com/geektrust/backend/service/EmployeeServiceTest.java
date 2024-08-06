package com.geektrust.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseStatus;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.exception.CourseAllotedException;
import com.geektrust.backend.exception.CourseFullException;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.repository.CourseRepository;
import com.geektrust.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    private Course course;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        String employeeName = "EMPLOYEE1";
        String email = "EMPLOYEE1@GMAIL.COM";
        String courseName = "JAVA";
        String instructorName = "JHON";
        String courseOfferingId = "OFFERING-JAVA-JHON";
        String courseStartDate = "01012024";
        int minEmployees = 1;
        int maxEmployees = 3;

        course = new Course(courseName, instructorName, courseStartDate, minEmployees, maxEmployees);
        course.setCourseOfferingId(courseOfferingId);
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE1-JAVA");
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE2-JAVA");
        employee = new Employee(employeeName, email, courseOfferingId);
        employee.setCourseRegistrationId("REG-COURSE-EMPLOYEE1-JAVA");
        employeeService = new EmployeeService(courseRepository, employeeRepository);
    }
    
    @Test
    public void testRegisterCourseWithInvalidInput(){
        String emailId = "JHON@GMAIL.COM";
        String courseOfferingId = "";

        assertThrows(InputDataException.class,()->employeeService.registerCourse(emailId,courseOfferingId));
    }

    @Test
    public void testRegisterCourseWithInvalidEmailId(){
        String emailId = "JHON";
        String courseOfferingId = "OFFERING-JAVA-JHON";
        assertThrows(InputDataException.class,()->employeeService.registerCourse(emailId, courseOfferingId));

    }
    @Test
    public void testRegisterCourseWithCourseHavingMaxEmployees(){
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE3-JAVA");
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        
        assertThrows(CourseFullException.class, ()->employeeService.registerCourse("EMPLOYEE4@GMAIL.COM","OFFERING-JAVA-JHON"));
        verify(courseRepository,times(1)).findById(anyString());
    }

    @Test
    public void testRegisterCourseWithCourseAlreadyAllotted(){
        course.setCourseStatus(CourseStatus.CONFIRMED);

        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));

        assertThrows(CourseAllotedException.class, ()->employeeService.registerCourse("EMPLOYEE3@GMAIL.COM", "OFFERING-JAVA-JHON"));
        verify(courseRepository,times(1)).findById(anyString());
    }

    @Test
    public void testRegisterCourseWithValidCourse(){
        course.setCourseStatus(CourseStatus.COURSE_NOTALLOTED);
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        when(employeeRepository.save(any(),anyString())).thenReturn("REG-COURSE-EMPLOYEE3-JAVA");
        EmployeeRequestStatusDto actual = employeeService.registerCourse("EMPLOYEE3@GMAIL.COM", "OFFERING-JAVA-JHON");
        ArgumentCaptor<Employee> employee = ArgumentCaptor.forClass(Employee.class);
        assertEquals("ACCEPTED", actual.getStatus());
        assertEquals("REG-COURSE-EMPLOYEE3-JAVA",actual.getCourseRegistrationId());

        verify(courseRepository,times(1)).findById(anyString());
        verify(employeeRepository,times(1)).save(employee.capture(), anyString());
        assertEquals("EMPLOYEE3", employee.getValue().getEmployeeName());
    }

    @Test
    public void testCancelRegistrationWithInvalidData(){
        String courseRegistrationId = "REG-COURSE-EMPLOYEE3-JAVA";
        assertThrows(InputDataException.class,()->employeeService.cancelRegistration(courseRegistrationId));
    }

    @Test
    public void testCancelRegistrationWithAllottedCourse(){
        course.setCourseStatus(CourseStatus.CONFIRMED);
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        EmployeeRequestStatusDto actualCourseRegistrationDto = employeeService.cancelRegistration("REG-COURSE-EMPLOYEE1-JAVA");
        assertEquals("CANCEL_REJECTED",actualCourseRegistrationDto.getStatus());
        assertEquals("REG-COURSE-EMPLOYEE1-JAVA", actualCourseRegistrationDto.getCourseRegistrationId());
        verify(employeeRepository,times(1)).findById(anyString());
        verify(employeeRepository,times(1)).findById(anyString());
    }

    @Test
    public void testCancelRegistrationWithValidCourse(){
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));
        when(courseRepository.findById(anyString())).thenReturn(Optional.of(course));
        EmployeeRequestStatusDto actualCourseRegistrationDto = employeeService.cancelRegistration("REG-COURSE-EMPLOYEE1-JAVA");
        assertEquals("CANCEL_ACCEPTED",actualCourseRegistrationDto.getStatus());
        assertEquals("REG-COURSE-EMPLOYEE1-JAVA", actualCourseRegistrationDto.getCourseRegistrationId());
        verify(employeeRepository,times(1)).findById(anyString());
        verify(employeeRepository,times(1)).findById(anyString());
    }
}
