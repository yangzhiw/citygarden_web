package com.citygarden.service;

import com.citygarden.domain.Dish;
import com.citygarden.repository.DishRepository;
import com.citygarden.web.rest.dto.DishDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/24 0024.
 */

@Service
public class DishService {

    private final Logger log = LoggerFactory.getLogger(DishService.class);

    @Inject
    private DishRepository dishRepository;

    @Inject
    private DishPhotoUtilService dishPhotoUtilService;

    @Inject
    private MongoTemplate mongoTemplate;

    public List<DishDTO> findAll() throws  Exception{
        List<Dish> dishs = dishRepository.findAll();
        List<DishDTO> dishDTOs = new ArrayList<>();
        DishDTO dishDTO;
        for(Dish dish : dishs){
            dishDTO = new DishDTO();

            dishDTO.setId(dish.getId());
            dishDTO.setName(dish.getName());
            dishDTO.setDiscountPrice(dish.getDiscountPrice());
            dishDTO.setIsDiscount(dish.getIsDiscount());
            dishDTO.setIsGain(dish.getIsGain());
            dishDTO.setIsHot(dish.getIsHot());
            dishDTO.setOriginalPrice(dish.getOriginalPrice());
            dishDTO.setChineseName(dish.getChineseName());
            dishDTO.setDishPhoto(dishPhotoUtilService.getDishPhoto(dish.getName()));
            System.err.println(dishDTO);

            dishDTOs.add(dishDTO);
        }
        return dishDTOs;
    }

    public DishDTO findOne(String id)  throws Exception{
        Dish dish =  dishRepository.findOne(id);
        DishDTO dishDTO = new DishDTO();

        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDiscountPrice(dish.getDiscountPrice());
        dishDTO.setIsDiscount(dish.getIsDiscount());
        dishDTO.setIsGain(dish.getIsGain());
        dishDTO.setIsHot(dish.getIsHot());
        dishDTO.setOriginalPrice(dish.getOriginalPrice());
        dishDTO.setChineseName(dish.getChineseName());

        dishDTO.setDishPhoto(dishPhotoUtilService.getDishPhoto(dish.getName()));

        return dishDTO;

    }

    public List<DishDTO> findByName(String name){
        Dish dish = new Dish();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and(name).regex(dish.getChineseName());
        query.addCriteria(criteria);
        List<Dish> dishs = mongoTemplate.find(query, Dish.class);

        List<DishDTO> dishDTOs = new ArrayList<>(0);
        dishs.forEach(x->{
            DishDTO y = new DishDTO();
            y.setId(x.getId());
            y.setDescription(x.getDescription());
            y.setIsGain(x.getIsGain());
            y.setChineseName(x.getChineseName());
            y.setDiscountPrice(x.getDiscountPrice());
            try {
                y.setDishPhoto(dishPhotoUtilService.getDishPhoto(x.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            y.setIsHot(x.getIsHot());
            y.setName(x.getName());
            y.setOriginalPrice(x.getOriginalPrice());
            y.setIsDiscount(x.getIsDiscount());

            dishDTOs.add(y);
        });
        return dishDTOs;
    }
}
