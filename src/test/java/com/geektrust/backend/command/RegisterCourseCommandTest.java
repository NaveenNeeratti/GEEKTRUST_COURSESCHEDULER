package com.geektrust.backend.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.command.RegisterCourseCoammand;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
import com.geektrust.backend.exception.CourseAllotedException;
import com.geektrust.backend.exception.CourseCancelledException;
import com.geektrust.backend.exception.CourseFullException;
import com.geektrust.backend.exception.InputDataException;
import com.geektrust.backend.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterCourseCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private RegisterCourseCoammand registerCourseCommand;

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_withInvalidData_shouldPrintDataInputError(){
        String emailId = "EMPLOYEE1@GMAIL.COM";
        String courseOfferingId = "OFFERING-INSTRUCTOR1-JAVA";

        List<String> tokens = Arrays.asList("REGISTER",emailId,courseOfferingId);

        doThrow(InputDataException.class).when(employeeService).registerCourse(emailId,courseOfferingId);
        registerCourseCommand.execute(tokens);

        assertEquals("INPUT_DATA_ERROR",outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_withInvalidData_shouldPrintCourseFullError(){
        String emailId = "EMPLOYEE1@GMAIL.COM";
        String courseOfferingId = "OFFERING-INSTRUCTOR1-JAVA";

        List<String> tokens = Arrays.asList("REGISTER",emailId,courseOfferingId);

        doThrow(CourseFullException.class).when(employeeService).registerCourse(emailId,courseOfferingId);
        registerCourseCommand.execute(tokens);

        assertEquals("COURSE_FULL_ERROR",outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_withInvalidData_shouldPrintCourseAllottedError(){
        String emailId = "EMPLOYEE1@GMAIL.COM";
        String courseOfferingId = "OFFERING-INSTRUCTOR1-JAVA";

        List<String> tokens = Arrays.asList("REGISTER",emailId,courseOfferingId);

        doThrow(CourseAllotedException.class).when(employeeService).registerCourse(emailId,courseOfferingId);
        registerCourseCommand.execute(tokens);

        assertEquals("COURSE_ALLOTTED_ERROR",outputStreamCaptor.toString().trim());
    }

    @Test
    public void execute_withInvalidData_shouldPrintCourseCancelledError(){
        String emailId = "EMPLOYEE1@GMAIL.COM";
        String courseOfferingId = "OFFERING-INSTRUCTOR1-JAVA";

        List<String> tokens = Arrays.asList("REGISTER",emailId,courseOfferingId);

        doThrow(CourseCancelledException.class).when(employeeService).registerCourse(emailId,courseOfferingId);
        registerCourseCommand.execute(tokens);

        assertEquals("COURSE_CANCELLED_ERROR",outputStreamCaptor.toString().trim());
    }

    @Test
    public void excetue_withValidData_shouldPrintCourseRegistrationDto(){
        String emailId = "EMPLOYEE1@GMAIL.COM";
        String courseOfferingId = "OFFERING-INSTRUCTOR-JAVA";
        EmployeeRequestStatusDto courseRegistrationDto = new EmployeeRequestStatusDto("REG-COURSE-EMPLOYEE1-JAVA", "ACCEPTED");
        List<String> tokens = Arrays.asList("REGISTER",emailId,courseOfferingId);

        doReturn(courseRegistrationDto).when(employeeService).registerCourse(emailId,courseOfferingId);
        registerCourseCommand.execute(tokens);

        assertEquals("REG-COURSE-EMPLOYEE1-JAVA ACCEPTED",outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
