package com.jc.products.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jc.products.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	Product findByName(String name);
}