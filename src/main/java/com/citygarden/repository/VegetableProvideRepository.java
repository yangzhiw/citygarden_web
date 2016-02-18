package com.citygarden.repository;

import com.citygarden.domain.VegetableProvide;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VegetableProvide entity.
 */
public interface VegetableProvideRepository extends MongoRepository<VegetableProvide,String> {

}
