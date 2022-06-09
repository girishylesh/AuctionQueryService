package com.eauction.eventhandler;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.events.AuctionUserCreatedEvent;
import com.eauction.events.BidCreatedEvent;
import com.eauction.events.BidUpdatedEvent;
import com.eauction.events.ProductCreatedEvent;
import com.eauction.events.ProductDeletedEvent;
import com.eauction.query.repository.AuctionUserRepository;
import com.eauction.query.repository.BidRepository;
import com.eauction.query.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ProcessingGroup("auctionEventProcessor")
public class AuctionEventHandler {
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	private static final String INFO_COMMON_PLACEHOLDER = "{} : {}";
	
	@EventHandler
	public void auctionUserCreatedEvent(AuctionUserCreatedEvent auctionUserCreatedEvent) {
		AuctionUser auctionUser = new AuctionUser();
		BeanUtils.copyProperties(auctionUserCreatedEvent, auctionUser);
		auctionUserRepository.save(auctionUser);
		log.info(INFO_COMMON_PLACEHOLDER, AuctionUserCreatedEvent.class.getSimpleName(), auctionUserCreatedEvent.getFirstName());
	}
	
	@EventHandler
	public void productCreatedEvent(ProductCreatedEvent productCreatedEvent) {
		Product product = new Product();
		BeanUtils.copyProperties(productCreatedEvent, product);
		productRepository.save(product);
		log.info(INFO_COMMON_PLACEHOLDER, ProductCreatedEvent.class.getSimpleName(), productCreatedEvent.getName());
	}
	
	@EventHandler
	public void productDeletedEvent(ProductDeletedEvent productDeletedEvent) {
		productRepository.deleteById(productDeletedEvent.getUid());
		log.info(INFO_COMMON_PLACEHOLDER, ProductDeletedEvent.class.getSimpleName(), productDeletedEvent.getProductId());
	}
	
	@EventHandler
	public void bidCreatedEvent(BidCreatedEvent bidCreatedEvent) {
		Bid bid = new Bid();
		BeanUtils.copyProperties(bidCreatedEvent, bid);
		bidRepository.save(bid);
		log.info(INFO_COMMON_PLACEHOLDER, BidCreatedEvent.class.getSimpleName(), bidCreatedEvent.getBidAmount());
	}
	
	@EventHandler
	public void bidUpdatedEvent(BidUpdatedEvent bidUpdatedEvent) {
		Optional<Bid> bid = bidRepository.findById(bidUpdatedEvent.getUid());
		if(bid.isPresent()) {
			Bid bidUpdated = bid.get();
			bidUpdated.setBidAmount(bidUpdatedEvent.getBidAmount());
			bidRepository.save(bidUpdated);
			log.info(INFO_COMMON_PLACEHOLDER, BidUpdatedEvent.class.getSimpleName(), bidUpdatedEvent.getBidAmount());
		} else {
			log.error("Bid not found: {}", bidUpdatedEvent.getUid());
		}
	}
}
