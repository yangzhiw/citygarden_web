package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2016/5/20 0020.
 */
@Document(collection = "T_DISH_RELATION_PROVIDE")
public class DishRelationProvide extends AbstractAuditingEntity {
    @Id
    private String id;

    @Field("dish_id")
    private String dishId;

    @Field("provide_merchant_id")
    private String provideMerchantId;

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

    public String getProvideMerchantId() {
        return provideMerchantId;
    }

    public void setProvideMerchantId(String provideMerchantId) {
        this.provideMerchantId = provideMerchantId;
    }


    @Override
    public String toString() {
        return "DishRelationProvide{" +
            "id='" + id + '\'' +
            ", dishId='" + dishId + '\'' +
            ", provideMerchantId='" + provideMerchantId + '\'' +
            '}';
    }
}
