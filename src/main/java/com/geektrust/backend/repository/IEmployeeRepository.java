package com.geektrust.backend.repository;

import java.util.List;
import com.geektrust.backend.entity.Employee;

public interface IEmployeeRepository extends CRUDRepository<Employee,String>{
    /**
     * It finds all the employees who are register to a particular courseOfferingId.
     * @param courseOfferingId - It is the input where we find employees who have registered to this course offering Id.
     * @return It returns list of employees with same courseOfferingId
     */
    List<Employee> findAllByCourseOfferingId(String courseOfferingId);

    /**
     * It saves the record i.e employee object in the database
     * @param employee -Record which needs to be saved.
     * @param courseName- courseName is required to generate courseRegistrationId after saving record
     * @return -returns courseRegistrationId after saving record. 
     */
    String save(Employee employee, String courseName);

    
}
