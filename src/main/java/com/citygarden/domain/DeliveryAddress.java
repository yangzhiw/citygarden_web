package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DeliveryAddress.
 */

@Document(collection = "T_DELIVERY_ADDRESS")
public class DeliveryAddress extends AbstractAuditingEntity {

    @Id
    private String id;

    private String address;
    @Field("is_default")
    private String isDefault;

    private String  username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeliveryAddress deliveryAddress = (DeliveryAddress) o;
        if(deliveryAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, deliveryAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
            "id='" + id + '\'' +
            ", address='" + address + '\'' +
            ", isDefault='" + isDefault + '\'' +
            ", username=" + username  +
            '}';
    }
}
