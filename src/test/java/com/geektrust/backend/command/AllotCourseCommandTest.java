package com.geektrust.backend.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.command.AllotCourseCommand;
import com.geektrust.backend.dto.CourseAllotmentDto;
import com.geektrust.backend.entity.Course;
import com.geektrust.backend.entity.CourseStatus;
import com.geektrust.backend.entity.Employee;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AllotCourseCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private CourseService courseService;
    @InjectMocks
    private AllotCourseCommand allotCourseCommand;

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_withInvaliInput_shouldPrintDataInputError(){
        String courseOfferingId = "OFFERING-JAVA-INSTRUCTOR1";
        List<String> tokens = Arrays.asList("ALLOT",courseOfferingId);

        doThrow(InputDataException.class).when(courseService).allotCourse(courseOfferingId);

        allotCourseCommand.execute(tokens);
        assertEquals("INPUT_DATA_ERROR",outputStreamCaptor.toString().trim());
        verify(courseService,times(1)).allotCourse(courseOfferingId);
    }

    @Test
    public void execute_withValidData_ShouldPrintListOfCourseAllotmentDto(){
        String courseOfferingId = "OFFERING-JAVA-INSTRUCTOR1";
        List<String> tokens = Arrays.asList("ALLOT",courseOfferingId);
        CourseAllotmentDto courseAllotmentDto1 = new CourseAllotmentDto("REG-COURSE-EMPLOYEE1-JAVA","EMPLOYEE1@GMAIL.COM", "OFFERING-JAVA-INSTRUCTOR1", "JAVA", "INSTRUCTOR1", "01012024", "CONFIRMED");
        CourseAllotmentDto courseAllotmentDto2 = new CourseAllotmentDto("REG-COURSE-EMPLOYEE2-JAVA","EMPLOYEE2@GMAIL.COM","OFFERING-JAVA-INSTRUCTOR1", "JAVA", "INSTRUCTOR1", "01012024", "CONFIRMED");
        List<CourseAllotmentDto> courseAllotmentList = Arrays.asList(courseAllotmentDto1,courseAllotmentDto2);

        doReturn(courseAllotmentList).when(courseService).allotCourse(courseOfferingId);

        allotCourseCommand.execute(tokens);
        assertEquals("REG-COURSE-EMPLOYEE1-JAVA EMPLOYEE1@GMAIL.COM OFFERING-JAVA-INSTRUCTOR1 JAVA INSTRUCTOR1 01012024 CONFIRMED\nREG-COURSE-EMPLOYEE2-JAVA EMPLOYEE2@GMAIL.COM OFFERING-JAVA-INSTRUCTOR1 JAVA INSTRUCTOR1 01012024 CONFIRMED", outputStreamCaptor.toString().trim());
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
