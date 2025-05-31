package org.facundopinazo.springcloud.msv.courses.msvc_courses.clients;

import org.facundopinazo.springcloud.msv.courses.msvc_courses.entities.DTOs.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="msvc-users" , url="localhost:8090")
public interface UserClientRest {
    @GetMapping("/{id}")
    UserDTO detail(@PathVariable Long id);

    @PostMapping("/")
    UserDTO create(@RequestBody UserDTO userDTO);

    @GetMapping("/list-all")
    List<UserDTO> listAllByIds(@RequestParam Iterable<Long> ids);
}
