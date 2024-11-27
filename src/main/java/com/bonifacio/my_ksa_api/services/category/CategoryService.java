package com.bonifacio.my_ksa_api.services.category;

import com.bonifacio.my_ksa_api.controller.dto.CategoryDetailsDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryOutDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryUpdateDto;
import com.bonifacio.my_ksa_api.controller.response.Response;
import com.bonifacio.my_ksa_api.mapper.category.ICategoryMapper;
import com.bonifacio.my_ksa_api.persistence.entities.CategoryEntity;
import com.bonifacio.my_ksa_api.persistence.reporsitory.ICategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    @Autowired
    private final ICategoryRepository categoryRepository;
    @Autowired
    private final ICategoryMapper categoryMapper;

    @Override
    public Response<Page<CategoryOutDto>> getAll(Pageable pageable) {
        Page<CategoryOutDto> data = this.categoryRepository.findAll(pageable)
                .map(this.categoryMapper::categoryToCategoryOutDto);
        return new Response<>("Categorias",data,true);
    }

    @Override
    public Response<CategoryOutDto> save(CategoryInDto category) {
        CategoryEntity data = this.categoryMapper.categoryInDtoToCategory(category);
        data.generateSlug();
        data = this.categoryRepository.save(data);
        CategoryOutDto out = this.categoryMapper.categoryToCategoryOutDto(data);
        return new Response<>("Categoria ".concat(out.getName()),out,true);
    }

    @Override
    public Response<CategoryDetailsDto> getBySlug(String slug) {
        Optional<CategoryEntity> data = this.categoryRepository.findBySlug(slug);
        if (data.isEmpty()) return new Response<>("NOT FOUND",null,false);
        var out = this.categoryMapper.categoryToCategoryDetailsDto(data.get());
        return  new Response<>("FOUND",out,true);
    }

    @Override
    public Response<CategoryOutDto> updateById(CategoryUpdateDto updateDto) {
       try{

           if(!this.categoryRepository.existsById(updateDto.getId())) return new Response<>("NOT FOUND",null,false);

           CategoryEntity category = this.categoryMapper.categoryUpdateDtoToCategory(updateDto);
           category.generateSlug();
           category = this.categoryRepository.save(category);


           CategoryOutDto out = this.categoryMapper.categoryToCategoryOutDto(category);
           return new Response<>("UPDATED",out,true);

       } catch (Exception e) {
           return new Response<>(e.getMessage(),null,false);
       }

    }

    @Override
    public void deleteById(UUID id) {
        this.categoryRepository.deleteById(id);
    }
}
