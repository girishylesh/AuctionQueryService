package com.eauction.query.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.query.dto.GetProductQuery;
import com.eauction.query.dto.ProductBids;

@RestController
@RequestMapping(value = "/e-auction/api/v1")
public class AuctionQueryController {
	
	@Autowired
    private QueryGateway queryGateway;
	
	@GetMapping("/seller/show-bids/{productId}")
	public ProductBids getBids(@PathVariable String productId) throws Exception {	
		return queryGateway.query(new GetProductQuery(productId), ResponseTypes.instanceOf(ProductBids.class)).get();
	}
	
}
