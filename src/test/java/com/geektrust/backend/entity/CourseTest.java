package com.geektrust.backend.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseTest {
    private Course course;

    @BeforeEach
    public void setUp() {
        course = new Course("JAVA", "INSTRUCTOR1", "15012024", 5, 20);
        course.setCourseOfferingId("OFFERING-JAVA-INSTRUCTOR1");
        course.setCourseStatus(CourseStatus.COURSE_NOTALLOTED);
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE1-JAVA");
        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE2-JAVA");
    }

    @Test
    public void testGetters() {
        assertEquals("OFFERING-JAVA-INSTRUCTOR1", course.getCourseOfferingId());
        assertEquals("JAVA", course.getCourseName());
        assertEquals("INSTRUCTOR1", course.getIntructorName());
        assertEquals("15012024", course.getCourseOfferingDate());
        assertEquals(5, course.getMinEmployees());
        assertEquals(20, course.getMaxEmployees());
        assertTrue(course.getRegisteredEmployees().contains("REG-COURSE-EMPLOYEE1-JAVA"));
        assertTrue(course.getRegisteredEmployees().contains("REG-COURSE-EMPLOYEE2-JAVA"));
        assertEquals(CourseStatus.COURSE_NOTALLOTED, course.getCourseStatus());
    }

    @Test
    public void testAddAndRemoveRegisteredEmployee() {

        course.addRegistrationIdOfEmployee("REG-COURSE-EMPLOYEE3-JAVA");
        assertTrue(course.getRegisteredEmployees().contains("REG-COURSE-EMPLOYEE3-JAVA"));

        course.removeRegisteredEmployee("REG-COURSE-EMPLOYEE2-JAVA");
        assertFalse(course.getRegisteredEmployees().contains("REG-COURSE-EMPLOYEE2-JAVA"));
    }

    @Test
    public void testToString() {
        String expectedToString = "Course[CourseOfferingId = OFFERING-JAVA-INSTRUCTOR1, CourseName = JAVA, InstructorName = INSTRUCTOR1, CourseOfferingDate = 15012024, MIN_EMPLOYEES = 5, MAX_EMPLOYEES = 20, RegisteredEmployeeIds = [REG-COURSE-EMPLOYEE1-JAVA, REG-COURSE-EMPLOYEE2-JAVA], CourseStatus = COURSE_NOTALLOTED]";

        assertEquals(expectedToString, course.toString());
    }
}
