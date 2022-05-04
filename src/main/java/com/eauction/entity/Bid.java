package com.eauction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("Bid")
public class Bid {
	@Id
	private String uid;
	private Double bidAmount;
	@DBRef
	private Product product;
	@DBRef
	private AuctionUser auctionUser;
}
