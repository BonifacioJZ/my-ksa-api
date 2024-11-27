package com.bonifacio.my_ksa_api.mapper.category;

import com.bonifacio.my_ksa_api.controller.dto.CategoryDetailsDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryOutDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryUpdateDto;
import com.bonifacio.my_ksa_api.persistence.entities.CategoryEntity;
import org.springframework.stereotype.Service;

public interface ICategoryMapper {
    CategoryOutDto categoryToCategoryOutDto(CategoryEntity category);
    CategoryEntity categoryInDtoToCategory(CategoryInDto category);
    CategoryDetailsDto categoryToCategoryDetailsDto(CategoryEntity category);
    CategoryEntity categoryUpdateDtoToCategory(CategoryUpdateDto category);
}
