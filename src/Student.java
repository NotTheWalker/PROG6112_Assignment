import java.util.HashMap;

public class Student {
    record StudentDetails(String name, int age, String email, String course) {}
    HashMap<Integer, StudentDetails> studentRecords = new HashMap<Integer, StudentDetails>();
    String[] menu = new String[]{
            "Capture a new student.",
            "Search for a student.",
            "Delete a student.",
            "Print student report",
            "Exit Application."
    };

    public static void saveStudent() {

    }

    public static void searchStudent() {

    }

    public static void deleteStudent() {

    }

    public static void StudentReport() {

    }

    public static void ExitStudentApplication() {

    }

}
