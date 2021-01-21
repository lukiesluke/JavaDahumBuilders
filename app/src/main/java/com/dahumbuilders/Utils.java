package com.dahumbuilders;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Utils {

    public static String format(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(bigDecimal);
    }

    @SuppressLint("SimpleDateFormat")
    public static String stringToDate(String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(value);
            return new SimpleDateFormat("MMMM dd, yyyy").format(Objects.requireNonNull(date));
        } catch (ParseException ignored) {
            return "Invalid date";
        }
    }
}
