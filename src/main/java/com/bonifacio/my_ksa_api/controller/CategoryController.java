package com.bonifacio.my_ksa_api.controller;

import com.bonifacio.my_ksa_api.controller.dto.CategoryInDto;
import com.bonifacio.my_ksa_api.controller.response.Response;
import com.bonifacio.my_ksa_api.services.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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
            return new ResponseEntity<>(data,HttpStatus.OK);
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
}
