package com.eauction.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder	
public class PlacedBid {
	private String uid;
	private Double bidAmount;
	private String buyerFirstName;
	private String buyerLastName;
	private String buyerEmail;
}
