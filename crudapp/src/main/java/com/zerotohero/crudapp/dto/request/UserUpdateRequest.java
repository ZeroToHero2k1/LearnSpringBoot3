package com.zerotohero.crudapp.dto.request;

import com.zerotohero.crudapp.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min=8,message="PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min=18)
    LocalDate dob;
    List<String> roles;


}
