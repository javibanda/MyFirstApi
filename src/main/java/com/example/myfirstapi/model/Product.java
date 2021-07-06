package com.example.myfirstapi.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private float price;
    private boolean enable;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;
}
