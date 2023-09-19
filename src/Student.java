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
    private static final int ID_LOWER_BOUND = 9999;
    private static final int ID_UPPER_BOUND = 100000;

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
                case 4 -> StudentReport();
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
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input.equals(c);
    }

    private static int parseAndValidate(String message, int lower, int upper) {
        //take integer input from user and validate it
        int input = parseInt();
        if(rangeValidate(input, lower, upper)) return input;
        System.out.println(message);
        return parseAndValidate(message, lower, upper);
    }

    private static int parseAndValidate(int lower, int upper) {
        return parseAndValidate("Invalid input!", lower, upper);
    }

    private static int parseInt() {
        //take input from user and parse it to integer
        Scanner sc = new Scanner(System.in);
        String input;
        int inputNum;
        input = sc.nextLine();
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

    public static void saveStudent() {
        //take input from user and create a student object
        Scanner sc = new Scanner(System.in);
        String banner = "CAPTURE A NEW STUDENT";
        System.out.println(banner);
        System.out.println("*".repeat(banner.length()));
        System.out.print("Enter the student id: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        System.out.print("Enter the student name: ");
        String name = sc.nextLine();
        System.out.print("Enter the student age: ");
        int age = parseAndValidate("You have entered an incorrect student age!!!\nPlease re-enter the student age",15, 120);
        System.out.print("Enter the student email: ");
        String email = sc.nextLine();
        System.out.print("Enter the student course: ");
        String course = sc.nextLine();
        StudentDetails student = new StudentDetails(id, name, age, email, course);
        studentRecords.put(id, student);
    }

    public static void searchStudent() {
        //take ID from user and search for the student
        String banner = "SEARCH FOR A STUDENT";
        System.out.println(banner);
        System.out.println("-".repeat(banner.length()));
        System.out.print("Enter the student id: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        StudentDetails student = studentRecords.get(id);
        if(student == null) {
            System.out.println("Student with id: " + id + " not found.");
            return;
        }
        printStudentRecord(student);
    }

    public static void deleteStudent() {
        //take ID from user and delete the student
        String banner = "DELETE A STUDENT";
        System.out.println(banner);
        System.out.println("-".repeat(banner.length()));
        System.out.print("Enter the student id to delete: ");
        int id = parseAndValidate(ID_LOWER_BOUND, ID_UPPER_BOUND);
        StudentDetails student = studentRecords.get(id);
        if (student == null) {
            System.out.println("Student with id: " + id + " not found.");
            return;
        }
        System.out.println("Are you sure you want to delete student with id: " + id + "? (y) ");
        if(confirm("y")) {
            studentRecords.remove(id);
            System.out.println("Student with id: " + id + " deleted.");
            return;
        }
        System.out.println("Student deletion cancelled.");
    }

    public static void StudentReport() {
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

    public static void ExitStudentApplication() {
        //exit the application
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
