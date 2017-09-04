package com.whatyplugin.base.http;


import java.util.regex.Pattern;

public final class HttpDateTime {
    class TimeOfDay {
        int hour;
        int minute;
        int second;

        TimeOfDay(int h, int m, int s) {
            super();
            this.hour = h;
            this.minute = m;
            this.second = s;
        }
    }

    private static Pattern HTTP_DATE_ANSIC_PATTERN = null;
    private static final String HTTP_DATE_ANSIC_REGEXP = "[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})";
    private static Pattern HTTP_DATE_RFC_PATTERN = null;
    private static final String HTTP_DATE_RFC_REGEXP = "([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])";

    static {
        HttpDateTime.HTTP_DATE_RFC_PATTERN = Pattern.compile("([0-9]{1,2})[- ]([A-Za-z]{3,9})[- ]([0-9]{2,4})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])");
        HttpDateTime.HTTP_DATE_ANSIC_PATTERN = Pattern.compile("[ ]([A-Za-z]{3,9})[ ]+([0-9]{1,2})[ ]([0-9]{1,2}:[0-9][0-9]:[0-9][0-9])[ ]([0-9]{2,4})");
    }

    public HttpDateTime() {
        super();
    }

    private static int getDate(String dateString) {
        int date = dateString.length() == 2 ? (dateString.charAt(0) - 48) * 10 + (dateString.charAt(1) - 48) : dateString.charAt(0) - 48;
        return date;
    }

    private static int getMonth(String monthString) {
        int v3 = 2;
        int v1 = 0;
        switch(Character.toLowerCase(monthString.charAt(0)) + Character.toLowerCase(monthString.charAt(1)) + Character.toLowerCase(monthString.charAt(v3)) - 291) {
            case 9: {
                return 11;
            }
            case 10: {
                return 1;
            }
            case 22: {
                return v1;
            }
            case 26: {
                return 7;
            }
            case 29: {
                return v3;
            }
            case 32: {
                return 3;
            }
            case 35: {
                return 9;
            }
            case 36: {
                return 4;
            }
            case 37: {
                return 8;
            }
            case 40: {
                return 6;
            }
            case 42: {
                return 5;
            }
            case 48: {
                return 10;
            }
        }

        throw new IllegalArgumentException();
    }


    private static int getYear(String yearString) {
        int year;
        int length = 2;
        if(yearString.length() == 2) {
            int temp = (yearString.charAt(0) - 48) * 10 + (yearString.charAt(1) - 48);
            year = temp >= 70 ? temp + 1900 : temp + 2000;
        }
        else if(yearString.length() == 3) {
            year = (yearString.charAt(0) - 48) * 100 + (yearString.charAt(1) - 48) * 10 + (yearString.charAt(length) - 48) + 1900;
        }
        else if(yearString.length() == 4) {
            year = (yearString.charAt(0) - 48) * 1000 + (yearString.charAt(1) - 48) * 100 + (yearString.charAt(length) - 48) * 10 + (yearString.charAt(3) - 48);
        }
        else {
            year = 1970;
        }

        return year;
    }
}

