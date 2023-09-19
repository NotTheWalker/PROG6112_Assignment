import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void tearDown(){
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString().replace("\r","");
    }

    @Test
    public void testSaveStudent() {
        provideInput(
                """
                        54333
                        Caleb
                        23
                        foo@bar.gov
                        PROG6212
                        """
        );
        final String expectedOutput =
                """
                        CAPTURE A NEW STUDENT
                        *********************
                        Enter the student id:\s
                        Enter the student name:\s
                        Enter the student age:\s
                        Enter the student email:\s
                        Enter the student course:\s
                        """;
        Student.saveStudent();
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void testSearchStudent() {
        provideInput("54333");
        final String expectedOutput = """
                SEARCH FOR A STUDENT
                --------------------
                Enter the student id:\s
                Student with id: 54333 not found.
                """;
        Student.searchStudent();
        assertEquals(expectedOutput, getOutput());
    }

}