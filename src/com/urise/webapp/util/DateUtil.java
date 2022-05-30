package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.now();
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    private static int getYear(String val) {
        if (val == null || val.isEmpty()) {
            val = "0000";
        }
        String s = val.substring(val.length() - 4);
        return Math.min(Integer.parseInt(s), NOW.getYear());
    }

    private static Month getMonth(String val) {
        if (val == null || val.isEmpty()) {
            val = "00";
        }
        String s = val.substring(0, 2);
        LocalDate date = LocalDate.of(1, Math.min(Integer.parseInt(s), NOW.getMonthValue()), 1);
        return date.getMonth();
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.getYear() == NOW.getYear() ? "Сейчас" : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String val) {
        return val.equals("Сейчас") ? of(NOW.getYear(), NOW.getMonth()) : of(getYear(val), getMonth(val));
    }
}