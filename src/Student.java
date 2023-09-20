import java.util.HashMap;
import java.util.Scanner;

public class Student {
    private record StudentDetails(Integer id, String name, int age, String email, String course) {}
    private static final HashMap<Integer, StudentDetails> studentRecords = new HashMap<>();
    private static final String[] menu = new String[]{
            "Capture a new student.",
            "Search for a student.",
            "Delete a student.",
            "Print student report",
            "Exit Application."
    };

    private static Scanner scanner = new Scanner(System.in);
    private static final int ID_LOWER_BOUND = 9999;
    private static final int ID_UPPER_BOUND = 100000;

    /**
     * Worker call method
     */
    public static void start() {
        printBanner();
        while(true) {
            System.out.println("Enter (1) to launch menu or any other key to exit");
            if(!confirm("1")) {
                ExitStudentApplication();
                return;
            }
            printMenu();
            int menuSelection = parseAndValidate(0, menu.length+1);
            switch (menuSelection) {
                case 1 -> saveStudent();
                case 2 -> searchStudent();
                case 3 -> deleteStudent();
                case 4 -> studentReport();
                case 5 -> ExitStudentApplication();
            }
            System.out.println("*".repeat(50));
        }

    }

    private static void printBanner() {
        String banner = "STUDENT MANAGEMENT APPLICATION";
        System.out.println(banner);
        System.out.println("*".repeat(banner.length()));
    }

    private static boolean confirm(String c) {
        //take input from user and validate it
        String input = scanner.nextLine();
        return input.equals(c);
    }

    private static int parseAndValidate(int lower, int upper) {
        //take integer input from user and validate it
        int input = parseInt();
        if(rangeValidate(input, lower, upper)) return input;
        System.out.println("Invalid input!");
        return parseAndValidate(lower, upper);
    }

    private static int parseInt() {
        //take input from user and parse it to integer
        String input;
        int inputNum;
        input = scanner.nextLine();
        try {
            inputNum = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid input!");
            return parseInt();
        }
        return inputNum;
    }

    private static boolean rangeValidate(int input, int lower, int upper) {
        //validate the input is within the range
        return lower < input && input < upper;
    }

    private static void printMenu() {
        //print the menu options
        System.out.println("Select an option from the menu below:");
        for (int i = 0; i < menu.length; i++) {
            System.out.println("("+(i+1)+") "+menu[i]);
        }
    }

    /**
     * Prompts user for information pertaining to a new student before saving to student records object
     */
    public static void saveStudent() {
        //take input from user anc create a student object
        String banner = "CAPTURE A NEW STUDENT";
        System.out.println(banner);
        System.out.println("*".repeat(banner.length()));
        System.out.println("Enter the student id: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        System.out.println("Enter the student name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the student age: ");
        int age = parseAndValidate(15, 120);
        System.out.println("Enter the student email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the student course: ");
        String course = scanner.nextLine();
        StudentDetails student = new StudentDetails(id, name, age, email, course);
        studentRecords.put(id, student);
    }

    /**
     * Prompts user for student id before returning matching student's record or an error statement if no match found
     */
    public static void searchStudent() {
        //take ID from user and search for the student
        String banner = "SEARCH FOR A STUDENT";
        System.out.println(banner);
        System.out.println("-".repeat(banner.length()));
        System.out.println("Enter the student id: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        StudentDetails student = studentRecords.get(id); //TODO: investigate monad implementation
        if(student == null) {
            System.out.println("Student with id: " + id + " not found.");
            return;
        }
        printStudentRecord(student);
    }

    /**
     * Prompts user for student id and confirms intent before deleting matching student or an error statement if no match found
     */
    public static void deleteStudent() {
        //take ID from user and delete the student
        String banner = "DELETE A STUDENT";
        System.out.println(banner);
        System.out.println("-".repeat(banner.length()));
        System.out.println("Enter the student id to delete: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        StudentDetails student = studentRecords.get(id);
        if (student == null) {
            System.out.println("Student with id: " + id + " not found.");
            return;
        }
        System.out.println("Are you sure you want to delete student with id: " + id + "? (y)");
        if(confirm("y")) {
            studentRecords.remove(id);
            System.out.println("Student with id: " + id + " deleted.");
            return;
        }
        System.out.println("Student deletion cancelled.");
    }

    /**
     * Prints a student's information
     */
    public static void studentReport() {
        //display report for every student
        String banner = "STUDENT REPORT";
        System.out.println(banner);
        System.out.println("-".repeat(banner.length()*4));
        for (StudentDetails student : studentRecords.values()) {
            printStudentRecord(student);
            System.out.println("-".repeat(banner.length()*4));
        }
    }

    private static void printStudentRecord(StudentDetails student) {
        System.out.println("STUDENT ID: " + student.id);
        System.out.println("STUDENT NAME: " + student.name);
        System.out.println("STUDENT AGE: " + student.age);
        System.out.println("STUDENT EMAIL: " + student.email);
        System.out.println("STUDENT COURSE: " + student.course);
    }

    /**
     * Exits the application
     */
    public static void ExitStudentApplication() {
        //exit the application
        System.out.println("Exiting application...");
        scanner.close();
        System.exit(0);
    }

    /**
     * Helper function for testing by directly adding to student record
     * @param id
     * @param name
     * @param age
     * @param email
     * @param course
     */
    public static void addStudent(Integer id, String name, int age, String email, String course) {
        studentRecords.put(id, new StudentDetails(id, name, age, email, course));
    }

    /**
     * Helper function for testing
     * @return the number of student records
     */
    public static int getStudentRecordLength() {
        return studentRecords.size();
    }

    /**
     *  Helper function for testing by resetting static members to default state
     */
    public static void reset() {
        resetScanner();
        resetRecords();
    }

    private static void resetScanner() {
        scanner.close();
        scanner = new Scanner(System.in);
    }

    private static void resetRecords() {
        for(Integer id : studentRecords.keySet()) {
            studentRecords.remove(id);
        }
    }
}
