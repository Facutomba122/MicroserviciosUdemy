package org.facundopinazo.springcloud.msv.courses.msvc_courses.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.Course;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.User;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.servicies.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> create(@Valid @RequestBody Course newCourses, BindingResult result) {
        if (result.hasErrors()){
            return jsonValidation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(newCourses));
    }

    @PutMapping("/assing-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> OUser;
        try {
            OUser = courseService.assignUser(user, courseId);
        } catch (FeignException e){
            System.out.println("Se produjo un error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "invalid id")
            );
        }
        if (OUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(OUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User newUser, @PathVariable Long courseId){
        Optional<User> OUser;
        try {
            OUser = courseService.createUser(newUser, courseId);
        } catch (FeignException e){
            System.out.println("Se produjo un error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "invalid user")
            );
        }
        if (OUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(OUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User deleteUser, @PathVariable Long courseId){
        Optional<User> OUser;
        try {
            OUser = courseService.deleteUser(deleteUser, courseId);
        } catch (FeignException e){
            System.out.println("Se produjo un error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "invalid user")
            );
        }
        if (OUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(OUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course updatedCourses,BindingResult result , @PathVariable Long id) {
        if (result.hasErrors()){
            return jsonValidation(result);
        }

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

    private static ResponseEntity<HashMap<String, String>> jsonValidation(BindingResult result) {
        HashMap<String, String> errors = new HashMap<String, String>();
        result.getFieldErrors().forEach((err) -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
