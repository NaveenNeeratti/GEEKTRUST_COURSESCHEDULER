package com.geektrust.backend.entity;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseOfferingId;
    private final String courseName;
    private final String instructorName;
    private final String courseOfferingDate;
    private final int MIN_EMPLOYEES;
    private final int MAX_EMPLOYEES;
    private final List<String> registeredEmployeeIds;
    private  CourseStatus courseStatus;

    public Course(String courseName, String instructorName, String courseOfferingDate, int minEmployees, int maxEmployees){
        this.courseName = courseName;
        this.instructorName = instructorName;
        this.courseOfferingDate = courseOfferingDate;
        this.MIN_EMPLOYEES = minEmployees;
        this.MAX_EMPLOYEES = maxEmployees;
        this.registeredEmployeeIds = new ArrayList<>();
        this.courseStatus = CourseStatus.COURSE_NOTALLOTED;
    }
    public String getCourseOfferingId(){
        return courseOfferingId;
    }
    public void setCourseOfferingId(String courseOfferingId){
        if(courseOfferingId.contains(instructorName.toUpperCase()) && courseOfferingId.contains(courseName.toUpperCase())){
        this.courseOfferingId = courseOfferingId;
        }
    }
    public String getCourseName(){
        return courseName;
    }
    public String getIntructorName(){
        return instructorName;
    }
    public String getCourseOfferingDate(){
        return courseOfferingDate;
    }
    public int getMinEmployees(){
        return MIN_EMPLOYEES;
    }
    public int getMaxEmployees(){
        return MAX_EMPLOYEES;
    }
    public List<String> getRegisteredEmployees(){
        return registeredEmployeeIds;
    }
    public void addRegistrationIdOfEmployee( String employeeRegistrationId){
        if(employeeRegistrationId.contains(courseName.toUpperCase())){
        registeredEmployeeIds.add(employeeRegistrationId);
        }
    }
    public void removeRegisteredEmployee(String employeeRegistrationId){
        if(employeeRegistrationId.contains(courseName.toUpperCase())){
        registeredEmployeeIds.remove(employeeRegistrationId);
        }
    }
    public CourseStatus getCourseStatus(){
        return courseStatus;
    }
    public void setCourseStatus(CourseStatus courseStatus){
        if(this.courseStatus == CourseStatus.COURSE_NOTALLOTED){
        this.courseStatus = courseStatus;
        }
    }
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Course)){
            return false;
        }
        Course course = (Course)o;
        return getCourseOfferingId().equals(course.getCourseOfferingId());
    }

    @Override
    public String toString(){
        return "Course[" +
            "CourseOfferingId = " + courseOfferingId +
            ", CourseName = " + courseName  +
            ", InstructorName = " + instructorName +
            ", CourseOfferingDate = " + courseOfferingDate +
            ", MIN_EMPLOYEES = " + MIN_EMPLOYEES +
            ", MAX_EMPLOYEES = " + MAX_EMPLOYEES +
            ", RegisteredEmployeeIds = " + registeredEmployeeIds +
            ", CourseStatus = " + courseStatus +
            "]";
    }
}
