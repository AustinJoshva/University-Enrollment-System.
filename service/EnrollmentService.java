package service;

import dao.EnrollmentDAO;
import exception.PrerequisiteNotMetException;
import exception.EnrollmentException;
import model.Course;
import model.Student;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The core Business Logic module. This handles all rules,
 * validation, and state changes (enroll, drop, check prerequisites).
 */
public class EnrollmentService {
    private final EnrollmentDAO dao;
    private static final int MAX_CREDIT_LOAD = 18;

    public EnrollmentService(EnrollmentDAO dao) {
        this.dao = dao;
    }

    /**
     * Attempts to enroll a student in a course, checking all university rules.
     * @throws EnrollmentException if the course/student is not found or other enrollment error occurs.
     * @throws PrerequisiteNotMetException if prerequisites are missing.
     */
    public void enrollStudentInCourse(String studentId, String courseId) throws EnrollmentException, PrerequisiteNotMetException {
        Student student = dao.getStudentById(studentId)
                .orElseThrow(() -> new EnrollmentException("Student with ID " + studentId + " not found."));
        Course course = dao.getCourseById(courseId)
                .orElseThrow(() -> new EnrollmentException("Course with ID " + courseId + " not found."));

        // 1. Check for Prerequisites (Functional Module 2)
        checkPrerequisites(student, course);

        // 2. Check Enrollment Conflicts/Rules
        if (student.getCurrentEnrollmentIds().contains(courseId) || student.getCompletedCourseIds().contains(courseId)) {
            throw new EnrollmentException(student.getName() + " is already enrolled or has completed " + course.getName() + ".");
        }

        // 3. Check Credit Load (Non-functional: Resource Efficiency/Rules)
        int currentCredits = calculateCurrentCredits(student.getCurrentEnrollmentIds());
        if (currentCredits + course.getCredits() > MAX_CREDIT_LOAD) {
            throw new EnrollmentException(student.getName() + " enrollment exceeds the maximum credit load of " + MAX_CREDIT_LOAD + ".");
        }

        // 4. Perform Enrollment and Save State
        student.enrollCourse(courseId);
        dao.saveStudent(student); // Persist the change
        System.out.println("SUCCESS: " + student.getName() + " enrolled in " + course.getName() + ".");
    }

    /**
     * Checks if a student meets all prerequisites for a given course.
     */
    private void checkPrerequisites(Student student, Course course) throws PrerequisiteNotMetException {
        List<String> requiredPrereqs = course.getPrerequisiteIds();
        for (String prereqId : requiredPrereqs) {
            if (!student.getCompletedCourseIds().contains(prereqId)) {
                throw new PrerequisiteNotMetException(
                        "Cannot enroll in " + course.getCourseId() + ". Missing prerequisite: " + prereqId);
            }
        }
    }

    /**
     * Calculates total current credit load. (Functional Module 3 support)
     */
    public int calculateCurrentCredits(Collection<String> courseIds) {
        return courseIds.stream()
                .map(dao::getCourseById)
                .filter(Optional::isPresent)
                .mapToInt(opt -> opt.get().getCredits())
                .sum();
    }

    // Functional Module 3: Reporting
    public String generateProgressReport(String studentId) throws EnrollmentException {
        Student student = dao.getStudentById(studentId)
                .orElseThrow(() -> new EnrollmentException("Student with ID " + studentId + " not found."));

        int totalCredits = student.getCompletedCourseIds().stream()
                .map(dao::getCourseById)
                .filter(Optional::isPresent)
                .mapToInt(opt -> opt.get().getCredits())
                .sum();

        StringBuilder report = new StringBuilder();
        report.append("\n================================================\n");
        report.append("DEGREE PROGRESS REPORT for ").append(student.getName()).append("\n");
        report.append("================================================\n");
        report.append("Total Completed Credits: ").append(totalCredits).append(" / 120 (Target)\n\n");
        report.append("Completed Courses:\n");
        student.getCompletedCourseIds().forEach(id -> dao.getCourseById(id).ifPresent(c ->
                report.append("- ").append(c.getCourseId()).append(": ").append(c.getName()).append("\n")
        ));
        report.append("\nCurrently Enrolled Courses (Credits: ").append(calculateCurrentCredits(student.getCurrentEnrollmentIds())).append("):\n");
        student.getCurrentEnrollmentIds().forEach(id -> dao.getCourseById(id).ifPresent(c ->
                report.append("- ").append(c.getCourseId()).append(": ").append(c.getName()).append("\n")
        ));
        report.append("================================================\n");
        return report.toString();
    }
}