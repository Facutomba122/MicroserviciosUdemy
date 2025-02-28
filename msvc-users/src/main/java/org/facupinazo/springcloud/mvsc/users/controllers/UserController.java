package org.facupinazo.springcloud.mvsc.users.controllers;

import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.facupinazo.springcloud.mvsc.users.models.entity.Users;
import org.facupinazo.springcloud.mvsc.users.services.UserService;
import org.facupinazo.springcloud.mvsc.users.utils.MyExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Users> listar(){
        return userService.listar();
    }

    @GetMapping("/list-all")
    public List<Users> listAllByIds(@RequestParam ArrayList<Long> ids){
        return userService.listAllByIds(ids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Users> optionalUsers = userService.findId(id);
        if (optionalUsers.isPresent()){
            return ResponseEntity.ok().body(optionalUsers.get());
        }
        System.out.println("Usuario no encontrado con ID: " + id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Users newUsers, BindingResult result){

        if (result.hasErrors()){
            return jsonValidation(result);
        }
        try {
            Users response = userService.save(newUsers);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MyExceptions.EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e){
            System.out.println("ERROR AL CREAR USUARIO: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Users updatedUsers, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return jsonValidation(result);
        }
        if (updatedUsers != null && id != null){
            Optional<Users> optionalUsers = userService.findId(id);
            if (optionalUsers.isPresent()){
                Users oldUser = optionalUsers.get();
                if (updatedUsers.getEmail() != null && !updatedUsers.getEmail().isEmpty()){
                    oldUser.setEmail(updatedUsers.getEmail());
                }
                if (updatedUsers.getName() != null && !updatedUsers.getName().isEmpty()){
                    oldUser.setName(updatedUsers.getName());
                }
                if (updatedUsers.getPassword() != null && !updatedUsers.getPassword().isEmpty()){
                    oldUser.setPassword(updatedUsers.getPassword());
                }
                userService.save(oldUser);
                return ResponseEntity.ok().body(oldUser);
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (id == null)ResponseEntity.badRequest().build();
        Optional<Users> oUser = userService.findId(id);
        if (oUser.isPresent()){
            userService.delete(id);
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

