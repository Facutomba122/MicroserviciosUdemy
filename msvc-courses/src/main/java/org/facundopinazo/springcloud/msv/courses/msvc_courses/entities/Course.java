package org.facundopinazo.springcloud.msv.courses.msvc_courses.entities;

import jakarta.persistence.*;
import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers = new ArrayList<CourseUser>();

    @Transient
    private List<UserDTO> userDTOS = new ArrayList<UserDTO>();;


    public List<UserDTO> getUsers() {
        return userDTOS;
    }

    public void setUsers(List<UserDTO> userDTOS) {
        this.userDTOS = userDTOS;
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public void addCourseUser(CourseUser courseUser) {
        this.courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {
        this.courseUsers.remove(courseUser);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
