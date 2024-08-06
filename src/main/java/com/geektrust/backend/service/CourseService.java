package com.geektrust.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.geektrust.backend.dto.CourseAllotmentDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseStatus;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IEmployeeRepository;

public class CourseService implements ICourseService{
    private final ICourseRepository courseRepository;
    private final IEmployeeRepository employeeRepository;
    public CourseService(ICourseRepository courseRepository,IEmployeeRepository employeeRepository){
        this.courseRepository = courseRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String addCourse(String courseName, String instructorName, String courseOfferingDate, int minEmployees,
            int maxEmployees)throws InputDataException {
        checkIfCourseIsBlank(courseName);
        checkIfInstructorNameIsBlank(instructorName);
        chekcIfCourseOfferingDateIsBlank(courseOfferingDate);
        Course newCourse = new Course(courseName, instructorName, courseOfferingDate, minEmployees, maxEmployees);
        String courseOfferingId = courseRepository.save(newCourse);
        return courseOfferingId;
    }


    @Override
    public List<CourseAllotmentDto> allotCourse(String courseOfferingId)throws InputDataException {
        Course course = courseRepository.findById(courseOfferingId).orElseThrow(()->new InputDataException("Invalid CourseOfferingId"));
        CourseStatus courseStatus = checkIfCourseHasRequiredEmployees(course) ? CourseStatus.CONFIRMED : CourseStatus.COURSE_CANCELED;
        course.setCourseStatus(courseStatus);
        //EmployeeStatus employeeStatus = course.getCourseStatus() == CourseStatus.COURSE_ALLOTED ? EmployeeStatus.CONFIRMED : EmployeeStatus.CANCELLED;
        List<CourseAllotmentDto> courseAllotmentList = createListOfCourseAllotmentDto(course,courseOfferingId);
        List<CourseAllotmentDto> sortedCourseAllotmentList = sortListOnCourseRegistrationId(courseAllotmentList);
        return sortedCourseAllotmentList;
    }

    private void chekcIfCourseOfferingDateIsBlank(String courseOfferingDate) {
        if(courseOfferingDate.isBlank()) throw new InputDataException("Course offering date is blank or empty");
    }

    private void checkIfInstructorNameIsBlank(String instructorName) {
        if(instructorName.isBlank()) throw new InputDataException("Instructor name is blank or empty!");
    }

    private void checkIfCourseIsBlank(String courseName) {
        if(courseName.isBlank()) throw new InputDataException("Course name is blank or empty!");
    }

    // private Course createCourse(String courseName, String instructorName, String date,
    // int minEmployees, int maxEmployees){
    //     return new Course(courseName, instructorName, date, minEmployees, maxEmployees);
    // }
    

    private List<CourseAllotmentDto> createListOfCourseAllotmentDto(Course course,String courseOfferingId) {
        List<Employee> employees = employeeRepository.findAllByCourseOfferingId(courseOfferingId);
        List<CourseAllotmentDto> courseAllotmentList = new ArrayList<>();
        for(Employee employee : employees){
            courseAllotmentList.add(new CourseAllotmentDto(employee.getCourseRegistrationId(), employee.getEmail(), course.getCourseOfferingId(), course.getCourseName(), course.getIntructorName(), course.getCourseOfferingDate(),course.getCourseStatus().name()));
        }
        return courseAllotmentList;
    }

    private boolean checkIfCourseHasRequiredEmployees(Course course) {
        return course.getRegisteredEmployees().size() > course.getMinEmployees() ? true : false;
    }

    private List<CourseAllotmentDto> sortListOnCourseRegistrationId(
            List<CourseAllotmentDto> courseAllotmentList) {
        Collections.sort(courseAllotmentList,Comparator.comparing(CourseAllotmentDto::getCourseRegistrationId));
        return courseAllotmentList;
    }

}
