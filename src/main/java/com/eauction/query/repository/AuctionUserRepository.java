package com.eauction.query.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.entity.AuctionUser;

public interface AuctionUserRepository extends MongoRepository<AuctionUser, String>{
	public Optional<AuctionUser> findByEmail(String email);
}
