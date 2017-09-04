package com.whatyplugin.base.type;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MCDate implements Serializable {
    private Date _date;
    private long milliseconds;

    private MCDate(long milliseconds) {
        super();
        this.milliseconds = milliseconds;
    }

    private MCDate(Date _date) {
        super();
        this._date = _date;
    }

    public String CNHHMMSS() {
        return new SimpleDateFormat("HH时mm分ss").format(this._date);
    }

    public String CNYYYYMMDD() {
        return new SimpleDateFormat("yyyy年MM月dd").format(this._date);
    }

    public String CNYYYYMMDDHHMM() {
        return new SimpleDateFormat("yyyy年MM月dd�?HH时mm").format(this._date);
    }

    public String ENHHMM() {
        return new SimpleDateFormat("HH:mm").format(this._date);
    }

    public String ENHHMMSS() {
        return new SimpleDateFormat("HH:mm:ss").format(this._date);
    }

    public String ENMMDD() {
        return new SimpleDateFormat("MM-dd").format(this._date);
    }

    public String ENMMDDHHMM() {
        return new SimpleDateFormat("MM-dd HH:mm").format(this._date);
    }

    public String ENYYYYMMDD() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this._date);
    }

    public String ENYYYYMMDDHHMM() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this._date);
    }

    public String GREENWICH() {
        return new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SZ").format(this._date);
    }

    public String INTERVALAGO() {
        SimpleDateFormat v21 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat v22 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String v4 = this.ENYYYYMMDDHHMM();
        String v7 = v21.format(new Date());
        Date v10 = this._date;
        Integer.valueOf(v7.substring(8, 10)).intValue();
        Integer.valueOf(v4.substring(8, 10)).intValue();
        String v16 = v22.format(v10).substring(5, 12);
        String v27 = v22.format(v10).substring(0, 12);
        int v15 = Integer.valueOf(v16.substring(0, 2)).intValue();
        int v11 = Integer.valueOf(v16.substring(3, 5)).intValue();
        if(v15 < 10 && v11 < 10) {
            new StringBuilder(String.valueOf(v16.substring(1, 3))).append(v16.substring(4)).toString();
        }
        else if(v15 < 10) {
            v16.substring(1);
        }
        else if(v11 < 10) {
            new StringBuilder(String.valueOf(v16.substring(0, 3))).append(v16.substring(4)).toString();
        }

        int v26 = Integer.valueOf(v27.substring(5, 7)).intValue();
        int v25 = Integer.valueOf(v27.substring(8, 10)).intValue();
        if(v26 < 10 && v25 < 10) {
            new StringBuilder(String.valueOf(v27.substring(0, 5))).append(v27.substring(6, 8)).append(v27.substring(9)).toString();
        }
        else if(v26 < 10) {
            new StringBuilder(String.valueOf(v27.substring(0, 5))).append(v27.substring(6)).toString();
        }
        else if(v25 < 10) {
            new StringBuilder(String.valueOf(v27.substring(0, 8))).append(v27.substring(9)).toString();
        }

        float v8 = ((float)MCDate.farmatTime(String.valueOf(v7.substring(0, 10)) + " 00:00:00").longValue());
        float v5 = ((float)MCDate.farmatTime(String.valueOf(v4.substring(0, 10)) + " 00:00:00").longValue());
        int v9 = Integer.valueOf(v7.substring(0, 4)).intValue();
        int v6 = Integer.valueOf(v4.substring(0, 4)).intValue();
        int v13 = ((int)(MCDate.farmatTime(v7).longValue() / 1000 - MCDate.farmatTime(v4).longValue() / 1000));
        String v12 = null;
        v4.substring(11, 16);
        int v24 = v13;
        if(v5 == v8) {
            if(v24 < 60) {
                v12 = "刚刚";
            }
            else if(v24 < 1800) {
                v12 = String.valueOf(v24 / 60) + "分钟";
            }
            else if(v24 < 86400) {
                v12 = this.ENHHMM();
            }
        }
        else if(v5 < v8) {
            v12 = v6 == v9 ? this.ENMMDD() : this.ENYYYYMMDD();
        }

        return v12;
    }

    public Date date() {
        return this._date;
    }

    public static MCDate dateWithGreenwichDateString(String dateString) {
        Date date=new Date();
        try {
            date = DateFormat.getDateInstance().parse(dateString);
        }
        catch(ParseException v1) {
            v1.printStackTrace();
        }

        return new MCDate(date);
    }

    public static MCDate dateWithMilliseconds(long milliseconds) {
        Date date = new Date();
        date.setTime(milliseconds);
        return new MCDate(date);
    }

    public static MCDate dateWithSeconds(long seconds) {
        return MCDate.dateWithMilliseconds(1000 * seconds);
    }

    public static MCDate dateWithYYYYMMDDHHMMSSString(String dateString) {
        Date date= new Date();
        try {
            date = DateFormat.getDateInstance().parse(dateString);
        }
        catch(ParseException v1) {
            v1.printStackTrace();
        }

        return new MCDate(date);
    }

    public static MCDate dateWithYYYYMMDDString(String dateString) {
        Date date =  new Date();
        try {
            date = DateFormat.getDateInstance().parse(dateString);
        }
        catch(ParseException v1) {
            v1.printStackTrace();
        }

        return new MCDate(date);
    }

    public int dayOfMonth() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ((Calendar)gregorianCalendar).setTime(this._date);
        return ((Calendar)gregorianCalendar).get(5);
    }

    public int dayOfWeek() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ((Calendar)gregorianCalendar).setTime(this._date);
        return ((Calendar)gregorianCalendar).get(7);
    }

    public int dayOfYear() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ((Calendar)gregorianCalendar).setTime(this._date);
        return ((Calendar)gregorianCalendar).get(6);
    }

    private static Long farmatTime(String string) {
        Date date = null;
        try {
            date = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string).getTime());
        }
        catch(Exception v2) {
            v2.printStackTrace();
        }

        return Long.valueOf(date.getTime());
    }

    private boolean isSameDay(Date date, Date curDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = simpleDateFormat.format(date).equals(simpleDateFormat.format(curDate)) ? true : false;
        return flag;
    }

    private boolean isSameYear(Date date, Date curDate) {
        boolean flag = true;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(Integer.parseInt(simpleDateFormat.format(date).substring(0, 4)) != Integer.parseInt(simpleDateFormat.format(curDate).substring(0, 4))) {
                return false;
            }
        }
        catch(Exception v2) {
        }

        return flag;
    }

    public long millisecondsSince1970() {
        return this._date.getTime();
    }

    public int month() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ((Calendar)gregorianCalendar).setTime(this._date);
        return ((Calendar)gregorianCalendar).get(2);
    }

    public long secondsSince1970() {
        return this.millisecondsSince1970() / 1000;
    }

    public String specifyFormatTime(boolean isNeedShowTime) {
        String formatTime;
        long v11 = 60000;
        Date date = new Date();
        if(this.isSameDay(this._date, date)) {
            long v5 = new Date().getTime() - this._date.getTime();
            if(v5 > 0 && v5 <= v11) {
                formatTime = "刚刚";
                return formatTime;
            }

            if(v5 > v11 && v5 <= 1800000) {
                return String.valueOf(v5 / v11) + "分钟";
            }

            formatTime = this.ENHHMM();
        }
        else {
            if(this.isSameYear(this._date, date)) {
                if(isNeedShowTime) {
                    return this.ENMMDDHHMM();
                }

                return this.ENMMDD();
            }

            if(isNeedShowTime) {
                return this.ENYYYYMMDDHHMM();
            }

            formatTime = this.ENYYYYMMDD();
        }

        return formatTime;
    }

    public int year() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ((Calendar)gregorianCalendar).setTime(this._date);
        return ((Calendar)gregorianCalendar).get(1);
    }
}

