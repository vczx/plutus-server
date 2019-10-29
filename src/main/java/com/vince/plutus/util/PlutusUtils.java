package com.vince.plutus.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlutusUtils {

    public static final String TRANSACTION_TYPE_CR = "CR";
    public static final String TRANSACTION_TYPE_DR = "DR";
    public static final int MOCK_TRANSACTION_AMOUNT = 500;
    public static Double formatTwoDecimalPlaces(Double input) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(input));
    }
    public static <T> List<T> fromIterableToList(Iterable<T> result) {
        List<T> converted = new ArrayList<>();
        if (result != null) {
            result.forEach(converted::add);
        }
        return converted;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
