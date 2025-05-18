package com.zerotohero.crudapp.dto.request;

import com.zerotohero.crudapp.exception.ErrorCode;
import com.zerotohero.crudapp.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min=3,message= "USERNAME_INVALID")
    String username;
    @Size(min=8,message="PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min=18,message ="INVALID_DOB")
    LocalDate dob;

    List<String> roles;


}
