package com.satyam.poc.hateoas.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student extends RepresentationModel<Student> {

    @Min(1)
    private long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private List<Course> enrolledCourses;

    public Student(@Min(1) long id) {
        this.id = id;
    }

    public Student(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Student student) {
        this.id = student.id;
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.enrolledCourses = student.enrolledCourses;
    }

    public void enroll(Course course) {
        if (Objects.isNull(course)) {
            return;
        }

        if (this.enrolledCourses == null) {
            this.enrolledCourses = new ArrayList();
        }
        this.enrolledCourses.add(course);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
