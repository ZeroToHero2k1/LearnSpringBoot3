package com.zerotohero.crudapp.service;

import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.entity.User;
import com.zerotohero.crudapp.exception.AppException;
import com.zerotohero.crudapp.mapper.UserMapper;
import com.zerotohero.crudapp.repository.RoleRepository;
import com.zerotohero.crudapp.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("/test.properties")
public class UserServiceTest {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//    @TestConfiguration
//    static class CustomTestConfig {
//        @Bean
//        public UserRepository userRepository() {
//            return Mockito.mock(UserRepository.class);
//        }
//
//    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserCreationRequest userCreationRequest;
    private User user;

    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(1990, 12, 12);
        userCreationRequest = UserCreationRequest.builder()
                .username("john123")
                .firstName("john")
                .lastName("paul")
                .password("123456789")
                .dob(dob)
                .roles(List.of("USER"))
                .build();

        user = User.builder()
                .id("cf0600f538b3")
                .username("john123")
                .firstName("john")
                .lastName("paul")
                .password("123456789")
                .dob(dob)
                .roles(Set.of()) // Không test Role
                .build();

        // 👇 Fake mapper và encoder để không null
        Mockito.lenient().when(userMapper.toUser(any())).thenReturn(user);
        Mockito.lenient().when(userMapper.toUserResponse(any())).thenReturn(
                UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dob(user.getDob())
                        .build()
        );

        Mockito.lenient().when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
    }
    @Test
    void createUser_validRequest_success() {
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.createUser(userCreationRequest);

        Assertions.assertThat(response.getId()).isEqualTo("cf0600f538b3");
        Assertions.assertThat(response.getUsername()).isEqualTo("john123");

    }

    @Test
    void createUser_userExisted_fail() {
        Mockito.lenient().when(userRepository.existsByUsername(anyString())).thenReturn(true);

        AppException exception = assertThrows(AppException.class,
                () -> userService.createUser(userCreationRequest));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }
}
