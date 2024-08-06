package com.geektrust.backend.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entity.Employee;

public class EmployeeRepository implements IEmployeeRepository{
    
    private final Map<String,Employee> employeeMap;

    public EmployeeRepository(Map<String,Employee> employeeMap){
        this.employeeMap = employeeMap;
    }

    public EmployeeRepository(){
        employeeMap = new HashMap<>();
    }

    @Override
    public Optional<Employee> findById(String courseRegistrationId){
        return Optional.ofNullable(employeeMap.get(courseRegistrationId));
    }

    @Override
    public void delete(String courseRegistrationId) {
        employeeMap.remove(courseRegistrationId); 
    }

    @Override
    public List<Employee> findAll() {
       return employeeMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Employee> findAllByCourseOfferingId(String courseOfferingId) {
       return employeeMap.values().stream().filter(e->e.getCourseOfferingId().equals(courseOfferingId)).collect(Collectors.toList());
    }

    @Override
    public String save(Employee employee, String courseName) {
        String courseRegistrationId = buildCourseRegistrationId(employee.getEmployeeName(),courseName);
        employee.setCourseRegistrationId(courseRegistrationId);
        employeeMap.put(courseRegistrationId,employee);
        return courseRegistrationId;
    }

    private String buildCourseRegistrationId(String employeeName, String courseName) {
        return "REG-COURSE"+"-"+employeeName.toUpperCase()+"-"+courseName.toUpperCase();
    }

}
