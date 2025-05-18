package com.zerotohero.crudapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerotohero.crudapp.dto.request.UserCreationRequest;
import com.zerotohero.crudapp.dto.response.RoleResponse;
import com.zerotohero.crudapp.dto.response.UserResponse;
import com.zerotohero.crudapp.mapper.RoleMapper;
import com.zerotohero.crudapp.repository.RoleRepository;
import com.zerotohero.crudapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @Mock
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;
    private List<String> lstRoleRequest;
    private RoleMapper roleMapper;

    @BeforeEach
    void initData(){
        dob=LocalDate.of(1990,12,12);
        lstRoleRequest.add("USER");
        userCreationRequest= UserCreationRequest.builder()
                .username("john123")
                .firstName("john")
                .lastName("paul")
                .password("123456789")
                .dob(dob)
                .roles(lstRoleRequest)
                .build();

        var lstRole=roleRepository.findAllById(lstRoleRequest);
        var lstRoleResponse= lstRole.stream().map(roleMapper::toRoleResponse).collect(Collectors.toSet());

        userResponse=userResponse.builder()
                .username("john123")
                .firstName("john")
                .lastName("paul")
                .dob(dob)
                .roles(lstRoleResponse)
                .build();
    }

    @Test
    void createUser() throws Exception {
        //Given
        ObjectMapper objectMapper=new ObjectMapper();
        String content=objectMapper.writeValueAsString(userCreationRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);
        //When,Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
        );
    }
}
