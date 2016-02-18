package com.citygarden.repository;

import com.citygarden.domain.OrderItem;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the OrderItem entity.
 */
public interface OrderItemRepository extends MongoRepository<OrderItem,String> {

}
