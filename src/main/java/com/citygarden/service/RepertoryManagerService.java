package com.citygarden.service;

import com.citygarden.domain.*;
import com.citygarden.repository.*;
import com.citygarden.security.SecurityUtils;
import com.citygarden.web.rest.dto.DishDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserLevalDefinitionRepository userLevalDefinitionRepository;

    @Inject
    private UserLevelRepository userLevelRepository;

    /**
     * 更新菜名库存
     * @param order
     */
    public void update(Order order) {
        System.err.println(order);
       if(order == null){
           return;
       }else{
           updateUser(order);
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

    private void updateUser(Order order) {
        String username = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userRepository.findOneByLogin(username);
        User user1 = user.get();
        if(user1 != null){
            double totalPrice = user1.getTotalPrice();
            user1.setTotalPrice(totalPrice + order.getTotalPrice());
            userRepository.save(user1);
            updateUserLevel(user1);
        }
    }

    private void updateUserLevel(User user1) {
        double userScore = user1.getTotalPrice();

        int huangj_score = 0;
        int baij_score = 0;
        int zhuans_score = 0;

        String huanj_discount  =  null;
        String baij_discount  =  null;
        String zuans_discount  =  null;

        List<UserLevalDefinition> userLevalDefinitions = userLevalDefinitionRepository.findAll();
        for (UserLevalDefinition userLevalDefinition : userLevalDefinitions) {
            if(userLevalDefinition.getName().equals("黄金会员")) {
                huangj_score = userLevalDefinition.getIntegral();
                huanj_discount = userLevalDefinition.getDiscount();
            } else if(userLevalDefinition.getName().equals("白金会员")) {
                 baij_score = userLevalDefinition.getIntegral();
                 baij_discount = userLevalDefinition.getDiscount();
            }
            if(userLevalDefinition.getName().equals("钻石会员")) {
                zhuans_score = userLevalDefinition.getIntegral();
                zuans_discount = userLevalDefinition.getDiscount();
            }
        }
        String username = SecurityUtils.getCurrentUserLogin();
        UserLevel userLevel = userLevelRepository.findByUserName(username);

        if(userLevel == null){
            UserLevel userLevel1 = new UserLevel();
            if(userScore<huangj_score){
                userLevel1.setDiscount(Integer.valueOf(100));
                userLevel1.setUserName(username);
                userLevel1.setUserLevel("大众会员");
            }

            if(huangj_score <= userScore && userScore< baij_score){
                userLevel1.setDiscount(Integer.valueOf(huanj_discount));
                userLevel1.setUserName(username);
                userLevel1.setUserLevel("黄金会员");
            }

            if(baij_score <= userScore && userScore< zhuans_score){
                userLevel1.setDiscount(Integer.valueOf(baij_discount));
                userLevel1.setUserName(username);
                userLevel1.setUserLevel("白金会员");
            }

            if(zhuans_score <= userScore){
                userLevel1.setDiscount(Integer.valueOf(zhuans_score));
                userLevel1.setUserName(username);
                userLevel1.setUserLevel("钻石会员");
            }
            userLevelRepository.save(userLevel1);
        }else{
            if(userScore<huangj_score){
                userLevel.setDiscount(Integer.valueOf(100));
                userLevel.setUserName(username);
                userLevel.setUserLevel("大众会员");
            }

            if(huangj_score <= userScore && userScore< baij_score){
                userLevel.setDiscount(Integer.valueOf(huanj_discount));
                userLevel.setUserName(username);
                userLevel.setUserLevel("黄金会员");
            }

            if(baij_score <= userScore && userScore< zhuans_score){
                userLevel.setDiscount(Integer.valueOf(baij_discount));
                userLevel.setUserName(username);
                userLevel.setUserLevel("白金会员");
            }

            if(zhuans_score <= userScore){
                userLevel.setDiscount(Integer.valueOf(zhuans_score));
                userLevel.setUserName(username);
                userLevel.setUserLevel("钻石会员");
            }
            userLevelRepository.save(userLevel);
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
