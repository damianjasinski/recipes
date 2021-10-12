package com.service;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder pwdEncoder;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired PasswordEncoder pwdEncoder) {
        this.userRepository = userRepository;
        this.pwdEncoder = pwdEncoder;
    }

    @Override
    public void checkUser(User newUser) {
        Optional<User> tmp = userRepository.findByEmailIgnoreCase(newUser.getEmail());

        if (tmp.isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        registerUser(newUser);
    }

    private void registerUser(User toRegister) {
        toRegister.setPassword(pwdEncoder.encode(toRegister.getPassword()));
        userRepository.save(toRegister);
    }
}
