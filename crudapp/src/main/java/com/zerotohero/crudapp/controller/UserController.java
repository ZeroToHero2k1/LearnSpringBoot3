package com.zerotohero.crudapp.controller;

import com.zerotohero.crudapp.dto.response.ApiResponse;
import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }
    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{userId}")
    UserResponse getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }
    @PutMapping("/{userId}")
    UserResponse updateUserById(@PathVariable String userId,@RequestBody UserUpdateRequest request){
        return userService.updateUserById(userId,request);
    }
    @DeleteMapping("/{userId}")
    String deleteUserById(@PathVariable String userId){
        userService.deleteUserById(userId);
        return "User has been deleted";
    }
}
