package com.satyam.poc.hateoas.controller;

import com.satyam.poc.hateoas.dao.CourseDAO;
import com.satyam.poc.hateoas.entity.Course;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/courses", produces = "application/hal+json")
public class CourseController {

    private CourseDAO courseDAO;

    public CourseController(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @GetMapping(produces = { "application/hal+json" })
    public CollectionModel<Course> getCourses() {
        List<Course> courses = courseDAO.getCourses();

        // add links
        for (Course course : courses) {
            int id = course.getId();
            course.add(linkTo(CourseController.class).slash(id).withSelfRel());
        }
        Link link = linkTo(CourseController.class).withSelfRel();
        return new CollectionModel<>(courses, link);
    }

    @GetMapping("/{courseId}")
    public Course getStudentById(@PathVariable final int courseId) {
        Course course = courseDAO.getCourse(courseId);
        course.add(linkTo(CourseController.class).slash(course.getId()).withSelfRel());
        course.add(linkTo(CourseController.class).withRel("courses"));
        return course;
    }

}
