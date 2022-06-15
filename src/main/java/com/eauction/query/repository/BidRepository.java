package com.eauction.query.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.entity.Bid;

public interface BidRepository extends MongoRepository<Bid, String>{
   public Optional<List<Bid>> findByProductIn(List<String> uids);
   public Optional<List<Bid>> findByAuctionUserUid(String uid);
}
