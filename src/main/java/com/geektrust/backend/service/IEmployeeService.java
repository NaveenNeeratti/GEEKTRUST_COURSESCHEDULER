package com.geektrust.backend.service;

import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.exception.CourseAllotedException;
import com.geektrust.backend.exception.CourseCancelledException;
import com.geektrust.backend.exception.CourseFullException;
import com.geektrust.backend.exception.InputDataException;

public interface IEmployeeService {
    /**
     * registerCourse method register employee to the course if the employees are not full.
     * @param emailId - emailId of the employee
     * @param courseOfferingId - courseOfferingId of course
     * @return -EmployeeRequestStatusDto which has courseRegistrationId and status of Registration
     * @throws InputDataException - if emailId and courseOfferingId are not valid
     * @throws CourseFullException - if employees are full in the course
     * @throws CourseCancelledException - if the course is already cancelled becaues of less employees
     * @throws CourseAllotedException - if the course is already alloted
     */
    public EmployeeRequestStatusDto registerCourse(String emailId,String courseOfferingId) throws InputDataException, CourseFullException,CourseCancelledException,CourseAllotedException;
     /**
      * cacel registration method cancels the registration of the employee if course is not alloted
      * @param courseRegistrationId - courseRegistrationId of employee
      * @return - EmployeeRequestStatusDto which has courseRegistrationId and status of cancellation either Accepted or Rejected
      */
    public EmployeeRequestStatusDto cancelRegistration(String courseRegistrationId);
}
