package com.eauction.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.eauction.enums.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("Product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1881534830321772103L;
	@Id
	private String uid;
	private String name;
	private String shortDesc;
	private String detailedDesc;
	private Double startingPrice;
	private Category category;
	private LocalDate bidEndDate;
	@DBRef
	private AuctionUser auctionUser;
}
