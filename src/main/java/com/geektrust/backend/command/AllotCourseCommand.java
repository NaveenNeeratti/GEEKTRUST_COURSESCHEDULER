package com.geektrust.backend.command;

import java.util.List;
import com.geektrust.backend.dto.CourseAllotmentDto;
import com.geektrust.backend.entity.Constant;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.ICourseService;

public class AllotCourseCommand implements ICommand{

    private final ICourseService courseService;
    public AllotCourseCommand(ICourseService courseService){
        this.courseService = courseService;
    }
    @Override
    public void execute(List<String> tokens) {
        try{
        String courseOfferingId = tokens.get(Constant.ALLOT_COURSE_OFFERING_ID).trim();
        List<CourseAllotmentDto> courseAllotmentList = courseService.allotCourse(courseOfferingId);
        for(CourseAllotmentDto courseAllotmentDto : courseAllotmentList){
            System.out.println(courseAllotmentDto);
        }
        }catch(IndexOutOfBoundsException | InputDataException ex){
            System.out.println("INPUT_DATA_ERROR");
        }
    }
    
}
