package com.scaler.userserviceauth.controllers;


import com.scaler.userserviceauth.dtos.LoginRequestDto;
import com.scaler.userserviceauth.dtos.SignUpRequestDto;
import com.scaler.userserviceauth.dtos.TokenDto;
import com.scaler.userserviceauth.dtos.UserDto;
import com.scaler.userserviceauth.models.User;
import com.scaler.userserviceauth.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getId());
//        userDto.setEmail(user.getEmail());
//        userDto.setRoles(user.getRoles());
        return UserDto.from(user);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto){

        return  null;
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue){

        return  null;
    }

//    @GetMapping("/logout")
//    public logOut(){
//
//    }

}