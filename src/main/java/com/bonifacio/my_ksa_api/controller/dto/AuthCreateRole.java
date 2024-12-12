package com.bonifacio.my_ksa_api.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthCreateRole {
    @Size(max = 3)
    List<String> roles;
}
