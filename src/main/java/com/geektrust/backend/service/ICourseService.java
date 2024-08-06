package com.geektrust.backend.service;

import java.util.List;
import com.geektrust.backend.dto.CourseAllotmentDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.exception.InputDataException;

public interface ICourseService {
    /**
     * addCourse will add new course in the storage
     * @param courseName - name of the course
     * @param instructorName - name of the instructor
     * @param date -course offering date
     * @param minEmployees - minEmployees for course
     * @param maxEmployees - maxEmpoyees for course
     * @return - returns the generated courseOfferinId(OFFERING-INSTRUCTOR NAME-COURSE NAME)
     * @throws InputDataException - if the input has invalid data
     */
    public String addCourse(String courseName, String instructorName, String date, int minEmployees, int maxEmployees) throws InputDataException;


    /**
     * allotCourse will allot the course if the employees are greater than minimun employees
     * @param courseOfferingId - courseOfferingId of course
     * @return List of CourseAllotmentDto
     * @throws InputDataException - if the courseOfferingId is invalid.
     */
    public List<CourseAllotmentDto> allotCourse(String courseOfferingId)throws InputDataException;
}
