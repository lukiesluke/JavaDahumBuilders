package com.tprealty.corporation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.tprealty.corporation.network.Constant.PRE_NAME_DAHUM;

public class Utils {
    public static Typeface fontBold(AssetManager assets) {
        return Typeface.createFromAsset(assets, "OpenSans-Bold.ttf");
    }

    public static Typeface fontBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf");
    }

    public static Typeface fontRegular(AssetManager assets) {
        return Typeface.createFromAsset(assets, "OpenSans-Regular.ttf");
    }

    public static Typeface fontRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    public static Typeface fontLight(AssetManager assets) {
        return Typeface.createFromAsset(assets, "OpenSans-Light.ttf");
    }

    public static Typeface fontLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
    }

    public static String format(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(bigDecimal);
    }

    @SuppressLint("SimpleDateFormat")
    public static String stringToDate(String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(value);
            return new SimpleDateFormat("MMMM dd, yyyy").format(Objects.requireNonNull(date));
        } catch (ParseException ignored) {
            return "Invalid date";
        }
    }

    public static String getPref(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PRE_NAME_DAHUM, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "{}");
    }

    public static void putPref(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PRE_NAME_DAHUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
