import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

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
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString().replace("\r","");
    }

    private void addDefaultStudent() {
        Student.addStudent(54333, "Caleb", 23, "foo@bar.gov", "PROG6212");
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
        Student.reset();
        Student.saveStudent();
        assertEquals(expectedOutput, getOutput());
        assertEquals(1, Student.getStudentRecordLength());
    }

    @Test
    public void testSearchStudentEmpty() {
        provideInput("54333");
        final String expectedOutput = """
                SEARCH FOR A STUDENT
                --------------------
                Enter the student id:\s
                Student with id: 54333 not found.
                """;
        Student.reset();
        Student.searchStudent();
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void testSearchStudent() {
        provideInput("54333");
        final String expectedOutput = """
                SEARCH FOR A STUDENT
                --------------------
                Enter the student id:\s
                STUDENT ID: 54333
                STUDENT NAME: Caleb
                STUDENT AGE: 23
                STUDENT EMAIL: foo@bar.gov
                STUDENT COURSE: PROG6212
                """;
        Student.reset();
        addDefaultStudent();
        Student.searchStudent();
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void testDeleteStudentEmpty() {
        provideInput("54333");
        final String expectedOutput = """
                DELETE A STUDENT
                ----------------
                Enter the student id to delete:\s
                Student with id: 54333 not found.
                """;
        Student.reset();
        Student.deleteStudent();
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void testDeleteStudentAffirm() {
        provideInput("""
                        54333
                        y
                        """);
        final String expectedOutput = """
                DELETE A STUDENT
                ----------------
                Enter the student id to delete:\s
                Are you sure you want to delete student with id: 54333? (y)
                Student with id: 54333 deleted.
                """;
        Student.reset();
        addDefaultStudent();
        Student.deleteStudent();
        assertEquals(expectedOutput, getOutput());
        assertEquals(0, Student.getStudentRecordLength());
    }

    @Test
    public void testDeleteStudentCancel() {
        provideInput("""
                        54333
                        n
                        """);
        final String expectedOutput = """
                DELETE A STUDENT
                ----------------
                Enter the student id to delete:\s
                Are you sure you want to delete student with id: 54333? (y)
                Student deletion cancelled.
                """;
        Student.reset();
        addDefaultStudent();
        Student.deleteStudent();
        assertEquals(expectedOutput, getOutput());
        assertEquals(1, Student.getStudentRecordLength());
    }

    @Test
    public void testStudentReportEmpty() {
        final String expectedOutput = """
                STUDENT REPORT
                --------------------------------------------------------
                """;
        Student.reset();
        Student.studentReport();
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void testStudentReport() {
        final String expectedOutput = """
                STUDENT REPORT
                --------------------------------------------------------
                STUDENT ID: 54333
                STUDENT NAME: Caleb
                STUDENT AGE: 23
                STUDENT EMAIL: foo@bar.gov
                STUDENT COURSE: PROG6212
                --------------------------------------------------------
                """;
        Student.reset();
        addDefaultStudent();
        Student.studentReport();
        assertEquals(expectedOutput, getOutput());
    }

}