package com.example.demo;



import org.springframework.util.DigestUtils;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuweifeng wrote on 2017/10/26.
 */
public class CommonUtil {

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }


    /**
     * 生成uuid
     */
    public static String token() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 是否是直辖市里的区县
     *
     * @param cityCode
     *         区县码
     * @return 是否是直辖市里的
     */
    public static boolean isZhiXiaShi(Integer cityCode) {
        //110000北京，120000天津，310000上海， 500000重庆，810000香港，820000澳门
        int city = cityCode / 1000;
        return city == 110 || city == 120 || city == 310 || city == 500 || city == 810 || city == 820;
    }

    /**
     * 截取double2位
     *
     * @param d
     *         d
     * @return 截取后结果
     */
    public static Double cutDouble2(Double d) {
        if (d == null) {
            return 0.00;
        }
        DecimalFormat df = new DecimalFormat("######0.000");
        return Double.parseDouble(df.format(d));
    }

    public static Double parsePercent(Integer var1, Integer var2) {
        try {
            return cutDouble2(var1 * 1.0 / var2);
        } catch (Exception e) {
            return 0.000;
        }
    }

    /**
     * 将object转为long
     *
     * @param object
     *         object
     * @return long
     */
    public static Long parseObject(Object object) {
        if (object instanceof Integer) {
            return ((Integer) object).longValue();
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        return 0L;
    }

    public static boolean isMobile(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static String secToTime(Integer time) {
        String timeStr;
        int hour;
        int minute;
        int second;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }
}
