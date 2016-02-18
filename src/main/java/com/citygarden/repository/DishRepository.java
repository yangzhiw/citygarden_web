package com.citygarden.repository;

import com.citygarden.domain.Dish;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Dish entity.
 */
public interface DishRepository extends MongoRepository<Dish,String> {

}
