package service;

import dao.EnrollmentDAO;
import exception.PrerequisiteNotMetException;
import exception.EnrollmentException;
import model.Course;
import model.Student;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class EnrollmentService {
    private final EnrollmentDAO dao;
    private static final int MAX_CREDIT_LOAD = 18;

    public EnrollmentService(EnrollmentDAO dao) {
        this.dao = dao;
    }


    public void enrollStudentInCourse(String studentId, String courseId) throws EnrollmentException, PrerequisiteNotMetException {
        Student student = dao.getStudentById(studentId)
                .orElseThrow(() -> new EnrollmentException("Student with ID " + studentId + " not found."));
        Course course = dao.getCourseById(courseId)
                .orElseThrow(() -> new EnrollmentException("Course with ID " + courseId + " not found."));


        checkPrerequisites(student, course);


        if (student.getCurrentEnrollmentIds().contains(courseId) || student.getCompletedCourseIds().contains(courseId)) {
            throw new EnrollmentException(student.getName() + " is already enrolled or has completed " + course.getName() + ".");
        }


        int currentCredits = calculateCurrentCredits(student.getCurrentEnrollmentIds());
        if (currentCredits + course.getCredits() > MAX_CREDIT_LOAD) {
            throw new EnrollmentException(student.getName() + " enrollment exceeds the maximum credit load of " + MAX_CREDIT_LOAD + ".");
        }


        student.enrollCourse(courseId);
        dao.saveStudent(student);
        System.out.println("SUCCESS: " + student.getName() + " enrolled in " + course.getName() + ".");
    }


    private void checkPrerequisites(Student student, Course course) throws PrerequisiteNotMetException {
        List<String> requiredPrereqs = course.getPrerequisiteIds();
        for (String prereqId : requiredPrereqs) {
            if (!student.getCompletedCourseIds().contains(prereqId)) {
                throw new PrerequisiteNotMetException(
                        "Cannot enroll in " + course.getCourseId() + ". Missing prerequisite: " + prereqId);
            }
        }
    }


    public int calculateCurrentCredits(Collection<String> courseIds) {
        return courseIds.stream()
                .map(dao::getCourseById)
                .filter(Optional::isPresent)
                .mapToInt(opt -> opt.get().getCredits())
                .sum();
    }


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