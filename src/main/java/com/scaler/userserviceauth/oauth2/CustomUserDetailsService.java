package com.scaler.userserviceauth.oauth2;

import com.scaler.userserviceauth.models.User;
import com.scaler.userserviceauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//we may have many many users, so creating a bean doesn't make sense
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> OptionalUser = userRepository.findByEmail(email);
        if(OptionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = OptionalUser.get();
        return new CustomUserDetails(user);
    }
}
