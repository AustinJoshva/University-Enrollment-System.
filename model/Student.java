package model;

import java.util.Set;
import java.util.HashSet;

/**
 * Represents a student's record.
 * Tracks personal info, completed courses, and current enrollment.
 */
public class Student {
    private final String studentId;
    private final String name;
    private final Set<String> completedCourseIds;
    private final Set<String> currentEnrollmentIds;

    // This is the correct 4-argument constructor required by the DAO
    public Student(String studentId, String name, Set<String> completedCourseIds, Set<String> currentEnrollmentIds) {
        this.studentId = studentId;
        this.name = name;
        this.completedCourseIds = completedCourseIds != null ? new HashSet<>(completedCourseIds) : new HashSet<>();
        this.currentEnrollmentIds = currentEnrollmentIds != null ? new HashSet<>(currentEnrollmentIds) : new HashSet<>();
    }

    // Mutator methods (used by the Service layer to update state)
    public void enrollCourse(String courseId) {
        currentEnrollmentIds.add(courseId);
    }

    public void dropCourse(String courseId) {
        currentEnrollmentIds.remove(courseId);
    }

    // Getters (used by the Service layer and Application layer)
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public Set<String> getCompletedCourseIds() { return completedCourseIds; }
    public Set<String> getCurrentEnrollmentIds() { return currentEnrollmentIds; }
}