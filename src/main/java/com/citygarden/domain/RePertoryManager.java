package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RepertoryManager.
 */

@Document(collection = "T_REPERTORY_MANAGER")
public class RePertoryManager extends AbstractAuditingEntity {

    @Id
    private String id;

    @Field("dish_name")
    private String dishName;

    @Field("provide_name")
    private String provideName;

    @Field("dish_id")
    private String dishId;

    @Field("provide_id")
    private String provideId;

    @Field("now_count")
    private int nowCount;

    @Field("total_sale_count")
    private Long totalSaleCount;

    @DBRef(lazy = true)
    private Dish dish;

    @DBRef(lazy = true)
    @Field("provide_merchant")
    private ProvideMerchant provideMerchant;

    @Field("orginal_price")
    private String orginalPrice;

    @Field("sale_price")
    private String salePrice;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getProvideName() {
        return provideName;
    }

    public void setProvideName(String provideName) {
        this.provideName = provideName;
    }

    public int getNowCount() {
        return nowCount;
    }

    public void setNowCount(int nowCount) {
        this.nowCount = nowCount;
    }

    public Long getTotalSaleCount() {
        return totalSaleCount;
    }

    public void setTotalSaleCount(Long totalSaleCount) {
        this.totalSaleCount = totalSaleCount;
    }

    public ProvideMerchant getProvideMerchant() {
        return provideMerchant;
    }

    public void setProvideMerchant(ProvideMerchant provideMerchant) {
        this.provideMerchant = provideMerchant;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(String orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getProvideId() {
        return provideId;
    }

    public void setProvideId(String provideId) {
        this.provideId = provideId;
    }

    @Override
    public String toString() {
        return "RePertoryManager{" +
            "id='" + id + '\'' +
            ", dishName='" + dishName + '\'' +
            ", provideName='" + provideName + '\'' +
            ", dishId='" + dishId + '\'' +
            ", provideId='" + provideId + '\'' +
            ", nowCount=" + nowCount +
            ", totalSaleCount=" + totalSaleCount +
            ", dish=" + dish +
            ", provideMerchant=" + provideMerchant +
            ", orginalPrice='" + orginalPrice + '\'' +
            ", salePrice='" + salePrice + '\'' +
            '}';
    }
}
