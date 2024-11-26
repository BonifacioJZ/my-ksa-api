package com.bonifacio.my_ksa_api.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryOutDto{
    private UUID id;
    private String name;
    private String description;
    private String slug;
}

