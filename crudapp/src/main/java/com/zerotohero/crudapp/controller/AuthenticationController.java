package com.zerotohero.crudapp.controller;


import com.nimbusds.jose.JOSEException;
import com.zerotohero.crudapp.dto.request.IntrospectRequest;
import com.zerotohero.crudapp.dto.request.InvalidTokenRequest;
import com.zerotohero.crudapp.dto.request.RefreshTokenRequest;
import com.zerotohero.crudapp.dto.response.ApiResponse;
import com.zerotohero.crudapp.dto.request.AuthenticationRequest;
import com.zerotohero.crudapp.dto.response.AuthenticationResponse;
import com.zerotohero.crudapp.dto.response.IntrospectResponse;
import com.zerotohero.crudapp.entity.InvalidatedToken;
import com.zerotohero.crudapp.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result=authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result=authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody InvalidTokenRequest request) throws ParseException, JOSEException {
        authenticationService.logOut(request);
        return ApiResponse.<Void>builder().message("Logout success").build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        var result=authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
