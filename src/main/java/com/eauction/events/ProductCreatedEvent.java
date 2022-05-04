package com.eauction.events;

import java.time.LocalDate;

import com.eauction.entity.AuctionUser;
import com.eauction.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedEvent {
	private String uid;
	private String name;
	private String shortDesc;
	private String detailedDesc;
	private Double startingPrice;
	private Category category;
	private LocalDate bidEndDate;
	private AuctionUser auctionUser;
}
