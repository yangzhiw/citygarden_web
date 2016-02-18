package com.citygarden.repository;

import com.citygarden.domain.GroupPurchase;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the GroupPurchase entity.
 */
public interface GroupPurchaseRepository extends MongoRepository<GroupPurchase,String> {

}
