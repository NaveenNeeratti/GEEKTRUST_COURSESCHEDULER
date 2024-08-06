package com.geektrust.backend.dto;

import java.util.Date;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.Employee;

public class CourseAllotmentDto {
    private final String courseRegistrationId;
    private final String emailId;
    private final String courseOfferingId;
    private final String courseName;
    private final String instructor;
    private final String courseOfferingDate;
    private final String status;

    public CourseAllotmentDto(String courseRegistrationId, String emailId, String courseOfferingId, String courseName, String instructor, String courseOfferingDate, String status){
        this.courseRegistrationId = courseRegistrationId;
        this.emailId = emailId;
        this.courseOfferingId = courseOfferingId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.courseOfferingDate = courseOfferingDate;
        this.status = status;
    }

    public String getCourseRegistrationId(){
        return courseRegistrationId;
    }
    public String getEmailId(){
        return emailId;
    }
    public String getCourseOfferingId(){
        return courseOfferingId;
    }
    public String getCourseName(){
        return courseName;
    }
    public String getInstructor(){
        return instructor;
    }
    public String getCourseOfferingDate(){
        return courseOfferingDate;
    }
    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return courseRegistrationId +" "+emailId+" "+courseOfferingId+" "+courseName+" "+instructor+" "+courseOfferingDate+" "+status;
    }
}
