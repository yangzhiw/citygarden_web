package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A VegetableProvide.
 */

@Document(collection = "vegetable_provide")
public class VegetableProvide implements Serializable {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VegetableProvide vegetableProvide = (VegetableProvide) o;
        if(vegetableProvide.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vegetableProvide.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VegetableProvide{" +
            "id=" + id +
            '}';
    }
}
