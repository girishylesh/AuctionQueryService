package com.eauction.eventhandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.query.dto.GetAuctionUserQuery;
import com.eauction.query.dto.GetProductQuery;
import com.eauction.query.dto.PlacedBid;
import com.eauction.query.dto.ProductBids;
import com.eauction.query.repository.AuctionUserRepository;
import com.eauction.query.repository.BidRepository;

@Component
public class AuctionQueryHandler {
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;

	@Autowired
	private BidRepository bidRepository;
	
	@QueryHandler
	public ProductBids getBids(GetProductQuery getProductQuery) {
		List<Bid> bids = bidRepository.findByProductUid(getProductQuery.getProductId()).orElse(Collections.emptyList());
		if(!bids.isEmpty()) {
			Product product = bids.stream().findFirst().map(bid -> bid.getProduct()).get();
			List<PlacedBid> placedBids = new ArrayList<>();
			bids.stream().sorted(Comparator.comparingDouble(Bid::getBidAmount).reversed())
			.forEach(bid -> {
				PlacedBid placedBid = PlacedBid.builder()
						.uid(bid.getUid())
						.bidAmount(bid.getBidAmount())
						.buyerEmail(bid.getAuctionUser().getEmail())
						.buyerFirstName(bid.getAuctionUser().getFirstName())
						.buyerLastName(bid.getAuctionUser().getLastName())
						.build();
				placedBids.add(placedBid);	
			});
			return ProductBids.builder().bids(placedBids).product(product).build();
		}
		
		return ProductBids.builder().build();
	}
	
	@QueryHandler
	public AuctionUser getAuctionUser(GetAuctionUserQuery getAuctionUserQuery) {
		return auctionUserRepository.findByEmail(getAuctionUserQuery.getEmail()).orElseGet(AuctionUser::new);
	}
}
