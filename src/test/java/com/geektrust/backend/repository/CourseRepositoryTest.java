package com.geektrust.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseRepositoryTest {
    
    private CourseRepository courseRepository;

    @BeforeEach
    public void setUp(){

        Map<String,Course> courseMap = new HashMap<>();
        Course course1 = new Course("Java", "Instructor1", "01012024", 3, 6);
        Course course2 = new Course("Python", "Instructor2", "01022024", 1, 3);
        Course course3 = new Course("Data Science", "Instructor3", "01032024", 2, 5);

        String course1OfferingId = "OFFERING-JAVA-INSTRUCTOR1";
        String course2OfferingId = "OFFERING-PYTHON-INSTRUCTOR2";
        String course3OfferingId = "OFFERING-DATA SCIENCE-INSTRUCTOR3";

        course1.setCourseOfferingId(course1OfferingId);
        course2.setCourseOfferingId(course2OfferingId);
        course3.setCourseOfferingId(course3OfferingId);

        course1.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE1-JAVA");
        course1.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE2-JAVA");

        course2.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE3-PYTHON");
        course2.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE4-PYTHON");

        course3.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE5-DATA SCIENCE");
        course3.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE6-DATA SCIENCE");

        courseMap.put("OFFERING-DATA SCIENCE-INSTRUCTOR3",course3);
        courseMap.put("OFFERING-PYTHON-INSTRUCTOR2",course2);
        courseMap.put("OFFERING-JAVA-INSTRUCTOR1",course1);

        courseRepository = new CourseRepository(courseMap);
    }

    @Test
    public void findById(){
        String courseOfferingId = "OFFERING-JAVA-INSTRUCTOR1";
        Optional<Course> course = courseRepository.findById(courseOfferingId);
        
        assertEquals("Java",course.get().getCourseName());
        assertEquals("Instructor1",course.get().getIntructorName());
        assertEquals("01012024",course.get().getCourseOfferingDate());
        assertEquals(3,course.get().getMinEmployees());
        assertEquals(6,course.get().getMaxEmployees());
    }

    @Test
    public void findAll(){
        List<Course> courses = courseRepository.findAll();

        assertEquals("Data Science",courses.get(0).getCourseName());
        assertEquals("Instructor3",courses.get(0).getIntructorName());
        assertEquals("01032024",courses.get(0).getCourseOfferingDate());
        assertEquals(2,courses.get(0).getMinEmployees());
        assertEquals(5,courses.get(0).getMaxEmployees());
        assertEquals(2, courses.get(0).getRegisteredEmployees().size());

        assertEquals("Python",courses.get(1).getCourseName());
        assertEquals("Instructor2",courses.get(1).getIntructorName());
        assertEquals("01022024",courses.get(1).getCourseOfferingDate());
        assertEquals(1,courses.get(1).getMinEmployees());
        assertEquals(3,courses.get(1).getMaxEmployees());
        assertEquals(2, courses.get(1).getRegisteredEmployees().size());

        assertEquals("Java",courses.get(2).getCourseName());
        assertEquals("Instructor1",courses.get(2).getIntructorName());
        assertEquals("01012024",courses.get(2).getCourseOfferingDate());
        assertEquals(3,courses.get(2).getMinEmployees());
        assertEquals(6,courses.get(2).getMaxEmployees());
        assertEquals(2, courses.get(2).getRegisteredEmployees().size());
    }
    @Test
    public void save(){
        String courseName = "Data Structures";
        String instructorName = "Smith";
        String courseStartDate = "12012024";
        int minEmployees = 3;
        int maxEmployees = 5;

        Course course4 = new Course(courseName, instructorName, courseStartDate, minEmployees, maxEmployees);
        course4.setCourseStatus(CourseStatus.COURSE_NOTALLOTED);

        String expectedCourseOfferingId = "OFFERING-DATA STRUCTURES-SMITH";
        assertEquals(expectedCourseOfferingId,courseRepository.save(course4));
        assertEquals(expectedCourseOfferingId,courseRepository.findById(expectedCourseOfferingId).get().getCourseOfferingId());
    }

    @Test
    public void delete(){
        courseRepository.delete("OFFERING-JAVA-INSTRUCTOR1");
        List<Course> courses = courseRepository.findAll();
        for(Course course : courses){
            assertNotEquals("OFFERING-JAVA-INSTRUCTOR1", course.getCourseOfferingId());
        }
    }

}
