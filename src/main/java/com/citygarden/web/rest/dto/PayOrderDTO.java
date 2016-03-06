package com.citygarden.web.rest.dto;

/**
 * Created by Administrator on 2016/3/5 0005.
 */
public class PayOrderDTO {
    private String id;
    private String bankCode;
    private String total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PayOrderDTO{" +
            "id='" + id + '\'' +
            ", bankCode='" + bankCode + '\'' +
            ", total='" + total + '\'' +
            '}';
    }
}
