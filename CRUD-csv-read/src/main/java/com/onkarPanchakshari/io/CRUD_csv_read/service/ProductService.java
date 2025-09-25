package com.onkarPanchakshari.io.CRUD_csv_read.service;

import com.onkarPanchakshari.io.CRUD_csv_read.entity.Product;
import com.onkarPanchakshari.io.CRUD_csv_read.entity.SubProduct;
import com.onkarPanchakshari.io.CRUD_csv_read.repositories.ProductRepository;
import com.onkarPanchakshari.io.CRUD_csv_read.repositories.SubProductRepository;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final SubProductRepository subRepo;

    public ProductService(ProductRepository productRepo, SubProductRepository subRepo) {
        this.productRepo = productRepo;
        this.subRepo = subRepo;
    }

    // Upload CSV
    public String uploadCSV(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                if (line.length < 3) continue;

                Product p = new Product();
                p.setName(line[0].trim());
                p.setType(line[1].trim());
                p.setUnit(line[2].trim());

                // generate sub-products
                List<SubProduct> subs = generateSubProducts(p);
                subs.forEach(sub -> sub.setProduct(p));
                p.setSubProducts(subs);

                productRepo.save(p);
            }

            return "CSV processed successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private List<SubProduct> generateSubProducts(Product product) {
        List<SubProduct> subs = new ArrayList<>();
        String name = product.getName().toLowerCase();

        if (name.equals("sugar")) {
            subs.add(new SubProduct(product.getName() + " 1Kg", "1Kg", product));
            subs.add(new SubProduct(product.getName() + " 10Kg", "10Kg", product));
        } else if (name.equals("tea")) {
            subs.add(new SubProduct(product.getName() + " 200gm", "200gm", product));
            subs.add(new SubProduct(product.getName() + " 500gm", "500gm", product));
            subs.add(new SubProduct(product.getName() + " 1Kg", "1Kg", product));
        } else if (name.equals("milk")) {
            subs.add(new SubProduct(product.getName() + " 1ltr", "1ltr", product));
            subs.add(new SubProduct(product.getName() + " 500ml", "500ml", product));
        }
        return subs;
    }


    public List<Product> getAll() { return productRepo.findAll(); }
    public Optional<Product> getById(Long id) { return productRepo.findById(id); }
    public void delete(Long id) { productRepo.deleteById(id); }
}

