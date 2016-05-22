package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/2/29 0029.
 */

@Document(collection = "T_CART_TO_ACCOUNT")
public class CartToAccount extends AbstractAuditingEntity{
    @Id
    private String id;

    @Field("total_price")
    private double totalPrice;
    @Field("delivery_way")
    private String deliveryWay;
    @Field("order_status")
    private String orderStatus;

    private String username;

    private String isCheck;

    private List<OrderItem> orderItemList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryWay() {
        return deliveryWay;
    }

    public void setDeliveryWay(String deliveryWay) {
        this.deliveryWay = deliveryWay;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartToAccount order = (CartToAccount) o;
        if(order.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CartToAccount{" +
            "id='" + id + '\'' +
            ", totalPrice=" + totalPrice +
            ", deliveryWay='" + deliveryWay + '\'' +
            ", orderStatus='" + orderStatus + '\'' +
            ", username='" + username + '\'' +
            ", isCheck='" + isCheck + '\'' +
            ", orderItemList=" + orderItemList +
            '}';
    }
}
