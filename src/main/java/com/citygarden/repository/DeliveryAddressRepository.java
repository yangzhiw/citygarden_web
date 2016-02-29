package com.citygarden.repository;

import com.citygarden.domain.DeliveryAddress;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the DeliveryAddress entity.
 */
public interface DeliveryAddressRepository extends MongoRepository<DeliveryAddress,String> {

    DeliveryAddress findByIsDefaultAndUsername(String isDefault, String username);

    List<DeliveryAddress> findByUsername(String username);
}
