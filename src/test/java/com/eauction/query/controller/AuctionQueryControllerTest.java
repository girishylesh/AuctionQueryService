package com.eauction.query.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.enums.Category;
import com.eauction.enums.UserType;
import com.eauction.query.dto.PlacedBid;
import com.eauction.query.repository.AuctionUserRepository;
import com.eauction.query.repository.BidRepository;
import com.eauction.query.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
@AutoConfigureMockMvc
class AuctionQueryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	AuctionUserRepository auctionUserRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	BidRepository bidRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void init() {
		AuctionUser seller = new AuctionUser();
		seller.setUid("uid100");
		seller.setFirstName("sname1");
		seller.setLastName("slname1");
		seller.setAddress("saddress1");
		seller.setCity("scity1");
		seller.setState("sstate1");
		seller.setPin(111111L);
		seller.setPhone(1111111111L);
		seller.setEmail("seller@email.com");
		seller.setUserType(UserType.SELLER);
		auctionUserRepository.save(seller);
		
		AuctionUser buyer = new AuctionUser();
		buyer.setUid("uid101");
		buyer.setFirstName("bname1");
		buyer.setLastName("blname1");
		buyer.setAddress("baddress1");
		buyer.setCity("bcity1");
		buyer.setState("bstate1");
		buyer.setPin(111111L);
		buyer.setPhone(1111111111L);
		buyer.setEmail("buyer@email.com");
		buyer.setUserType(UserType.BUYER);
		auctionUserRepository.save(buyer);
		
		Product product = new Product();
		product.setUid("uid102");
		product.setAuctionUser(seller);
		product.setName("product1");
		product.setShortDesc("short desc");
		product.setDetailedDesc("detailed desc");
		product.setStartingPrice(100.0);
		product.setCategory(Category.SCULPTOR);
		product.setBidEndDate(LocalDate.now().plusWeeks(1));
		productRepository.save(product);
		
		Bid bid = new Bid();
		bid.setUid("uid103");
		bid.setBidAmount(110.0);
		bid.setAuctionUser(buyer);
		bid.setProduct(product);
		bidRepository.save(bid);
	}
	
	@Test
	void getBidTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/e-auction/api/v1/query/show-bids/uid101")
				.contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		CollectionType typeReference =
			    TypeFactory.defaultInstance().constructCollectionType(List.class, PlacedBid.class);
		List<PlacedBid> bids = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
		assertEquals("product1", bids.get(0).getProduct().getName());
		assertEquals(110.0, bids.get(0).getBidAmount());
	}
	
	@Test
	void getBidTestNoBids() throws Exception {
		MvcResult result = mockMvc.perform(get("/e-auction/api/v1/query/show-bids/uid1xx")
				.contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		CollectionType typeReference =
			    TypeFactory.defaultInstance().constructCollectionType(List.class, PlacedBid.class);
		List<PlacedBid> bids = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
		assertEquals(0, bids.size());
	}
	
	@Test
	void getProductsTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/e-auction/api/v1/query/products/uid100")
				.contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		CollectionType typeReference =
			    TypeFactory.defaultInstance().constructCollectionType(List.class, Product.class);
		List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
		assertEquals("product1", products.get(0).getName());
		assertEquals(100.0, products.get(0).getStartingPrice());
	}
	
	@Test
	void getProductsTestNoData() throws Exception {
		MvcResult result = mockMvc.perform(get("/e-auction/api/v1/query/products/uid1xx")
				.contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		CollectionType typeReference =
			    TypeFactory.defaultInstance().constructCollectionType(List.class, Product.class);
		List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);
		assertEquals(0, products.size());
	}
	
	@Test
	void getUserTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/e-auction/api/v1/query/user/seller@email.com")
				.contentType("application/json"))
	            .andExpect(status().isOk())
	            .andReturn();
		AuctionUser user = objectMapper.readValue(result.getResponse().getContentAsString(), AuctionUser.class);
		assertEquals("sname1", user.getFirstName());
	}
	
	@Test
	void getUserNotFoundTest() throws Exception {
		mockMvc.perform(get("/e-auction/api/v1/query/user/xyz@email.com")
				.contentType("application/json"))
	            .andExpect(status().isNotFound())
	            .andReturn();
	}
	
}
