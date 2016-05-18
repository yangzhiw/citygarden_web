package com.citygarden.repository;

import com.citygarden.domain.ProvideMerchant;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ProvideMerchant entity.
 */
public interface ProvideMerchantRepository extends MongoRepository<ProvideMerchant,String> {

}
