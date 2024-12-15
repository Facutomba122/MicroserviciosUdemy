package org.facundopinazo.springcloud.msv.courses.msvc_courses.controllers;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> listar() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Course> optionalCourses = courseService.findById(id);
        if (optionalCourses.isPresent()) {
            return ResponseEntity.ok().body(optionalCourses.get());
        }
        System.out.println("Curso no encontrado con ID: " + id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course newCourses) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(newCourses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Course updatedCourses, @PathVariable Long id) {
        if (updatedCourses != null && id != null) {
            Optional<Course> optionalCourses = courseService.findById(id);
            if (optionalCourses.isPresent()) {
                Course oldCourse = optionalCourses.get();
                oldCourse.setName(updatedCourses.getName());
                courseService.save(oldCourse);
                return ResponseEntity.ok().body(oldCourse);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id == null) ResponseEntity.badRequest().build();
        Optional<Course> oCourse = courseService.findById(id);
        if (oCourse.isPresent()) {
            courseService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
