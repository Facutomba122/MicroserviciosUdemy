package org.facundopinazo.springcloud.msv.courses.msvc_courses.repositories;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("DELETE FROM CourseUser cu WHERE cu.userId = ?1 ")
    void deleteUserFromCourse(Long id);


}

