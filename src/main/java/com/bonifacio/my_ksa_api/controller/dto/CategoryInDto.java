package com.bonifacio.my_ksa_api.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryInDto {
    @NotEmpty
    @NotBlank
    @Size(min = 0,max = 150)
    private String name;
    @Size(max = 5000)
    private String description;
}
