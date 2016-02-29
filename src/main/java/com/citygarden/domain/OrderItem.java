package com.citygarden.domain;

import com.citygarden.web.rest.dto.DishDTO;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A OrderItem.
 */

@Document(collection = "T_ORDER_ITEM")
public class OrderItem implements Serializable {

    @Id
    private String id;

    private DateTime date;
    private double subtotal;
    private int count;
    private DishDTO dish;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDish(DishDTO dish) {
        this.dish = dish;
    }

    public DishDTO getDish() {
        return dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        if(orderItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id='" + id + '\'' +
            ", date=" + date +
            ", subtotal=" + subtotal +
            ", count=" + count +
            ", dish=" + dish +
            '}';
    }
}
