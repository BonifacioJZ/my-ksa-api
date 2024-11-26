package com.bonifacio.my_ksa_api.services.category;

import com.bonifacio.my_ksa_api.controller.dto.CategoryDetailsDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryOutDto;
import com.bonifacio.my_ksa_api.controller.response.Response;
import com.bonifacio.my_ksa_api.persistence.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    Response<Page<CategoryOutDto>> getAll(Pageable pageable);
    Response<CategoryOutDto> save(CategoryInDto category);
    Response<CategoryDetailsDto> getBySlug(String slug);
}
