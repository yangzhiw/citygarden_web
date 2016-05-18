package com.citygarden.web.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A DTO for the ProvideMerchant entity.
 */
public class ProvideMerchantDTO implements Serializable {
    private String id;

    private String name;

    private String chineseName;

    private String description;

    private List<DishDTO> dishs = new ArrayList<>();

    private String providePhoto;

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

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DishDTO> getDishs() {
        return dishs;
    }

    public void setDishs(List<DishDTO> dishs) {
        this.dishs = dishs;
    }

      public String getProvidePhoto() {
        return providePhoto;
    }

    public void setProvidePhoto(String providePhoto) {
        this.providePhoto = providePhoto;
    }

    @Override
    public String toString() {
        return "provideMerchantDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", chineseName='" + chineseName + '\'' +
            ", description='" + description + '\'' +
            ", dishs=" + dishs +
            ", providePhoto='" + providePhoto + '\'' +
            '}';
    }
}
