package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static int getYear(String val) {
        String s = val.substring(val.length() - 4);
        return Integer.parseInt(s);
    }

    public static Month getMonth(String val) {
        String s = val.substring(0, 2);
        LocalDate month = LocalDate.of(1, Integer.parseInt(s), 1);
        return month.getMonth();
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Настоящее время" : date.format(DATE_FORMATTER);
    }
}