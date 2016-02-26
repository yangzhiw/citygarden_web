package com.citygarden.web.rest.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Administrator on 2016/2/26 0026.
 */
public class RoundDoubleUtil {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
