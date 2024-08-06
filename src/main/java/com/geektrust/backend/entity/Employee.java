package com.geektrust.backend.entity;

public class Employee {
    private String courseRegistrationId;
    private final String employeeName;
    private final String emailId;
    private final String courseOfferingId;

    public Employee(String employeeName,String emailId,String courseOfferingId){
        this.employeeName = employeeName;
        this.emailId = emailId;
        this.courseOfferingId = courseOfferingId;
    }
    public void setCourseRegistrationId(String courseRegistrationId){
        if(courseRegistrationId.contains(employeeName.toUpperCase())){
        this.courseRegistrationId = courseRegistrationId;
        }
    }
    public String getCourseRegistrationId(){
        return courseRegistrationId;
    }
    public String getEmployeeName(){
        return employeeName;
    }
    public String getEmail(){
        return emailId;
    }
    public String getCourseOfferingId(){
        return courseOfferingId;
    }

    @Override
    public String toString(){
        return "Employee[" +
            "CourseRegistrationId = " + courseRegistrationId +
            ", EmployeeName = " + employeeName  +
            ", EmailId = " + emailId +
            ", CourseOfferingId = " + courseOfferingId +
            "]";
    }
}
