package org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.clients.UserClientRest;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.CourseUser;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.UserDTO;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseRepository repository;
    @Autowired
    private UserClientRest userClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UserDTO> assignUser(UserDTO userDTO, Long id) {
        Optional<Course> findCourseOptional = repository.findById(id);
        if (findCourseOptional.isPresent()){
            UserDTO userDTOMsvc = userClientRest.detail(userDTO.getId());
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDTOMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);

            return Optional.of(userDTOMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDTO> createUser(UserDTO newUserDTO, Long courseId) {
        Optional<Course> findCourseOptional = repository.findById(courseId);
        if (findCourseOptional.isPresent()){
            UserDTO userDTOMsvc = userClientRest.create(newUserDTO);
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDTOMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);

            return Optional.of(userDTOMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserDTO> deleteUser(UserDTO deletedUserDTO, Long courseId) {
        Optional<Course> findCourseOptional = repository.findById(courseId);
        if (findCourseOptional.isPresent()){
            UserDTO userDTOMsvc = userClientRest.detail(deletedUserDTO.getId());
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userDTOMsvc.getId());
            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userDTOMsvc);
        }

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<Course> findUsersByCourseId(Long id){
        Optional<Course> oCourse = repository.findById(id);
        if (oCourse.isPresent()){
            Course actualCourse = oCourse.get();
            List<UserDTO> users = null;
            if (!actualCourse.getCourseUsers().isEmpty()) {
                Iterable<Long> ids = actualCourse.getCourseUsers().stream().map(cu -> cu.getId()).collect(Collectors.toList());
                if (ids != null) {
                    users = userClientRest.listAllByIds(ids);
                }
                actualCourse.setUsers(users);
                return Optional.of(actualCourse);
            }
        }
        return Optional.empty();
    }
}
