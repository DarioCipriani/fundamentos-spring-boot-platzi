package com.fundamentosplatzi.springboot.fundamentos.service;

import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional // si existe algun error en el insert, esta anotacion nos permite hacer un rollback de todas las transacciones que se han ido registrando en la BD
    public void saveTransactional (List<User> users){
        users.stream()
                .peek(user -> LOG.info("Usuario insertado: " + user))
                .forEach(userRepository::save);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.delete(new User(id));
    }

    public User update(User newUser, Long id) {
        return
                userRepository.findById(id)
                    .map(
                            user -> {
                                newUser.setEmail(newUser.getEmail());
                                newUser.setBirthDate(newUser.getBirthDate());
                                newUser.setName(newUser.getName());
                                return userRepository.save(user);
                            }
                    ).get();
    }
}
