package com.citygarden.repository;

import com.citygarden.domain.DishRelationProvide;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by yzw on 2016/5/20 0020.
 */
public interface DishRelationProvideRepository extends MongoRepository<DishRelationProvide,String> {
    DishRelationProvide findByDishId(String dishId);
}
