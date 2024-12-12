package com.bonifacio.my_ksa_api.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@JsonPropertyOrder({"username", "message", "status", "jwt"})
public class AuthResponse {
    private final String username;
    private final String message;
    private final String jwt;
    private final boolean status;
}
