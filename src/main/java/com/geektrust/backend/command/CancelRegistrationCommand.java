package com.geektrust.backend.command;

import java.util.List;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.IEmployeeService;

public class CancelRegistrationCommand implements ICommand{
    private final IEmployeeService employeeService;
    public CancelRegistrationCommand(IEmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @Override
    public void execute(List<String> tokens) {
        try{
        String courseRegistrationId = tokens.get(Constant.COURSE_REGISTRATION_ID).trim();
        EmployeeRequestStatusDto courseRegistrationDto = employeeService.cancelRegistration(courseRegistrationId);
        System.out.println(courseRegistrationDto);
        }catch(IndexOutOfBoundsException | InputDataException ex){
            System.out.println("INPUT_DATA_ERROR");
        }
        
    }
    
}
