package org.facundopinazo.springcloud.msv.courses.msvc_courses.entities;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.Pair;
import org.hibernate.mapping.Any;

@Entity
public class CourseUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof CourseUser cu) {
            return cu.getUserId().equals(this.getUserId());
        }
        return false;
    }
}
