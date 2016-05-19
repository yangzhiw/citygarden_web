package com.citygarden.repository;

import com.citygarden.domain.Order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
public interface OrderRepository extends MongoRepository<Order,String> {


    Order findByOrderStatus(String id);

    List<Order> findByUsernameAndOrderStatus(String username, String unpay);

    List<Order> findByUsername(String username);

}
