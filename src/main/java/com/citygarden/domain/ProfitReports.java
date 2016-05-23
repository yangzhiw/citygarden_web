package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by yzw on 2016/5/23 0023.
 */
@Document(collection = "T_PROFIT_REPORTS")
public class ProfitReports implements Serializable {
    @Id
    private String id;

    @Field("dish_id")
    private String dishId;

    @DBRef(lazy = true)
    private Dish  dish;

    @Field("orginal_price")
    private double orginalPrice;

    @Field("sale_price")
    private double salePrice;

    @Field("sale_count")
    private int saleCount;

    private double saleTotalPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public double getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(double orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public double getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(double saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    @Override
    public String toString() {
        return "ProfitReports{" +
            "id='" + id + '\'' +
            ", dishId='" + dishId + '\'' +
            ", dish=" + dish +
            ", orginalPrice=" + orginalPrice +
            ", salePrice=" + salePrice +
            ", saleCount=" + saleCount +
            ", saleTotalPrice=" + saleTotalPrice +
            '}';
    }
}
