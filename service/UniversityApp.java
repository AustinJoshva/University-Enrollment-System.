import dao.InMemoryEnrollmentDAO;
import dao.EnrollmentDAO;
import exception.PrerequisiteNotMetException;
import exception.EnrollmentException;
import service.EnrollmentService;

import java.util.Scanner;

public class UniversityApp {

    public static void main(String[] args) {

        EnrollmentDAO dao = new InMemoryEnrollmentDAO();
        EnrollmentService service = new EnrollmentService(dao);

        System.out.println("--- University Course Enrollment System ---");
        System.out.println("Loaded 2 Students (S1001, S1002) and 3 Courses (MA101, PH201, EE305).");
        System.out.println("MA101 has no prereqs. PH201 requires MA101. EE305 requires PH201.");
        System.out.println("Alice (S1001) has completed MA101.");
        System.out.println("Bob (S1002) has no completed courses.\n");

        Scanner scanner = new Scanner(System.in);
        String studentId = null;

        while (studentId == null) {
            System.out.print("Enter Student ID to login (S1001 or S1002): ");
            String input = scanner.nextLine().trim();
            if (dao.getStudentById(input).isPresent()) {
                studentId = input;
                System.out.println("Logged in as: " + dao.getStudentById(studentId).get().getName() + ".");
            } else {
                System.out.println("Invalid Student ID. Please try again.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Enroll in Course (Enter ID)");
            System.out.println("2. View Available Courses");
            System.out.println("3. Generate Progress Report");
            System.out.println("4. Exit");
            System.out.print("Select option: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        System.out.print("Enter Course ID to enroll (e.g., PH201, EE305): ");
                        String courseId = scanner.nextLine().toUpperCase();
                        service.enrollStudentInCourse(studentId, courseId);
                        break;
                    case 2:
                        System.out.println("\n--- Available Courses ---");
                        dao.getAllCourses().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.println(service.generateProgressReport(studentId));
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Please enter a valid number for the option.");
            } catch (EnrollmentException e) {
                System.err.println("ENROLLMENT FAILED: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An unexpected system error occurred: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using the Enrollment System.");
        scanner.close();
    }
}