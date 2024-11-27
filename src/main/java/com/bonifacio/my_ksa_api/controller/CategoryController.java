package com.bonifacio.my_ksa_api.controller;

import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.dto.CategoryUpdateDto;
import com.bonifacio.my_ksa_api.controller.response.Response;
import com.bonifacio.my_ksa_api.services.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("api/v1/products/category")
@AllArgsConstructor
public class CategoryController {
    @Autowired
    private final ICategoryService categoryService;

    @GetMapping(value = {"","/"})
    public ResponseEntity<Response<?>> index(@PageableDefault(page = 0,size = 10) Pageable pageable){
        Response<?> response = this.categoryService.getAll(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"","/"})
    public ResponseEntity<Response<?>> store(
            @Valid @RequestBody CategoryInDto category,
            BindingResult result
            ){
        try{
            if(result.hasErrors()){
                return new ResponseEntity<>(Response.builder()
                        .message("Error")
                        .data(result.getAllErrors())
                        .status(false)
                        .build(),HttpStatus.BAD_REQUEST);
            }

            var data = this.categoryService.save(category);
            return new ResponseEntity<>(data,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder()
                    .message(e.getMessage())
                    .data(e)
                    .status(false)
                    .build(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = {"{slug}","{slug}/"})
    public ResponseEntity<Response<?>> show(
            @PathVariable String slug
    ){
        try{
            var response = this.categoryService.getBySlug(slug);
            if(!response.status()){
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder()
                    .message(e.getMessage())
                    .data(e)
                    .status(false)
                    .build(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping(value = {"/edit","edit/"})
    public ResponseEntity<Response<?>>update(
            @Valid @RequestBody CategoryUpdateDto category,
            BindingResult result
            ){
        try {
            if(result.hasErrors()){
                return new ResponseEntity<>(Response.builder()
                        .message("ERROR")
                        .data(result.hasErrors())
                        .status(false)
                        .build(),HttpStatus.BAD_REQUEST);
            }

            var response = this.categoryService.updateById(category);
            if(!response.status()) return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Response.builder()
                    .message("ERROR ".concat(e.getMessage()))
                    .data(e)
                    .status(false)
                    .build(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(value = {"{id}/delete","{id}/delete/"})
    public ResponseEntity<Response<?>> delete(
            @PathVariable UUID id
    ){
        this.categoryService.deleteById(id);
        return new ResponseEntity<>(Response.builder()
                .message("DELETE")
                .data(null)
                .status(true)
                .build(),HttpStatus.OK);
    }
}
