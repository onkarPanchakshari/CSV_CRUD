package com.onkarPanchakshari.io.CRUD_csv_read.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    public SubProduct() {}
    public SubProduct(String name, String unit, Product product) {
        this.name = name;
        this.unit = unit;
        this.product = product;
    }
}

