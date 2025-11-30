package com.scaler.userserviceauth.dtos;

import com.scaler.userserviceauth.models.Role;
import com.scaler.userserviceauth.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private Long userId;
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        if(user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
