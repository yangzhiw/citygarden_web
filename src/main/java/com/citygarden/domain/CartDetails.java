package com.citygarden.domain;

import com.citygarden.web.rest.dto.DishDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CartDetails.
 */

@Document(collection = "T_CART_DETAILS")
public class CartDetails implements Serializable {

    @Id
    private String id;

    private double subtotal;
    private int count;
    private DishDTO dish;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public DishDTO getDish() {
        return dish;
    }

    public void setDish(DishDTO dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartDetails cartDetails = (CartDetails) o;
        if(cartDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cartDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public String toString() {
        return "CartDetails{" +
            "id='" + id + '\'' +
            ", subtotal='" + subtotal + '\'' +
            ", count=" + count +
            ", dish=" + dish +
            '}';
    }
}
