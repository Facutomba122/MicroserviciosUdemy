package org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Long id);
    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User newUser, Long courseId);
    Optional<User> deleteUser(User deletedUser, Long courseId);
}
