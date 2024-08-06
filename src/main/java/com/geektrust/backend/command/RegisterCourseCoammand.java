package com.geektrust.backend.command;

import java.util.List;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.exception.CourseAllotedException;
import com.geektrust.backend.exception.CourseCancelledException;
import com.geektrust.backend.exception.CourseFullException;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.IEmployeeService;

public class RegisterCourseCoammand implements ICommand{
    private final IEmployeeService employeeService;
    
    public RegisterCourseCoammand(IEmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @Override
    public void execute(List<String> tokens) {
       try{
       String emailId = tokens.get(Constant.EMAILID).trim();
       String courseOfferingId = tokens.get(Constant.REG_COURSE_OFFERING_ID).trim();
       EmployeeRequestStatusDto courseRegistrationDto = employeeService.registerCourse(emailId, courseOfferingId);
       System.out.println(courseRegistrationDto);
       }catch(IndexOutOfBoundsException | InputDataException ex){
        System.out.println("INPUT_DATA_ERROR");
       }catch(CourseFullException cfe){
        System.out.println("COURSE_FULL_ERROR");
       }catch(CourseAllotedException cae){
        System.out.println("COURSE_ALLOTTED_ERROR");
       }catch(CourseCancelledException cce){
        System.out.println("COURSE_CANCELLED_ERROR");
       }
        
    }
    
}
