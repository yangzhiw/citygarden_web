package com.citygarden.repository;

import com.citygarden.domain.Cart;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cart entity.
 */
public interface CartRepository extends MongoRepository<Cart,String> {

    Cart findByUsername(String username);
}
