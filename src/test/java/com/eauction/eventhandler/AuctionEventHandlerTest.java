package com.eauction.eventhandler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

@ActiveProfiles("test")
@SpringBootTest
class AuctionEventHandlerTest {

	@Mock
	private AuctionUserRepository auctionUserRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private AuctionEventHandler auctionEventHandler;

	@Test
	void auctionUserCreatedEventTest() {
		AuctionUserCreatedEvent auctionUserCreatedEvent = new AuctionUserCreatedEvent();
		when(auctionUserRepository.save(any(AuctionUser.class))).thenAnswer(i -> i.getArguments()[0]);
		auctionEventHandler.auctionUserCreatedEvent(auctionUserCreatedEvent);
		verify(auctionUserRepository).save(any(AuctionUser.class));
	}

	@Test
	void productCreatedEventTest() {
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);
		auctionEventHandler.productCreatedEvent(productCreatedEvent);
		verify(productRepository).save(any(Product.class));
	}

	@Test
	void productDeletedEvent() {
		ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
		productDeletedEvent.setUid("u123");
		doNothing().when(productRepository).deleteById(anyString());
		auctionEventHandler.productDeletedEvent(productDeletedEvent);
		verify(productRepository).deleteById(anyString());
	}
	
	@Test
	void bidCreatedEventTest() {
		BidCreatedEvent bidCreatedEvent = new BidCreatedEvent();
		when(bidRepository.save(any(Bid.class))).thenAnswer(i -> i.getArguments()[0]);
		auctionEventHandler.bidCreatedEvent(bidCreatedEvent);
		verify(bidRepository).save(any(Bid.class));
	}
	
	@Test
	void bidUpdatedEventTest() {
		BidUpdatedEvent bidUpdatedEvent = new BidUpdatedEvent();
		bidUpdatedEvent.setUid("u111");
		when(bidRepository.findById(anyString())).thenReturn(Optional.of(new Bid()));
		when(bidRepository.save(any(Bid.class))).thenAnswer(i -> i.getArguments()[0]);
		auctionEventHandler.bidUpdatedEvent(bidUpdatedEvent);
		verify(bidRepository).save(any(Bid.class));	
	}
}
