package model;

import java.util.Set;
import java.util.HashSet;


public class Student {
    private final String studentId;
    private final String name;
    private final Set<String> completedCourseIds;
    private final Set<String> currentEnrollmentIds;

    public Student(String studentId, String name, Set<String> completedCourseIds, Set<String> currentEnrollmentIds) {
        this.studentId = studentId;
        this.name = name;
        this.completedCourseIds = completedCourseIds != null ? new HashSet<>(completedCourseIds) : new HashSet<>();
        this.currentEnrollmentIds = currentEnrollmentIds != null ? new HashSet<>(currentEnrollmentIds) : new HashSet<>();
    }

    public void enrollCourse(String courseId) {
        currentEnrollmentIds.add(courseId);
    }

    public void dropCourse(String courseId) {
        currentEnrollmentIds.remove(courseId);
    }


    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public Set<String> getCompletedCourseIds() { return completedCourseIds; }
    public Set<String> getCurrentEnrollmentIds() { return currentEnrollmentIds; }
}