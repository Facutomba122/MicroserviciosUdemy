package org.facundopinazo.springcloud.msv.courses.msvc_courses.repositories;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}

