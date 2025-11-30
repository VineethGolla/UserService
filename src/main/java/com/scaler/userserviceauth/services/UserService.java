package com.scaler.userserviceauth.services;

import com.scaler.userserviceauth.models.Token;
import com.scaler.userserviceauth.models.User;
import lombok.Setter;

public interface UserService {
    User signUp(String name, String email, String password);
    Token login(String email, String password);
    User ValidateToken(String tokenValue);
}
