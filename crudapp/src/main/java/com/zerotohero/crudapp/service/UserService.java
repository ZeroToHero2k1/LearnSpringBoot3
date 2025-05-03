package com.zerotohero.crudapp.service;

import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.request.UserUpdateRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.exception.AppException;
import com.zerotohero.crudapp.exception.ErrorCode;
import com.zerotohero.crudapp.mapper.UserMapper;
import com.zerotohero.crudapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user=userMapper.toUser(request);
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found")));
    }

    public UserResponse updateUserById(String id, UserUpdateRequest request){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));

        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }
}
