package com.mineplex.studio.essencepvp.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

    /**
     * Formats numbers with commas for better readability.
     *
     * @param number The number to format.
     * @return A string representation with commas (e.g., "1,234").
     */
    public static String formatNumber(double number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

}
