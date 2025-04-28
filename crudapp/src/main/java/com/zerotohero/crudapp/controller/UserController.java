package com.zerotohero.crudapp.controller;

import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody @Valid UserCreationRequest request){
        return userService.createUser(request);
    }
    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{userId}")
    User getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }
    @PutMapping("/{userId}")
    User updateUserById(@PathVariable String userId,@RequestBody UserUpdateRequest request){
        return userService.updateUserById(userId,request);
    }
    @DeleteMapping("/{userId}")
    String deleteUserById(@PathVariable String userId){
        userService.deleteUserById(userId);
        return "User has been deleted";
    }
}
