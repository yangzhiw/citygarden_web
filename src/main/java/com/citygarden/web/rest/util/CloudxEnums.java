package com.citygarden.web.rest.util;

/**
 * Created by yzw on 2016/2/24 0024.
 */
public class CloudxEnums {
    private CloudxEnums() {
    }

    public final class DeliveryAddressEnum {

        //默认地址
        public static final String DEFAULTADDRESS = "0";
        //非默认地址
        public static final String UNDEFAULTADDRESS = "1";

        public DeliveryAddressEnum() {
        }
    }

    public final class OrderStatusEnum {

        //未付款
        public static final String UNPAY = "1";
        //付款未发货
        public static final String PAYANDUNDELIVE = "2";
        //发货未确认收货
        public static final String DELIVEANDUNACCEPT = "3";
        //确认收货
        public static final String ACCEPT = "4";

        public OrderStatusEnum() {

        }

    }

    public final  class CheckEnum{
        public static final String ISCHECK = "0";
        public static final String UNCHECK = "1";
    }
}
