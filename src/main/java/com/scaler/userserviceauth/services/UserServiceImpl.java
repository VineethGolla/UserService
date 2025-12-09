package com.scaler.userserviceauth.services;

import com.scaler.userserviceauth.exceptions.InvalidTokenException;
import com.scaler.userserviceauth.exceptions.PasswordMismatchException;
import com.scaler.userserviceauth.models.Token;
import com.scaler.userserviceauth.models.User;
import com.scaler.userserviceauth.repositories.TokenRepository;
import com.scaler.userserviceauth.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Getter
@Setter
@Primary
public class UserServiceImpl implements UserService {
    public UserRepository userRepository;
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    public TokenRepository tokenRepository;
    public SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository, SecretKey secretKey) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
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
    public String login(String email, String password) throws PasswordMismatchException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            //Redirect to signup page
        }
        User user = existingUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //validation failed
            throw new PasswordMismatchException("Incorrect pwd:");

        }
//        Token token = new Token();
//        token.setUser(user);
//
//        //Apache commons lang3
//        token.setTokenValue(RandomStringUtils.randomAlphanumeric(120));
//
//        token.setExpiryAt(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));


//        return tokenRepository.save(token);
        //Generate a JWT token using JJWT library
//        String payload = " {\n" +
//                " \"userId\": \"123e4567-e89b-12d3-a456-426614174000\",\n" +
//                " \"email\": \"user@example.com\",\n" +
//                " \"roles\": [\"STUDENT\"],\n" +
//                "\"expiresAt\": \"2026-01-01T00:00:00Z\"\n" +
//                "}";
//
//        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
//        String token = Jwts.builder().compact();
//        return token;

        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "scaler.com");
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDate = calendar.getTime();

        claims.put("exp", expiryDate.getTime() / 1000);
//        claims.put("exp", expiryDate);
        claims.put("roles", user.getRoles());

//        MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = macAlgorithm.key().build();

//        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
//        String token = Jwts.builder().content(payloadBytes).compact();

        String jwtToken = Jwts.builder().claims(claims).signWith(secretKey).compact();

        //Create a UserSession as well here in order to store more details with the token.

        return jwtToken;

    }
    @Override
    public User ValidateToken(String tokenValue) throws InvalidTokenException {
        //validate JWT token
        JwtParser jwtparser = Jwts.parser().verifyWith(secretKey).build();

        //from claims extract users
        Claims claims = jwtparser.parseSignedClaims(tokenValue).getPayload();

//        Long userId = (Long) claims.get("userId");
//
//        Optional<Token> tokenOptional = tokenRepository.findByTokenValueAndUserIdAndExpiryAtGreaterThan(
//                tokenValue,
//                userId,
//                new Date()
//        );
//        if(tokenOptional.isEmpty()) {
//            throw new InvalidTokenException("Invalid Token");
//        }
//        Token token = tokenOptional.get();
//        return token.getUser();

        System.out.println("claims: " + claims);

//        Long expiryTime = ((Integer) claims.get("exp")).longValue() * 1000;
//        Long currentTime = System.currentTimeMillis();
//        Long expiryTime = (Long) claims.get("exp");
//        Long expiryTime = ((Integer) claims.get("exp")).longValue();
//        Long expiryTime = ((Number) claims.get("exp")).longValue();
//        Long currentTime = System.currentTimeMillis() / 1000;
//
//
//        if (expiryTime < currentTime) {
//            //Token is InValid.
//
//
//            //TODO - Check expiry Time and current time (Milliseconds vs Seconds) issue.
//            System.out.println("Expiry time : " + expiryTime);
//            System.out.println("Current time : " + currentTime);
//
//            throw new InvalidTokenException("Invalid JWT token.");
//        }

        //Token is Valid.
        Long userId2 = ((Number) claims.get("userId")).longValue();
        Optional<User> optionalUser = userRepository.findById(userId2);

        return optionalUser.get();
    }
}


/// secret key - in vault/ AWS secret manager