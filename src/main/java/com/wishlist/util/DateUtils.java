package com.wishlist.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateUtils {

    public Instant parseFromBrowser(String date) {
        String pattern = getPattern(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(pattern, formatter);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public Instant parseFromBrowser(String date, String time) {
        String pattern = getPattern(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m, " + pattern, Locale.ENGLISH);
        String[] times = time.split(":");
        LocalDate localDate = LocalDate.parse(time + ", " + date, formatter);
        return localDate.atTime(Integer.parseInt(times[0]), Integer.parseInt(times[1])).toInstant(ZoneOffset.UTC);
    }

    private String getPattern(String date) {
        if (date.contains("/")) {
            return "M/d/yyyy";
        } else if (date.contains(".")) {
            return "M.d.yyyy";
        } else {
            return "M-d-yyyy";
        }
    }
}
