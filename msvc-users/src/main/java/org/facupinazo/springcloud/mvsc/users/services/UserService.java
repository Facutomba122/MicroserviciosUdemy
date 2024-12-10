package org.facupinazo.springcloud.mvsc.users.services;

import org.facupinazo.springcloud.mvsc.users.models.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> listar();
    Optional<Users> findId(Long id);
    Users save(Users newUsers);
    void delete(Long id);

    
}
