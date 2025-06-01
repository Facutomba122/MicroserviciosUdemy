package org.facupinazo.springcloud.mvsc.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name="msvc-course",
        url = "localhost:8090"
)


public interface CourseClient {

    @DeleteMapping("/delete-user/{courseId}")
    void deleteUserFromCourse(Long courseId);

    @PostMapping("/add-user/{courseId}")
    void addUserToCourse(Long courseId);


}
