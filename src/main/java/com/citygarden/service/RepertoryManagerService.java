package com.citygarden.service;

import com.citygarden.domain.*;
import com.citygarden.repository.*;
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

    @Inject
    private ProfitReportsRepository profitReportsRepository;

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
               //保存数据到利润表中
               saveProfit(orderItem);

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

    private void saveProfit(OrderItem orderItem) {
        int count = orderItem.getCount();
        DishDTO dishDTO = orderItem.getDish();
        Dish dish = dishRepository.findOne(dishDTO.getId());

        ProfitReports profitReports = profitReportsRepository.findByDishId(dish.getId());

        if (profitReports == null) {
            profitReports = new ProfitReports();
            profitReports.setDish(dish);
            profitReports.setDishId(dish.getId());
            profitReports.setSalePrice(Double.valueOf(dish.getDiscountPrice()));
            profitReports.setSaleCount(orderItem.getCount());
            profitReports.setSaleTotalPrice(orderItem.getSubtotal());

            String provideId = dishRelationProvideRepository.findByDishId(dish.getId()).getProvideMerchantId();
            if(provideId != null){
                RePertoryManager repertoryManager = repertoryManagerRepository.findByDishId(dish.getId());
                profitReports.setOrginalPrice(Double.valueOf(repertoryManager.getOrginalPrice()));
            }

            profitReportsRepository.save(profitReports);
        }else{
            profitReports.setSaleTotalPrice(profitReports.getSalePrice() + orderItem.getSubtotal());
            profitReports.setSaleCount(profitReports.getSaleCount() + orderItem.getCount());

            profitReportsRepository.save(profitReports);
        }
    }
}
