package com.geektrust.backend.appconfig;

import com.geektrust.backend.command.AddCourseCommand;
import com.geektrust.backend.command.AllotCourseCommand;
import com.geektrust.backend.command.CancelRegistrationCommand;
import com.geektrust.backend.command.CommandInvoker;
import com.geektrust.backend.command.RegisterCourseCoammand;
import com.geektrust.backend.repository.CourseRepository;
import com.geektrust.backend.repository.EmployeeRepository;
import com.geektrust.backend.repository.ICourseRepository;
import com.geektrust.backend.repository.IEmployeeRepository;
import com.geektrust.backend.service.CourseService;
import com.geektrust.backend.service.EmployeeService;
import com.geektrust.backend.service.ICourseService;
import com.geektrust.backend.service.IEmployeeService;

public class ApplicationConfig {
    private final IEmployeeRepository employeeRepository = new EmployeeRepository();
    private final ICourseRepository courseRepository = new CourseRepository();
    
    private final IEmployeeService employeeService = new EmployeeService(courseRepository, employeeRepository);
    private final ICourseService courseService = new CourseService(courseRepository, employeeRepository);

    private final AddCourseCommand addCourseCommand = new AddCourseCommand(courseService);
    private final AllotCourseCommand allotCourseCommand = new AllotCourseCommand(courseService);
    private final RegisterCourseCoammand registerCourseCommand = new RegisterCourseCoammand(employeeService);
    private final CancelRegistrationCommand cancelRegistrationCommand = new CancelRegistrationCommand(employeeService);

    private final CommandInvoker commandInvoker = new CommandInvoker();
    public CommandInvoker getCommandInvoker(){
        commandInvoker.registerCommand("ADD-COURSE-OFFERING", addCourseCommand);
        commandInvoker.registerCommand("REGISTER", registerCourseCommand);
        commandInvoker.registerCommand("ALLOT", allotCourseCommand);
        commandInvoker.registerCommand("CANCEL", cancelRegistrationCommand);

        return commandInvoker;
    }

}
