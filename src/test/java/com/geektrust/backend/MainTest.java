package com.geektrust.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App Test")
class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    //Arrange
    List<String> arguments= new ArrayList<>(List.of("sample_input/input1.txt"));
        
    @Test
    public void Application_Test() throws Exception{
            // Arrange
        String expectedOutput = "OFFERING-JAVA-JAMES\n"+
        "INPUT_DATA_ERROR\n"+
        "OFFERING-KUBERNETES-WOODY\n"+ 
        "REG-COURSE-ANDY-JAVA ACCEPTED\n"+ 
        "REG-COURSE-WOO-JAVA ACCEPTED\n"+ 
        "COURSE_FULL_ERROR\n"+ 
        "INPUT_DATA_ERROR\n"+ 
        "REG-COURSE-ANDY-JAVA ANDY@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED\n"+ 
        "REG-COURSE-WOO-JAVA WOO@GMAIL.COM OFFERING-JAVA-JAMES JAVA JAMES 15062022 CONFIRMED\n"+ 
        "REG-COURSE-ANDY-JAVA CANCEL_REJECTED";
        
    
        // Act   
        Main.run(arguments);
        // Assert
        Assertions.assertEquals(expectedOutput,outputStreamCaptor.toString().trim());
    }
    
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}

