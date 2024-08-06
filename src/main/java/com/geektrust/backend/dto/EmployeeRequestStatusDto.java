package com.geektrust.backend.dto;


public class EmployeeRequestStatusDto {
    private final String courseRegistrationId;
    private final String status;

    public EmployeeRequestStatusDto(String courseRegistrationId, String status){
        this.courseRegistrationId = courseRegistrationId;
        this.status = status;
    }

    public String getCourseRegistrationId(){
        return courseRegistrationId;
    }
    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return courseRegistrationId+" "+status;
    }
}
