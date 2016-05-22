package com.citygarden.service;

import com.alibaba.fastjson.JSON;
import com.citygarden.domain.Cart;
import com.citygarden.domain.CartDetails;
import com.citygarden.domain.OrderItem;
import com.citygarden.domain.util.DomainCodeGenerator;
import com.citygarden.repository.CartRepository;
import com.citygarden.security.SecurityUtils;
import com.citygarden.web.rest.dto.OrderDTO;
import com.citygarden.web.rest.util.RoundDoubleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
@Service
public class CartService {
    private final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Inject
    private CartRepository cartRepository;

    public Cart addToCart(CartDetails cartDetails) {
        cartDetails.setId(DomainCodeGenerator.generateCartDetailId());
        String username = SecurityUtils.getCurrentUserLogin();
        Cart cart = cartRepository.findByUsername(username);
        double totalCount = 0;
        if(cart == null){
           cart = new Cart();
           cart.getCartDetailsList().add(cartDetails);
           cart.setUsername(username);
           totalCount = cartDetails.getSubtotal();
           totalCount = RoundDoubleUtil.round(totalCount, 2);
           cart.setTotal(totalCount);

           return  cartRepository.save(cart);
        }else{
            cart.getCartDetailsList().add(cartDetails);
            List<CartDetails> cartDetailses =  new ArrayList<>();
            cartDetailses = cart.getCartDetailsList();
            for(CartDetails cartDetails1 : cartDetailses){
                totalCount = totalCount + cartDetails1.getSubtotal();
            }
            totalCount = RoundDoubleUtil.round(totalCount, 2);
            cart.setTotal(totalCount);

            return  cartRepository.save(cart);
        }
    }

    //删除后某一购物项后，更新购物车
    public Cart updateCart(Cart cart) {
        System.err.println(cart.getCartDetailsList());
        double totalCount = 0;
        String username = SecurityUtils.getCurrentUserLogin();
        Cart cart_exist = cartRepository.findByUsername(username);
        if(cart.getCartDetailsList().size() == 0){
            cartRepository.delete(cart_exist);
            return null;
        }else{
            List<CartDetails> cartDetailses =  new ArrayList<>();
            cartDetailses = cart.getCartDetailsList();
            for(CartDetails cartDetails1 : cartDetailses){
                totalCount = totalCount + cartDetails1.getSubtotal();
            }
            totalCount = RoundDoubleUtil.round(totalCount, 2);
            cart.setTotal(totalCount);

            return  cartRepository.save(cart);
        }
    }

    /**
     * 提交订单之后 删除购物车中的某项
     * @param order
     */
    public void updateCartFromOrder(OrderDTO order) {
        System.err.println("cart:"+order);
        String username = SecurityUtils.getCurrentUserLogin();
        Cart cart = cartRepository.findByUsername(username);
        double totalTotal = cart.getTotal();
        if (order != null) {
            List<OrderItem> orderItems = order.getOrderItemList();
            for (OrderItem orderItem : orderItems) {

                 CartDetails cartDetails = new CartDetails();
                cartDetails.setId(orderItem.getId());
                cartDetails.setCount(orderItem.getCount());
                cartDetails.setSubtotal(orderItem.getSubtotal());
                cartDetails.setDish(orderItem.getDish());

                System.out.println("cartDetails:" + cartDetails);

                List<CartDetails> cartDetailses =  new ArrayList<>();
                cartDetailses = cart.getCartDetailsList();

                CartDetails re_cartDetail = null;
                for(CartDetails cartDetails1 : cartDetailses){
                   if(!cartDetails1.getId().equals(cartDetails.getId())){
                       re_cartDetail = cartDetails1;
                       break;
                   }
                    cart.getCartDetailsList().remove(re_cartDetail);
                    totalTotal = totalTotal - re_cartDetail.getSubtotal();
                    totalTotal = RoundDoubleUtil.round(totalTotal, 2);
                    cart.setTotal(totalTotal);

                    cartRepository.save(cart);
                }
            }
        }
    }
}
