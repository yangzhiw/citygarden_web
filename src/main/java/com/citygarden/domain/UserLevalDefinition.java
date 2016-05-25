package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yzw on 2016/5/16 0016.
 */
@Document(collection = "T_USER_LEVEL_DEFINITION")
public class UserLevalDefinition extends AbstractAuditingEntity {

    @Id
    private String id;

    private String name;
    private int integral;
    private String discount;


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

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserLevalDefinition{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", integral=" + integral +
            ", discount='" + discount + '\'' +
            '}';
    }
}
