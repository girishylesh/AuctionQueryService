package com.eauction.query.dto;

import java.util.List;

import com.eauction.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductBids {
    private List<PlacedBid> bids;
    private Product product;
}
