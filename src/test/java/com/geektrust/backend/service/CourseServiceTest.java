package com.geektrust.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.dto.CourseAllotmentDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.repository.CourseRepository;
import com.geektrust.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    private CourseService courseService;
    @BeforeEach
    public void setUp(){
        courseService = new CourseService(courseRepository,employeeRepository);
    }
    @Test
    public void addCourseWithEmptyInputFeilds_shouldReturnInputDataExpection(){
        String courseName = " ";
        String instructorName = " ";
        String date = "16232020";
        int minEmployees = 1;
        int maxEmployees = 2;
        
        assertThrows(InputDataException.class,()->courseService.addCourse(courseName, instructorName, date, minEmployees, maxEmployees));
    }

    // @Test
    // public void testaddCourseWithMaxEmployeesGreaterThanMinEmployees(){
    //     String courseName = "Java Programming";
    //     String instructorName = "John Instructor";
    //     String date = "01012024";
    //     int minEmployees = 2;
    //     int maxEmployees = 0;

    //     assertThrows(InputDataException.class,()->courseService.addCourse(courseName, instructorName, date, minEmployees, maxEmployees));
    // }

    // @Test
    // public void testAddCourseWithZeroMinEmployees_shouldReturnInputDataExcetion(){
    //     String courseName = "Java Programming";
    //     String instructorName = "John Instructor";
    //     String date = "01012024";
    //     int minEmployees = 0;
    //     int maxEmployees = 2;

    //     assertThrows(InputDataException.class,()->courseService.addCourse(courseName, instructorName, date, minEmployees, maxEmployees));
    // }

    @Test
    public void addCourseWithValidData_ShouldReturnCourseOfferingId(){
        
        String courseName = "Java Programming";
        String instructorName = "Jhon Instructor";
        String courseStartDate = "01012024";
        int minEmployees = 1;
        int maxEmployees = 2;
        String courseOfferingId = "OFFERING"+"-"+courseName.toUpperCase()+"-"+instructorName.toUpperCase();
        when(courseRepository.save(any(Course.class))).thenReturn(courseOfferingId);
        assertEquals(courseOfferingId, courseService.addCourse(courseName, instructorName, courseStartDate, minEmployees, maxEmployees));
        verify(courseRepository,times(1)).save(any(Course.class));
    }

    @Test
    public void allotCourseWithInvalidData_shouldReturnInputDataException(){
        String courseName = "Java Programming";
        String instructorName = "Jhon Instructor";
        String courseOfferingId = "OFFERING"+"-"+courseName.toUpperCase()+"-"+instructorName.toUpperCase();
        assertThrows(InputDataException.class, ()->courseService.allotCourse(courseOfferingId));
        verify(courseRepository,times(1)).findById(anyString());
    }
    
    @Test
    public void allotCourseWithValidData_shouldReturnCourseAllotmentDto(){
        List<Employee> employees = new ArrayList<>();
        String courseName = "Java";
        String instructorName = "Jhon";
        String courseOfferingId = "OFFERING-JAVA-JHON";
        String courseStartDate = "01012024";
        int minEmployees = 1;
        int maxEmployees = 3;

        Employee employee1 = new Employee("employee1", "employee1@example.com", "OFFERIN-JAVA-JHON");
        Employee employee2 = new Employee("employee2", "employee2@example.com", "OFFERIN-JAVA-JHON");
        Employee employee3 = new Employee("employee3", "employee3@example.com", "OFFERIN-JAVA-JHON");

        employee1.setCourseRegistrationId("REG-COURSE-EMPLOYEE1-JAVA");
        employee2.setCourseRegistrationId("REG-COURSE-EMPLOYEE2-JAVA");
        employee3.setCourseRegistrationId("REG-COURSE-EMPLOYEE3-JAVA");
        
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        Course course = new Course(courseName, instructorName, courseStartDate, minEmployees, maxEmployees);
        course.setCourseOfferingId(courseOfferingId);
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE1-JAVA");
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE2-JAVA");
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE3-JAVA");

        when(courseRepository.findById(courseOfferingId)).thenReturn(Optional.ofNullable(course));
        when(employeeRepository.findAllByCourseOfferingId(courseOfferingId)).thenReturn(employees);

        List<CourseAllotmentDto> actualCourseAllotmentDto = courseService.allotCourse(courseOfferingId);
        assertEquals(3, actualCourseAllotmentDto.size());
        assertEquals("REG-COURSE-EMPLOYEE1-JAVA", actualCourseAllotmentDto.get(0).getCourseRegistrationId());
        assertEquals("Java", actualCourseAllotmentDto.get(0).getCourseName());
        assertEquals("OFFERING-JAVA-JHON",actualCourseAllotmentDto.get(0).getCourseOfferingId());
        assertEquals("employee1@example.com",actualCourseAllotmentDto.get(0).getEmailId());
        assertEquals("Jhon", actualCourseAllotmentDto.get(0).getInstructor());
        assertEquals("01012024", actualCourseAllotmentDto.get(0).getCourseOfferingDate());
        assertEquals("CONFIRMED", actualCourseAllotmentDto.get(0).getStatus());       

        assertEquals("REG-COURSE-EMPLOYEE2-JAVA", actualCourseAllotmentDto.get(1).getCourseRegistrationId());
        assertEquals("Java", actualCourseAllotmentDto.get(1).getCourseName());
        assertEquals("OFFERING-JAVA-JHON",actualCourseAllotmentDto.get(1).getCourseOfferingId());
        assertEquals("employee2@example.com",actualCourseAllotmentDto.get(1).getEmailId());
        assertEquals("Jhon", actualCourseAllotmentDto.get(1).getInstructor());
        assertEquals("01012024", actualCourseAllotmentDto.get(1).getCourseOfferingDate());
        assertEquals("CONFIRMED", actualCourseAllotmentDto.get(1).getStatus());  

        assertEquals("REG-COURSE-EMPLOYEE3-JAVA", actualCourseAllotmentDto.get(2).getCourseRegistrationId());
        assertEquals("Java", actualCourseAllotmentDto.get(2).getCourseName());
        assertEquals("OFFERING-JAVA-JHON",actualCourseAllotmentDto.get(2).getCourseOfferingId());
        assertEquals("employee3@example.com",actualCourseAllotmentDto.get(2).getEmailId());
        assertEquals("Jhon", actualCourseAllotmentDto.get(2).getInstructor());
        assertEquals("01012024", actualCourseAllotmentDto.get(2).getCourseOfferingDate());
        assertEquals("CONFIRMED", actualCourseAllotmentDto.get(2).getStatus());  

        verify(courseRepository,times(1)).findById(anyString());
        verify(employeeRepository,times(1)).findAllByCourseOfferingId(anyString());
    }
}
