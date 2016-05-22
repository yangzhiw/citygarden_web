package com.citygarden.service;

import com.citygarden.domain.Dish;
import com.citygarden.domain.Order;
import com.citygarden.domain.OrderItem;
import com.citygarden.domain.RePertoryManager;
import com.citygarden.repository.DishRelationProvideRepository;
import com.citygarden.repository.DishRepository;
import com.citygarden.repository.ProvideMerchantRepository;
import com.citygarden.repository.RepertoryManagerRepository;
import com.citygarden.web.rest.dto.DishDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Administrator on 2016/5/22 0022.
 */

@Service
public class RepertoryManagerService {

    @Inject
    private DishRepository dishRepository;

    @Inject
    private DishRelationProvideRepository dishRelationProvideRepository;

    @Inject
    private ProvideMerchantRepository provideMerchantRepository;

    @Inject
    private RepertoryManagerRepository repertoryManagerRepository;

    /**
     * 更新菜名库存
     * @param order
     */
    public void update(Order order) {
        System.err.println(order);
       if(order == null){
           return;
       }else{
           List<OrderItem> orderItems = order.getOrderItemList();
           for(OrderItem orderItem : orderItems){
               int count = orderItem.getCount();
               DishDTO dishDTO = orderItem.getDish();
               Dish dish = dishRepository.findOne(dishDTO.getId());
               if(dish.getId() != null){
                   String provideId = dishRelationProvideRepository.findByDishId(dish.getId()).getProvideMerchantId();
                   if(provideId != null){
                       System.out.println(dish.getId());
                       RePertoryManager repertoryManager = repertoryManagerRepository.findByDishId(dish.getId());
                       repertoryManager.setTotalSaleCount(repertoryManager.getTotalSaleCount() + orderItem.getCount());
                       repertoryManager.setNowCount(repertoryManager.getNowCount() - orderItem.getCount());
                       repertoryManagerRepository.save(repertoryManager);
                   }
               }
           }
       }
    }
}
