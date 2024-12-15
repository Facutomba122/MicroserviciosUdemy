package org.facupinazo.springcloud.mvsc.users.repositories;

import org.facupinazo.springcloud.mvsc.users.models.entity.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
}
