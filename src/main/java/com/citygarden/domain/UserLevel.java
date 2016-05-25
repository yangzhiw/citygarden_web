package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yzw on 2016/5/25 0025.
 */

@Document(collection = "T_USER_LEVEL")
public class UserLevel extends  AbstractAuditingEntity{
    @Id
    private String id;

    private String userName;

    private String userLevel;

    private int discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "UserLevel{" +
            "id='" + id + '\'' +
            ", userName='" + userName + '\'' +
            ", userLevel='" + userLevel + '\'' +
            ", discount=" + discount +
            '}';
    }
}
