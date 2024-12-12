package com.bonifacio.my_ksa_api.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RegisterDto {
    @NotEmpty
    @NotBlank
    @Size(max = 32)
    private String username;
    @Email
    @NotBlank
    @NotEmpty
    private String email;
    @NotBlank
    @NotEmpty
    @Size(min = 8,max = 32)
    private String password;
    @Valid
    private AuthCreateRole roles;
}
