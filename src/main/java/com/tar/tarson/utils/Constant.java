package com.tar.tarson.utils;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constant {
    private Constant() {
    }

    public static class Date {
        private Date() {
        }

        public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        public static DateTimeFormatter DATETIME_FORMATTER_ENG = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);

    }


}
