package com.scaler.userserviceauth.services;

import com.scaler.userserviceauth.exceptions.InvalidTokenException;
import com.scaler.userserviceauth.exceptions.PasswordMismatchException;
import com.scaler.userserviceauth.models.Token;
import com.scaler.userserviceauth.models.User;
import com.scaler.userserviceauth.repositories.TokenRepository;
import com.scaler.userserviceauth.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@Getter
@Setter
@Primary
public class UserServiceImpl implements UserService {
    public UserRepository userRepository;
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    public TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public User signUp(String name, String email, String password) {
        //c heck if user already exists
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
    public Token login(String email, String password) throws PasswordMismatchException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isEmpty()) {
            //Redirect to signup page
        }
        User user = existingUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //validation failed
            throw new PasswordMismatchException("Incorrect pwd:");

        }
        Token token = new Token();
        token.setUser(user);

        //Apache commons lang3
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(120));

        token.setExpiryAt(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));


        return tokenRepository.save(token);
    }

    @Override
    public User ValidateToken(String tokenValue) throws InvalidTokenException {
        Optional<Token> tokenOptional = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(
                tokenValue,
                new Date()
        );
        if(tokenOptional.isEmpty()) {
            throw new InvalidTokenException("Invalid Token");
        }
        Token token = tokenOptional.get();
        return token.getUser();

    }
}
