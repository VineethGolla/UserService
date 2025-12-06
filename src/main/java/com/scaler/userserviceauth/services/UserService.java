package com.scaler.userserviceauth.services;

import com.scaler.userserviceauth.exceptions.InvalidTokenException;
import com.scaler.userserviceauth.exceptions.PasswordMismatchException;
import com.scaler.userserviceauth.models.Token;
import com.scaler.userserviceauth.models.User;
import lombok.Setter;

public interface UserService {
    User signUp(String name, String email, String password);
    String login(String email, String password) throws PasswordMismatchException;
    User ValidateToken(String tokenValue) throws InvalidTokenException;
}
