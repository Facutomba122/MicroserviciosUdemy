package org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Long id);
}
