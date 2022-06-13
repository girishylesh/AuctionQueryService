package com.eauction.query.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	Optional<List<Product>> findByAuctionUserUidAndBidEndDateGreaterThanEqual(String uid, LocalDate date);
	Optional<List<Product>> findByBidEndDateGreaterThanEqual(LocalDate date);
}
