package com.bonifacio.my_ksa_api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class CategoryUpdateDto {
    @NotNull
    private UUID id;
    @NotEmpty
    @NotBlank
    @Size(min = 0,max = 150)
    private String name;
    @Size(max = 5000)
    private String description;
}
