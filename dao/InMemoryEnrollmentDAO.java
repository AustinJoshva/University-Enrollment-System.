package dao;

import model.Course;
import model.Student;
import java.util.*;

/**
 * In-Memory implementation of the EnrollmentDAO interface.
 * Uses HashMaps to store data, suitable for demonstration/testing.
 */
public class InMemoryEnrollmentDAO implements EnrollmentDAO {
    // These maps simulate a database
    private final Map<String, Student> studentStore = new HashMap<>();
    private final Map<String, Course> courseStore = new HashMap<>();

    public InMemoryEnrollmentDAO() {
        initializeCourses();
        initializeStudents();
    }

    // --- Student DAO Methods ---
    @Override
    public Optional<Student> getStudentById(String studentId) {
        return Optional.ofNullable(studentStore.get(studentId));
    }

    @Override
    public void saveStudent(Student student) {
        studentStore.put(student.getStudentId(), student);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentStore.values();
    }

    // --- Course DAO Methods ---
    @Override
    public Optional<Course> getCourseById(String courseId) {
        return Optional.ofNullable(courseStore.get(courseId));
    }

    @Override
    public void saveCourse(Course course) {
        courseStore.put(course.getCourseId(), course);
    }

    @Override
    public Collection<Course> getAllCourses() {
        return courseStore.values();
    }

    // --- Sample Data Initialization ---

    private void initializeCourses() {
        // Course: Calculus I (No Prereqs)
        courseStore.put("MA101", new Course("MA101", "Calculus I", 4, Collections.emptyList()));
        // Course: Physics I (Requires MA101)
        courseStore.put("PH201", new Course("PH201", "Physics I", 4, List.of("MA101")));
        // Course: Circuits (Requires PH201)
        courseStore.put("EE305", new Course("EE305", "Electrical Circuits", 3, List.of("PH201")));
    }

    private void initializeStudents() {
        // Alice (S1001): Completed MA101, Currently enrolled in nothing
        studentStore.put("S1001", new Student("S1001", "Alice Johnson",
                new HashSet<>(List.of("MA101")),
                new HashSet<>()));
        // Bob (S1002): No completed courses, Currently enrolled in nothing
        studentStore.put("S1002", new Student("S1002", "Bob Smith",
                new HashSet<>(),
                new HashSet<>()));
    }
}