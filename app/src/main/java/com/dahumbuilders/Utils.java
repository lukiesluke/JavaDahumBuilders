package com.dahumbuilders;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Utils {

    public static String format(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(bigDecimal);
    }
}
