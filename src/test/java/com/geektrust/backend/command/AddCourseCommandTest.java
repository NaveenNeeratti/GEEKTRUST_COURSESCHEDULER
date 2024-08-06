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
import com.geektrust.backend.command.AddCourseCommand;
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
public class AddCourseCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private CourseService courseService;
    @InjectMocks
    private AddCourseCommand addCourseCommand;

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_withInvalidData_shouldPrintDataInputError(){
        String courseName = "Java";
        String instructorName = "Instructor1";
        String courseOfferingDate = "01012024";
        String minEmployees = "0";
        String maxEmployees = "5";

        List<String> tokens = Arrays.asList("ADD-COURSE-OFFERING",courseName,instructorName,courseOfferingDate,minEmployees,maxEmployees);
        doThrow(InputDataException.class).when(courseService).addCourse(courseName, instructorName, courseOfferingDate, 0, 5);

        addCourseCommand.execute(tokens);

        assertEquals("INPUT_DATA_ERROR",outputStreamCaptor.toString().trim());
        verify(courseService,times(1)).addCourse(courseName, instructorName, courseOfferingDate, 0, 5);
    }

    @Test
    public void execute_withValidInput_shouldPrintCourseOfferingId(){
        String courseName = "Java";
        String instructorName = "Instructor1";
        String courseOfferingDate = "01012024";
        String minEmployees = "1";
        String maxEmployees = "5";

        List<String> tokens = Arrays.asList("ADD-COURSE-OFFERING",courseName,instructorName,courseOfferingDate,minEmployees,maxEmployees);
        doReturn("OFFERING-JAVA-INSTRUCTOR1").when(courseService).addCourse(courseName, instructorName, courseOfferingDate, 1, 5);
        addCourseCommand.execute(tokens);

        assertEquals("OFFERING-JAVA-INSTRUCTOR1", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
