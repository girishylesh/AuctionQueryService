package com.eauction.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.entity.AuctionUser;

public interface AuctionUserRepository extends MongoRepository<AuctionUser, String>{

}
