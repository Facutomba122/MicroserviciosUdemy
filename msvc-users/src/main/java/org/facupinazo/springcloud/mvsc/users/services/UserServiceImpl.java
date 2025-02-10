package org.facupinazo.springcloud.mvsc.users.services;

import org.facupinazo.springcloud.mvsc.users.models.entity.Users;
import org.facupinazo.springcloud.mvsc.users.repositories.UserRepository;
import org.facupinazo.springcloud.mvsc.users.utils.MyExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Users> listar() {
        return (List<Users>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> findId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Users save(Users newUsers) {
        if (!newUsers.getEmail().isEmpty()  || repository.findByEmail(newUsers.getEmail()).isPresent()) {
            throw new MyExceptions.EmailAlreadyExistsException();
        }
       return repository.save(newUsers);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        System.out.println("Delete succesfull");
    }
}
