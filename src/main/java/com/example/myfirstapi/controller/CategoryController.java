package com.example.myfirstapi.controller;

import com.example.myfirstapi.model.Category;
import com.example.myfirstapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(categories);
        }
    }
}
