package com.example.myfirstapi.controller;

import com.example.myfirstapi.model.Category;
import com.example.myfirstapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/category")
    public ResponseEntity<?> createNewCategory(@RequestBody Category newCategory){
        if (categoryExists(newCategory.getName())){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>(saveCategory(newCategory), HttpStatus.CREATED);
        }
    }

    private boolean categoryExists(String name){
        List<Category> categoryList  = categoryRepository.findByName(name);
        return !categoryList.isEmpty();
    }

    private Category saveCategory(Category category){
        return categoryRepository.save(new Category(category.getName()));
    }
}
