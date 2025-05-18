package com.zerotohero.crudapp.service;

import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.enums.Role;
import com.zerotohero.crudapp.exception.AppException;
import com.zerotohero.crudapp.exception.ErrorCode;
import com.zerotohero.crudapp.mapper.UserMapper;
import com.zerotohero.crudapp.repository.RoleRepository;
import com.zerotohero.crudapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user=userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var lstRole=roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(lstRole));

        return userMapper.toUserResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get Usser");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found")));
    }

    public UserResponse updateUserById(String id, UserUpdateRequest request){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));

        userMapper.updateUser(user,request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var lstRole=roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(lstRole));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo(){
        var context= SecurityContextHolder.getContext();
        String name=context.getAuthentication().getName();

        User user= userRepository.findByUsername(name).orElseThrow(()-> new AppException(ErrorCode.USER_NOTFOUND));
        return userMapper.toUserResponse(user);
    }

}
