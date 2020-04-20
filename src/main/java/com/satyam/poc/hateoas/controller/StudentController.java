package com.satyam.poc.hateoas.controller;

import com.satyam.poc.hateoas.dao.StudentDAO;
import com.satyam.poc.hateoas.entity.Course;
import com.satyam.poc.hateoas.entity.Student;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/students", produces = "application/hal+json")
public class StudentController {

    private StudentDAO studentDAO;

    public StudentController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(produces = { "application/hal+json" })
    public CollectionModel<Student> getStudents() {
        List<Student> students = studentDAO.getStudents();

        // add links
        for (Student student : students) {
            long id = student.getId();
            student.add(linkTo(StudentController.class).slash(id).withSelfRel());
            student.add(linkTo(methodOn(StudentController.class).getStudentEnrollments(id)).withRel("enrollments"));
        }

        Link link = linkTo(StudentController.class).withSelfRel();
        return new CollectionModel<>(students, link);
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable final long studentId) {
        Student student = studentDAO.getStudent(studentId);
        student.add(linkTo(StudentController.class).slash(student.getId()).withSelfRel());
        student.add(linkTo(StudentController.class).withRel("students"));
        student.add(linkTo(methodOn(StudentController.class).getStudentEnrollments(studentId)).withRel("enrollments"));
        return student;
    }

    @GetMapping("/{studentId}/enrollments")
    public CollectionModel<Course> getStudentEnrollments(@PathVariable final long studentId) {
        List<Course> courses = studentDAO.getEnrolledCourses(studentId);

        for (Course course : courses) {
            course.add(linkTo(CourseController.class).slash(course.getId()).withSelfRel());
            course.add(linkTo(CourseController.class).withRel("courses"));
        }

        Link link = linkTo(methodOn(StudentController.class).getStudentEnrollments(studentId)).withSelfRel();
        return new CollectionModel<>(courses, link);
    }

}
