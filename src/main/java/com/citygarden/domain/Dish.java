package com.citygarden.domain;

import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Dish.
 */

@Document(collection = "T_DISH")
public class Dish implements Serializable {

    @Id
    private String id;

    private String name;

    @Field("original_price")
    private String originalPrice;

    @Field("discount_price")
    private String discountPrice;

    @Field("is_discount")
    private String isDiscount;

    @Field("is_gain")
    private String isGain;

    @Field("is_hot")
    private String isHot;

    @Field("chinese_name")
    private String chineseName;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getIsGain() {
        return isGain;
    }

    public void setIsGain(String isGain) {
        this.isGain = isGain;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dish dish = (Dish) o;
        if(dish.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dish{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", originalPrice='" + originalPrice + '\'' +
            ", discountPrice='" + discountPrice + '\'' +
            ", isDiscount='" + isDiscount + '\'' +
            ", isGain='" + isGain + '\'' +
            ", isHot='" + isHot + '\'' +
            ", chineseName='" + chineseName + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}
