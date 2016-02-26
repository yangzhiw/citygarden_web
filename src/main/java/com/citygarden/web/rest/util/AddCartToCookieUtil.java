package com.citygarden.web.rest.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.citygarden.domain.Cart;
import com.citygarden.domain.CartDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class AddCartToCookieUtil {

    private final static Logger log = LoggerFactory.getLogger(AddCartToCookieUtil.class);
    public static void addCartDetailsToCart(
        @CookieValue(value = "cartCookie", required = false) String cartCookieStr,
        @RequestBody CartDetails cartDetails,
        HttpServletResponse response, Cart cart) throws Exception {
        System.err.println(cartDetails);
        log.debug("REST request to save CartDetails : {}", cartDetails);
        if (cartCookieStr == null) {
            cart.getCartDetailsList().add(cartDetails);
        } else {
            JSONObject jsonCart = JSONObject.parseObject(cartCookieStr);
            cart = JSON.toJavaObject(jsonCart, Cart.class);
            cart.getCartDetailsList().add(cartDetails);
        }
        String cartCookie = JSON.toJSONString(cart);//Cart转换成对象Json
        Cookie cookie = new Cookie("cartCookie", cartCookie);
        System.out.println(cookie);
        cookie.setMaxAge(60 * 60 * 24 * 7);//保留7天
        response.addCookie(cookie);
    }
}
