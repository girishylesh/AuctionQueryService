package com.eauction.events;

import com.eauction.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionUserCreatedEvent {
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
