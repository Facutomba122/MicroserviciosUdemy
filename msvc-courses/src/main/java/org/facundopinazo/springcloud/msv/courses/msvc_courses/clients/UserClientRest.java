package org.facundopinazo.springcloud.msv.courses.msvc_courses.clients;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="msvc-users" , url="localhost:8090")
public interface UserClientRest {
    @GetMapping("/{id}")
    User detail(@PathVariable Long id);

    @PostMapping("/")
    User create(@RequestBody User user);

    @GetMapping("/list-all")
    List<User> listAllByIds(@RequestParam ArrayList<Long> ids);
}
