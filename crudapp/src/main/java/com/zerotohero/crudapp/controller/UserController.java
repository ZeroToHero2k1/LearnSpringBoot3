package com.zerotohero.crudapp.controller;

import com.zerotohero.crudapp.dto.response.ApiResponse;
import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
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
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        log.info("User name: {}",authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers()).build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId){
        return ApiResponse.<UserResponse>builder().result(userService.getUserById(userId)).build();
    }

    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();//ảo vcl ko cần truyền gì, lấy user từ token
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUserById(@PathVariable String userId,@RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder().result(userService.updateUserById(userId,request)).build();
    }
    @DeleteMapping("/{userId}")
    ApiResponse deleteUserById(@PathVariable String userId){
        userService.deleteUserById(userId);
        return ApiResponse.builder().result("User has been deleted").build();
    }
}
