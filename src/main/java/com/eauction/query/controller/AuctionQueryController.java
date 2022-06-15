package com.eauction.query.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Product;
import com.eauction.query.dto.GetAuctionUserQuery;
import com.eauction.query.dto.GetProductBidsQuery;
import com.eauction.query.dto.GetProductQuery;
import com.eauction.query.dto.PlacedBid;
import com.eauction.query.exception.UserNotFoundException;

@RestController
@RequestMapping(value = "/e-auction/api/v1/query")
public class AuctionQueryController {
	
	@Autowired
    private QueryGateway queryGateway;
	
	@GetMapping("/user/{email}")
	public ResponseEntity<?> getAuctionUser(@PathVariable String email) throws Exception {	
		AuctionUser user = queryGateway.query(new GetAuctionUserQuery(email), ResponseTypes.instanceOf(AuctionUser.class)).get();
		if(ObjectUtils.isEmpty(user) || StringUtils.isBlank(user.getEmail())) {
			throw new UserNotFoundException("User not found");
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	

	@GetMapping("/products/{userUid}")
	public List<Product> getProducts(@PathVariable String userUid) throws Exception {	
		return queryGateway.query(GetProductQuery.builder().userUid(userUid).build(), ResponseTypes.multipleInstancesOf(Product.class)).get();
	}
	
	@GetMapping("/show-bids/{userUid}")
	public List<PlacedBid> getBids(@PathVariable String userUid) throws Exception {	
		return queryGateway.query(new GetProductBidsQuery(userUid), ResponseTypes.multipleInstancesOf(PlacedBid.class)).get();
	}

}
