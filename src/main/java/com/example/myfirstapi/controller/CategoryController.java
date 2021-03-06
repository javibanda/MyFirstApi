package com.example.myfirstapi.controller;

import com.example.myfirstapi.model.Category;
import com.example.myfirstapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        if (categoryIsEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(categoryRepository.findAll());
        }
    }

    @PostMapping("/category")
    public ResponseEntity<?> createNewCategory(@RequestBody Category newCategory){
        if (duplicateCategory(newCategory)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else if (isShortName(newCategory)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>(saveCategory(newCategory), HttpStatus.CREATED);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") long id, @RequestBody Category category){
        if (duplicateCategory(category)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else if (isShortName(category)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }else if (categoryNotExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(changeNameCategory(category, id), HttpStatus.OK);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        try {
            if (categoryExists(id)){
                categoryRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteAllCategory(){
        if (categoryIsEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            categoryRepository.deleteAll();
            return ResponseEntity.noContent().build();
        }
    }

    private boolean categoryIsEmpty(){
        return categoryRepository.findAll().isEmpty();
    }

    private boolean isShortName(Category category){
        return  (category.getName().length() < 3);
    }

    private boolean duplicateCategory(Category category){
        Optional<Category> categoryData = categoryRepository.findByName(category.getName());
        return categoryData.isPresent();
    }

    private boolean categoryExists(Long id){
        Optional<Category> categoryData = categoryRepository.findById(id);
        return categoryData.isPresent();
    }

    private boolean categoryNotExists(Long id){
        return !categoryExists(id);
    }

    private Category saveCategory(Category category){
        return categoryRepository.save(new Category(category.getName()));
    }

    private Category changeNameCategory(Category category, Long id){
        Category categoryChanged = categoryRepository.findById(id).get();
        categoryChanged.setName(category.getName());
        return categoryRepository.save(categoryChanged);
    }
}
