package dao;

import model.Course;
import model.Student;
import java.util.Optional;
import java.util.Collection;


public interface EnrollmentDAO {
    Optional<Student> getStudentById(String studentId);
    void saveStudent(Student student);
    Collection<Student> getAllStudents();

    Optional<Course> getCourseById(String courseId);
    void saveCourse(Course course); 
    Collection<Course> getAllCourses();
}