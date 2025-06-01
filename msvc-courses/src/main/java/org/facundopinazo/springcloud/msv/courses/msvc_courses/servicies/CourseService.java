package org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.UserDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Optional<Course> findUsersByCourseId(Long id);
    Course save(Course course);
    void delete(Long id);
    void deleteUserFromCourse(Long id);
    Optional<UserDTO> assignUser(UserDTO userDTO, Long courseId);
    Optional<UserDTO> createUser(UserDTO newUserDTO, Long courseId);
    Optional<UserDTO> deleteUser(UserDTO deletedUserDTO, Long courseId);

}
