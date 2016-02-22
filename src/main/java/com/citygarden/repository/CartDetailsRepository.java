package com.citygarden.repository;

import com.citygarden.domain.CartDetails;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the CartDetails entity.
 */
public interface CartDetailsRepository extends MongoRepository<CartDetails,String> {

}
