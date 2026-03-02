package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;
import java.util.UUID;

public interface ProductRepository {
    Product create(Product product);
    Iterator<Product> findAll();
    Product findById(UUID id);
    Product edit(Product product);
    void delete(UUID id);
}