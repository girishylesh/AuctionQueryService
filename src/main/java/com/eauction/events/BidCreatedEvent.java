package com.eauction.events;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidCreatedEvent {
	private String uid;
	private Double bidAmount;
	private Product product;
    private AuctionUser auctionUser;
}
