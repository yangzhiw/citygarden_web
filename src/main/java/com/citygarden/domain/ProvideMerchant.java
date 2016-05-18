package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzw on 2016/5/17 0017.
 */

@Document(collection = "T_PROVIDE_MERCHANT")
public class ProvideMerchant extends AbstractAuditingEntity {
    @Id
    private String id;

    private String name;

    @Field("chinese_name")
    private String chineseName;

    private String description;

    @DBRef(lazy = true)
    private List<Dish> dishs = new ArrayList<>();

    @DBRef(lazy = true)
    @Field("provide_dishs")
    private List<ProvideDish> provideDishs = new ArrayList<>(0);

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

    public List<ProvideDish> getProvideDishs() {
        return provideDishs;
    }

    public void setProvideDishs(List<ProvideDish> provideDishs) {
        this.provideDishs = provideDishs;
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

    public List<Dish> getDishs() {
        return dishs;
    }

    public void setDishs(List<Dish> dishs) {
        this.dishs = dishs;
    }

    @Override
    public String toString() {
        return "ProvideMerchant{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", chineseName='" + chineseName + '\'' +
            ", description='" + description + '\'' +
            ", dishs=" + dishs +
            ", provideDishs=" + provideDishs +
            '}';
    }
}
