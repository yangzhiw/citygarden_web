package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Order.
 */

@Document(collection = "order")
public class Order implements Serializable {

    @Id
    private String id;

    @Field("price_count")
    private Double priceCount;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPriceCount() {
        return priceCount;
    }
    
    public void setPriceCount(Double priceCount) {
        this.priceCount = priceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
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
        return "Order{" +
            "id=" + id +
            ", priceCount='" + priceCount + "'" +
            '}';
    }
}
