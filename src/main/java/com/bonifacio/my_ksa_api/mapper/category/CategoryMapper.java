package com.bonifacio.my_ksa_api.mapper.category;

import com.bonifacio.my_ksa_api.controller.dto.CategoryDetailsDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryOutDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryUpdateDto;
import com.bonifacio.my_ksa_api.persistence.entities.CategoryEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper implements ICategoryMapper  {
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public CategoryOutDto categoryToCategoryOutDto(CategoryEntity category) {
        if(category==null) return null;
        return modelMapper.map(category,CategoryOutDto.class);
    }

    @Override
    public CategoryEntity categoryInDtoToCategory(CategoryInDto category) {
        if(category == null) return null;
        return modelMapper.map(category,CategoryEntity.class);
    }

    @Override
    public CategoryDetailsDto categoryToCategoryDetailsDto(CategoryEntity category) {
        if(category == null) return null;
        return modelMapper.map(category,CategoryDetailsDto.class);
    }

    @Override
    public CategoryEntity categoryUpdateDtoToCategory(CategoryUpdateDto category) {
        if(category==null) return null;
        return modelMapper.map(category,CategoryEntity.class);
    }
}
