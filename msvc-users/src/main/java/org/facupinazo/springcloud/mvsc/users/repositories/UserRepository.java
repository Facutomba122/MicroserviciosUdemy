package org.facupinazo.springcloud.mvsc.users.repositories;

import org.facupinazo.springcloud.mvsc.users.models.entity.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
}
