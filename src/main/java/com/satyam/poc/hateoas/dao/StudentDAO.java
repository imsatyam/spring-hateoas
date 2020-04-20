package com.satyam.poc.hateoas.dao;

import com.satyam.poc.hateoas.entity.Course;
import com.satyam.poc.hateoas.entity.Student;
import com.satyam.poc.hateoas.exception.DataException;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 This class simulates the DAO layer by providing static data.
 */
@Component
public class StudentDAO {

    private static Set<Student> students;

    static {
        students = new HashSet<>(3);
        students.add(new Student(1, "John", "Smith"));
        students.add(new Student(2, "Jane", "Smith"));
        students.add(new Student(3, "John", "Doe"));
    }

    private CourseDAO courseDAO;

    public StudentDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public List<Student> getStudents() {
       return students.stream()
                .map(s -> new Student(s)).collect(Collectors.toList());
    }

    public Student getStudent(long id) {
        return getStudents().stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addStudent(@NotNull @Valid Student student) {
        boolean isAdded = students.add(student);
        if (!isAdded) {
            throw new DataException(String.format("Student with id %s already exists.", student.getId()));
        }
    }

    public void updateStudent(@NotNull @Valid Student student) {
        Student existingStudent = students.stream()
                .filter(s -> s.getId() == student.getId())
                .findFirst()
                .orElse(null);
        if (existingStudent == null) {
            throw new DataException(String.format("No student found with course id: %s", student.getId()));
        }
        existingStudent.setLastName(student.getLastName());   // this is just a symbolic update
    }

    public void deleteStudent(long id) {
        students.remove(new Student(id));
    }

    public List<Course> getEnrolledCourses(long studentId) {
        return courseDAO.getCourses();  // lets say the students have enrolled in all the three courses to begin with
    }

}
