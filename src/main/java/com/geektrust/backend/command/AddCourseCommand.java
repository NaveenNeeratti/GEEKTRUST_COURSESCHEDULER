package com.geektrust.backend.command;

import java.util.List;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.ICourseService;

public class AddCourseCommand implements ICommand{
    private final ICourseService courseService;
    
    public AddCourseCommand(ICourseService courseService){
        this.courseService = courseService;
    }
    @Override
    public void execute(List<String> tokens) {
        try{
        String courseName = tokens.get(Constant.COURSE_NAME).trim();
        String instructorName = tokens.get(Constant.INSTRUCTOR_NAME).trim();
        String courseOfferingDate = tokens.get(Constant.COURSE_OFFERING_DATE).trim();
        int minEmployees = Integer.parseInt(tokens.get(Constant.MIN_EMPLOYEES));
        int maxEmployees = Integer.parseInt(tokens.get(Constant.MAX_EMPLOYEES));
        String courseOfferingId = courseService.addCourse(courseName, instructorName, courseOfferingDate, minEmployees, maxEmployees);
        System.out.println(courseOfferingId);
        }catch(IndexOutOfBoundsException  | InputDataException ex){
            System.out.println("INPUT_DATA_ERROR");
        }
    }
    
}
