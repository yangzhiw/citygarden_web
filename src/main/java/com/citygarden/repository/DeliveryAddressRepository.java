package com.citygarden.repository;

import com.citygarden.domain.DeliveryAddress;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DeliveryAddress entity.
 */
public interface DeliveryAddressRepository extends MongoRepository<DeliveryAddress,String> {

}
