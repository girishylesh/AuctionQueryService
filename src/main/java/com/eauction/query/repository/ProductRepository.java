package com.eauction.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
