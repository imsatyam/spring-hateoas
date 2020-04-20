package com.satyam.poc.hateoas.dao;

import com.satyam.poc.hateoas.entity.Course;
import com.satyam.poc.hateoas.exception.DataException;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simulates DAO layer with static data
 */
@Component
public class CourseDAO {

    private static Set<Course> availableCourses;

    static {
        availableCourses = new HashSet<>(3);
        availableCourses.add(new Course(1, "HATEOAS - An introduction"));
        availableCourses.add(new Course(2, "HATEOAS with Spring"));
        availableCourses.add(new Course(3, "Understanding Hypermedia"));
    }

    public List<Course> getCourses() {
        return availableCourses.stream().map(c -> new Course(c)).collect(Collectors.toList());
    }

    public Course getCourse(int id) {
        return getCourses().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addCourse(@NotNull @Valid Course course) {
        boolean isAdded = availableCourses.add(course);
        if (!isAdded) {
            throw new DataException(String.format("Course with id %s already exists.", course.getId()));
        }
    }

    public void updateCourse(@NotNull @Valid Course course) {
        Course existingCourse = availableCourses.stream()
                .filter(c -> c.getId() == course.getId())
                .findFirst()
                .orElse(null);
        if (existingCourse == null) {
            throw new DataException(String.format("No course found with course id: %s", course.getId()));
        }
        existingCourse.setName(course.getName());
    }

    public void deleteCourse(int id) {
        availableCourses.remove(new Course(id));
    }
}
