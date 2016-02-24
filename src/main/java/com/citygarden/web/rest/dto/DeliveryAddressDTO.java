package com.citygarden.web.rest.dto;

/**
 * Created by Administrator on 2016/2/24 0024.
 */
public class DeliveryAddressDTO {
   private String address;
   private String isDefault;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "DeliveryAddressDTO{" +
            "address='" + address + '\'' +
            ", isDefault='" + isDefault + '\'' +
            '}';
    }
}
