package com.scaler.userserviceauth.services;

import com.scaler.userserviceauth.models.Token;
import com.scaler.userserviceauth.models.User;
import com.scaler.userserviceauth.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@Setter
@Primary
public class UserServiceImpl implements UserService {
    public UserRepository userRepository;
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signUp(String name, String email, String password) {
        //heck if user already exists
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()) {
            //redirect to the login page
            return existingUser.get();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        //user BCrypt password before saving
       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //created a Bean under configs, so no need to create Bcrypy obj all the time.
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public User ValidateToken(String tokenValue) {
        return null;
    }
}
