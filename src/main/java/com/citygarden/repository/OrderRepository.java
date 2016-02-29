package com.citygarden.repository;

import com.citygarden.domain.Order;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
public interface OrderRepository extends MongoRepository<Order,String> {


    Order findByOrderStatus(String id);
}
