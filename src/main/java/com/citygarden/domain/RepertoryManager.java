package com.citygarden.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RepertoryManager.
 */

@Document(collection = "repertory_manager")
public class RepertoryManager implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;
    
    @Field("now_count")
    private Integer nowCount;
    
    @Field("total_count")
    private Long totalCount;
    
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

    public Integer getNowCount() {
        return nowCount;
    }
    
    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepertoryManager repertoryManager = (RepertoryManager) o;
        if(repertoryManager.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, repertoryManager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RepertoryManager{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", nowCount='" + nowCount + "'" +
            ", totalCount='" + totalCount + "'" +
            '}';
    }
}
