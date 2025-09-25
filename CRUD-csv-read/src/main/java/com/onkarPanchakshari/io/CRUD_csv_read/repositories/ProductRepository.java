package com.onkarPanchakshari.io.CRUD_csv_read.repositories;

import com.onkarPanchakshari.io.CRUD_csv_read.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
