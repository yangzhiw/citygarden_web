package com.citygarden.repository;

import com.citygarden.domain.RepertoryManager;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RepertoryManager entity.
 */
public interface RepertoryManagerRepository extends MongoRepository<RepertoryManager,String> {

}
