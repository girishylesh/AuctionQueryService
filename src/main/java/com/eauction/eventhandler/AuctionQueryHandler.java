package com.eauction.eventhandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.enums.UserType;
import com.eauction.query.dto.GetAuctionUserQuery;
import com.eauction.query.dto.GetProductBidsQuery;
import com.eauction.query.dto.GetProductQuery;
import com.eauction.query.dto.PlacedBid;
import com.eauction.query.repository.AuctionUserRepository;
import com.eauction.query.repository.BidRepository;
import com.eauction.query.repository.ProductRepository;

@Component
public class AuctionQueryHandler {
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;

	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@QueryHandler
	public List<PlacedBid> getBids(GetProductBidsQuery getProductBidsQuery) {
		List<Bid> bids = new ArrayList<>();
		AuctionUser user = auctionUserRepository.findById(getProductBidsQuery.getUserUid()).orElseGet(AuctionUser::new);
		if(UserType.SELLER.equals(user.getUserType())) {
			List<Product> products = productRepository.findByAuctionUserUidAndBidEndDateGreaterThanEqual(user.getUid(), LocalDate.now())
					.orElse(Collections.emptyList());
			List<String> productUids = products.stream().map(prd-> prd.getUid()).collect(Collectors.toList());
			bids = bidRepository.findByProductIn(productUids).orElse(Collections.emptyList());
		} else if(UserType.BUYER.equals(user.getUserType())) {
			bids = bidRepository.findByAuctionUserUid(user.getUid()).orElse(Collections.emptyList());
		}
		if(!bids.isEmpty()) {
			List<PlacedBid> placedBids = new ArrayList<>();
			bids.stream().sorted(Comparator.comparingDouble(Bid::getBidAmount).reversed())
			.forEach(bid -> {
				PlacedBid placedBid = PlacedBid.builder()
						.uid(bid.getUid())
						.bidAmount(bid.getBidAmount())
						.buyerEmail(bid.getAuctionUser().getEmail())
						.buyerFirstName(bid.getAuctionUser().getFirstName())
						.buyerLastName(bid.getAuctionUser().getLastName())
						.product(bid.getProduct())
						.build();
				placedBids.add(placedBid);	
			});
			return placedBids;
		}
		
		return Collections.emptyList();
	}
	
	@QueryHandler
	public AuctionUser getAuctionUser(GetAuctionUserQuery getAuctionUserQuery) {
		return auctionUserRepository.findByEmail(getAuctionUserQuery.getEmail()).orElseGet(AuctionUser::new);
	}
	
	@QueryHandler
	public List<Product> getProducts(GetProductQuery getProductQuery) {
		AuctionUser user = auctionUserRepository.findById(getProductQuery.getUserUid()).orElseGet(AuctionUser::new);
		if(UserType.SELLER.equals(user.getUserType())) {
			return productRepository.findByAuctionUserUidAndBidEndDateGreaterThanEqual(getProductQuery.getUserUid(), LocalDate.now()).orElse(Collections.emptyList());
		} else if(UserType.BUYER.equals(user.getUserType())) {
			return productRepository.findByBidEndDateGreaterThanEqual(LocalDate.now()).orElse(Collections.emptyList());
		}
		return Collections.emptyList();
	}
}
