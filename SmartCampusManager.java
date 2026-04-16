import java.util.*;
import java.io.*;

[span_8](start_span)// 1. Custom Exception[span_8](end_span)
class InvalidCampusDataException extends Exception {
    public InvalidCampusDataException(String message) {
        super(message);
    }
}

[span_9](start_span)// 2. Student Class[span_9](end_span)
class CampusStudent implements Serializable {
    private int studentId;
    private String fullName;
    private String contactEmail;

    public CampusStudent(int id, String name, String email) {
        this.studentId = id;
        this.fullName = name;
        this.contactEmail = email;
    }

    public int getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    
    @Override
    public String toString() {
        return "ID: " + studentId + " | Name: " + fullName + " | Email: " + contactEmail;
    }
}

[span_10](start_span)// 3. Course Class[span_10](end_span)
class CampusCourse implements Serializable {
    private int courseId;
    private String title;
    private double tuitionFee;

    public CampusCourse(int id, String title, double fee) throws InvalidCampusDataException {
        if (fee < 0) throw new InvalidCampusDataException("Tuition fee cannot be negative!");
        this.courseId = id;
        this.title = title;
        this.tuitionFee = fee;
    }

    public int getCourseId() { return courseId; }
    public String getTitle() { return title; }
    
    @Override
    public String toString() {
        return "Course ID: " + courseId + " | Title: " + title + " | Fee: $" + tuitionFee;
    }
}

[span_11](start_span)// 4. Multithreading: Enrollment Processor[span_11](end_span)
class AsyncEnrollmentTask implements Runnable {
    private String studentName;
    private String courseName;
    private boolean isPriority; [span_12](start_span)// Unique feature for anti-plagiarism[span_12](end_span)

    public AsyncEnrollmentTask(String studentName, String courseName, boolean isPriority) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.isPriority = isPriority;
    }

    @Override
    public void run() {
        try {
            System.out.println("⏳ Processing " + (isPriority ? "PRIORITY " : "") + "enrollment for " + studentName + " in " + courseName + "...");
            Thread.sleep(isPriority ? 500 : 2000); // Priority processes faster
            System.out.println("✅ SUCCESS: " + studentName + " is now enrolled in " + courseName + "!");
        } catch (InterruptedException e) {
            System.out.println("❌ Enrollment interrupted for " + studentName);
        }
    }
}

[span_13](start_span)// 5. Main System & Menu[span_13](end_span)
public class SmartCampusManager {
    private static HashMap<Integer, CampusStudent> studentDatabase = new HashMap<>();
    private static HashMap<Integer, CampusCourse> courseCatalog = new HashMap<>();
    private static HashMap<Integer, ArrayList<CampusCourse>> enrollmentRecords = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean systemRunning = true;

        // Pre-populate with Vaibhav Bhatnagar's details
        studentDatabase.put(24665052, new CampusStudent(24665052, "Vaibhav Bhatnagar", "vaibhavbhatnagar11506@gmail.com"));
        try {
            courseCatalog.put(101, new CampusCourse(101, "BTEC cse", 12500.00));
        } catch (InvalidCampusDataException e) {
            System.err.println("Failed to initialize BTEC cse course: " + e.getMessage());
        }

        System.out.println("=== Welcome to GNC SmartCampus Management System ===");
        System.out.println("👋 Pre-loaded: Vaibhav Bhatnagar (ID: 24665052) & BTEC cse (ID: 101)");

        while (systemRunning) {
            System.out.println("
1. Add Student
2. Add Course
3. Enroll Student
4. View Students
5. View Enrollments
6. Process Enrollment (Thread)
7. Exit");
            System.out.print("Select an option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int sId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        studentDatabase.put(sId, new CampusStudent(sId, name, email));
                        System.out.println("Student added successfully.");
                        break;

                    case 2:
                        System.out.print("Enter Course ID: ");
                        int cId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Course Name: ");
                        String cName = scanner.nextLine();
                        System.out.print("Enter Fee: ");
                        double fee = Double.parseDouble(scanner.nextLine());
                        
                        [span_14](start_span)// Handles custom exception[span_14](end_span)
                        courseCatalog.put(cId, new CampusCourse(cId, cName, fee)); 
                        System.out.println("Course added successfully.");
                        break;

                    case 3:
                        System.out.print("Enter Student ID to enroll: ");
                        int enrollSId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter Course ID to enroll in: ");
                        int enrollCId = Integer.parseInt(scanner.nextLine());

                        if (!studentDatabase.containsKey(enrollSId)) throw new InvalidCampusDataException("Student not found.");
                        if (!courseCatalog.containsKey(enrollCId)) throw new InvalidCampusDataException("Course not found.");

                        enrollmentRecords.putIfAbsent(enrollSId, new ArrayList<>());
                        enrollmentRecords.get(enrollSId).add(courseCatalog.get(enrollCId));
                        System.out.println("Enrollment staged. (Use Option 6 to process).");
                        break;

                    case 4:
                        System.out.println("
--- Registered Students ---");
                        for (CampusStudent s : studentDatabase.values()) System.out.println(s);
                        break;

                    case 5:
                        System.out.println("
--- Enrollment Records ---");
                        for (Integer id : enrollmentRecords.keySet()) {
                            System.out.println("Student: " + studentDatabase.get(id).getFullName());
                            for (CampusCourse c : enrollmentRecords.get(id)) {
                                System.out.println("  -> " + c.getTitle());
                            }
                        }
                        break;

                    case 6:
                        System.out.print("Enter Student ID to process: ");
                        int processId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Is this a priority enrollment? (true/false): ");
                        boolean isPriority = Boolean.parseBoolean(scanner.nextLine());
                        
                        if (enrollmentRecords.containsKey(processId)) {
                            CampusStudent student = studentDatabase.get(processId);
                            for (CampusCourse course : enrollmentRecords.get(processId)) {
                                Thread t = new Thread(new AsyncEnrollmentTask(student.getFullName(), course.getTitle(), isPriority));
                                t.start();
                            }
                        } else {
                            System.out.println("No enrollments found for this student.");
                        }
                        break;

                    case 7:
                        systemRunning = false;
                        System.out.println("Exiting SmartCampus System. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter valid numeric values where required.");
            } catch (InvalidCampusDataException e) {
                System.out.println("Campus Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}