package dao;

import model.Course;
import model.Student;
import java.util.Optional;
import java.util.Collection;

/**
 * Interface (Contract) for data persistence operations (DAO).
 * This abstracts the data storage layer and is comprehensive.
 */
public interface EnrollmentDAO {
    Optional<Student> getStudentById(String studentId);
    void saveStudent(Student student);
    Collection<Student> getAllStudents(); // Added for completeness

    Optional<Course> getCourseById(String courseId);
    void saveCourse(Course course); // Added for completeness
    Collection<Course> getAllCourses();
}