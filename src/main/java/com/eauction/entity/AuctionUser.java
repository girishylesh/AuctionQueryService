package com.eauction.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.eauction.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Document("AuctionUser")
public class AuctionUser implements Serializable {

	private static final long serialVersionUID = -857768027816873795L;
	@Id
	private String uid;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private Long pin;
	private Long phone;
	private String email;
	private UserType userType;
}
