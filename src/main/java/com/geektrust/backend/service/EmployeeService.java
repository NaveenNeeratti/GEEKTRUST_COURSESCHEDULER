package com.geektrust.backend.service;

import java.util.List;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseCancellationRequest;
import com.geektrust.backend.entity.CourseRegistrationRequest;
import com.geektrust.backend.entity.CourseStatus;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.exception.CourseAllotedException;
import com.geektrust.backend.exception.CourseCancelledException;
import com.geektrust.backend.exception.CourseFullException;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IEmployeeRepository;

public class EmployeeService implements IEmployeeService{
    private final ICourseRepository courseRepository;
    private final IEmployeeRepository employeeRepository;
    public EmployeeService(ICourseRepository courseRepository, IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
        this.courseRepository = courseRepository;
    }
    @Override
    public EmployeeRequestStatusDto registerCourse(String emailId, String courseOfferingId)
            throws InputDataException, CourseFullException,CourseCancelledException,CourseAllotedException {
        Course course = courseRepository.findById(courseOfferingId).orElseThrow(()->new InputDataException("Input valid course offering Id!"));
        checkIfCourseOfferingIdIsBlank(courseOfferingId);
        checkIfEmailIdIsValid(emailId);
        checkIfUniqueInputFields(emailId,courseOfferingId);
        checkIscourseCancelled(course);
        checkIsCourseAllotted(course);
        checkIsCourseFull(course);
        String courseName = course.getCourseName();
        String employeeName = getEmployeeName(emailId);
        Employee employee = createEmployee(employeeName,emailId,courseOfferingId);
        String courseRegistrationId = employeeRepository.save(employee,courseName);
        course.addRegistrationIdOfEmployee(courseRegistrationId);
        return new EmployeeRequestStatusDto(courseRegistrationId, CourseRegistrationRequest.ACCEPTED.name());
    }
    
    @Override
    public EmployeeRequestStatusDto cancelRegistration(String courseRegistrationId)throws InputDataException {
       Employee employee = employeeRepository.findById(courseRegistrationId).orElseThrow(()->new InputDataException("Input Data Error"));
       Course course = courseRepository.findById(employee.getCourseOfferingId()).orElseThrow(()->new InputDataException("Input Data Error"));
       if(isCourseAllotted(course)) return new EmployeeRequestStatusDto(courseRegistrationId,CourseCancellationRequest.CANCEL_REJECTED.name());
       course.removeRegisteredEmployee(courseRegistrationId);
       employeeRepository.delete(courseRegistrationId);
       return new EmployeeRequestStatusDto(courseRegistrationId, CourseCancellationRequest.CANCEL_ACCEPTED.name());
    }

    private void checkIfUniqueInputFields(String emailId, String courseOfferingId) {
        if(!isUnique(emailId, courseOfferingId)) throw new InputDataException("EmailId is already registered to the course");
    }

    private void checkIfEmailIdIsValid(String emailId) {
        if(!isValidEmailId(emailId)) throw new InputDataException("EmailId is not valid");
    }

    private void checkIfCourseOfferingIdIsBlank(String courseOfferingId) {
        if(courseOfferingId.isBlank()) throw new InputDataException("Course offering id is blank or null");
    }

    private void checkIsCourseFull(Course course) {
        if(isCourseFull(course)) throw new CourseFullException("The course is full!");
    }
    
    private void checkIscourseCancelled(Course course) {
        if(isCourseCancelled(course)) throw new CourseCancelledException("The course has been cancelled!");
    }

    private void checkIsCourseAllotted(Course course) {
         if(isCourseAllotted(course)) throw new CourseAllotedException("The course has been allotted and running!");
    }

    private String getEmployeeName(String emailId) {
        int index = emailId.indexOf('@');
        String employeeName = emailId.substring(Constant.STARTING_INDEX,index);
        return employeeName;
    }
   

    private Employee createEmployee(String employeeName, String emailId, String courseOfferingId) {
        return new Employee(employeeName, emailId, courseOfferingId);
    }
    
    private boolean isValidEmailId(String emailId) {
        if(!emailId.contains("@")) return false;
        String[] parts = emailId.split("@");
        String employeeName = parts[Constant.NAME_IN_EMAILID];
        String domain = parts[Constant.DOMAIN_IN_EMAILID];
        if(!employeeName.isBlank() && domain.equalsIgnoreCase("GMAIL.COM")) return true;
        return false;
    }
    
    private boolean isUnique(String emailId,String courseOfferingId) {
        List<Employee> registeredEmployees = employeeRepository.findAllByCourseOfferingId(courseOfferingId);
        for(Employee employee : registeredEmployees){
            if(employee.getEmail().equals(emailId)) return false;
        }
        return true;
    }

    private boolean isCourseAllotted(Course course) {
        return course.getCourseStatus() == CourseStatus.CONFIRMED ? true : false;
    }

    private boolean isCourseCancelled(Course course) {
        return course.getCourseStatus() == CourseStatus.COURSE_CANCELED ? true : false;
    }

    private boolean isCourseFull(Course course) {
        return course.getRegisteredEmployees().size() == course.getMaxEmployees() ? true : false;
    } 
   
}
