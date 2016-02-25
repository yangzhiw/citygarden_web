package com.citygarden.web.rest.dto;


/**
 * Created by Administrator on 2016/2/24 0024.
 */
public class DishDTO {

    private String id;
    private String name;
    private String originalPrice;
    private String discountPrice;
    private String isDiscount;
    private String isGain;
    private String isHot;
    private String dishPhoto;
    private String chineseName;

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

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
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

    public String getDishPhoto() {
        return dishPhoto;
    }

    public void setDishPhoto(String dishPhoto) {
        this.dishPhoto = dishPhoto;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Override
    public String toString() {
        return "DishDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", originalPrice='" + originalPrice + '\'' +
            ", discountPrice='" + discountPrice + '\'' +
            ", isDiscount='" + isDiscount + '\'' +
            ", isGain='" + isGain + '\'' +
            ", isHot='" + isHot + '\'' +
            ", dishPhoto='" + dishPhoto + '\'' +
            ", chineseName='" + chineseName + '\'' +
            '}';
    }
}
