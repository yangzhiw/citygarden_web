package com.citygarden.repository;

import com.citygarden.domain.RePertoryManager;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RepertoryManager entity.
 */
public interface RepertoryManagerRepository extends MongoRepository<RePertoryManager,String> {

    RePertoryManager findByDishIdAndProvideId(String id, String provideId);

    RePertoryManager findByDishId(String id);
}
