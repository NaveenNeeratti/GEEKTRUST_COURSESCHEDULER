package com.geektrust.backend.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.command.CancelRegistrationCommand;
import com.geektrust.backend.dto.EmployeeRequestStatusDto;
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
public class CancelRegistrationCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private CancelRegistrationCommand cancelRegistrationCommand;

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void execute_withInvalidData_shouldPrintDataInputError(){
        String courseRegistrationId = "REG-COURSE-EMPLOYEE1-JAVA";

        List<String> tokens = Arrays.asList("CANCEL",courseRegistrationId);

        doThrow(InputDataException.class).when(employeeService).cancelRegistration(courseRegistrationId);
        cancelRegistrationCommand.execute(tokens);

        assertEquals("INPUT_DATA_ERROR",outputStreamCaptor.toString().trim());
    }

    @Test
    public void excetue_withValidData_shouldPrintCourseRegistrationDto(){
        String courseRegistrationId = "REG-COURSE-EMPLOYEE1-JAVA";
        EmployeeRequestStatusDto courseRegistrationDto = new EmployeeRequestStatusDto(courseRegistrationId, "CANCEL_ACCEPTED");
        List<String> tokens = Arrays.asList("CANCEL",courseRegistrationId);

        doReturn(courseRegistrationDto).when(employeeService).cancelRegistration(courseRegistrationId);
        cancelRegistrationCommand.execute(tokens);

        assertEquals("REG-COURSE-EMPLOYEE1-JAVA CANCEL_ACCEPTED",outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
