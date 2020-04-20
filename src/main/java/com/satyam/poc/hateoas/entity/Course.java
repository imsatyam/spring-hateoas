package com.satyam.poc.hateoas.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Course extends RepresentationModel<Course> {

    @Min(1)
    private int id;

    @NotEmpty
    private String name;

    public Course(@Min(1) int id) {
        this.id = id;
    }

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(Course course) {
        this.id = course.id;
        this.name = course.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
