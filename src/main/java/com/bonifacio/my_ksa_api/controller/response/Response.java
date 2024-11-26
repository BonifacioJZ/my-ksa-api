package com.bonifacio.my_ksa_api.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@JsonPropertyOrder({"message", "status", "data"})
public record Response<T>(String message, T data, boolean status) {
}