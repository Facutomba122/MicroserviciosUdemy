package org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.CourseUser;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.User;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseRepository repository;

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
    public Optional<User> assignUser(User user, Long id) {
        Optional<Course> findCourseOptional = repository.findById(id);
        if (findCourseOptional.isPresent()){
            User userMsvc = userClientRest.detail(user.getId());
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);

            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User newUser, Long courseId) {
        Optional<Course> findCourseOptional = repository.findById(id);
        if (findCourseOptional.isPresent()){
            User userMsvc = userClientRest.create(newUser);
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);

            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(User deletedUser, Long courseId) {
        Optional<Course> findCourseOptional = repository.findById(courseId);
        if (findCourseOptional.isPresent()){
            User userMsvc = userClientRest.detail(deletedUser.getId());
            Course course = findCourseOptional.get();

            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }


}
