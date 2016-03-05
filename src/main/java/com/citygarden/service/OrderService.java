package com.citygarden.service;

import com.citygarden.domain.Order;
import com.citygarden.repository.OrderRepository;
import com.citygarden.web.rest.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private OrderRepository orderRepository;


    public Order save(OrderDTO order) {
        Order order_new = new Order();
        order_new.setTotalPrice(order.getTotalPrice());
        order_new.setDate(order.getDate());
        order_new.setDeliveryAddress(order.getDeliveryAddress());
        order_new.setOrderItemList(order.getOrderItemList());
        order_new.setOrderStatus(order.getOrderStatus());
        order_new.setDeliveryWay(order.getDeliveryWay());
        return  orderRepository.save(order_new);
    }
}
