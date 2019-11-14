package com.zj.room.utils.utilcode;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author jiang zhu on 2019/10/25
 */
public class TimeUtils {

    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * Return the current formatted time string.
     * <p>The pattern is {@code yyyy-MM-dd HH:mm:ss}.</p>
     *
     * @return the current formatted time string
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), getDefaultFormat());
    }

    /**
     * Return the current formatted time string.
     *
     * @param format The format.
     * @return the current formatted time string
     */
    public static String getNowString(@NonNull final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    private static SimpleDateFormat getDefaultFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(pattern);
        }
        return simpleDateFormat;
    }


    /**
     * Milliseconds to the formatted time string.
     *
     * @param millis The milliseconds.
     * @param format The format.
     * @return the formatted time string
     */
    public static String millis2String(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     *  时间
     *  1）0-1min      刚刚
     *  2）1-60min     xx分钟前，如3分钟前
     *  3）1-24h        xx小时前，如2小时前
     *  4）昨天的       昨天+时+分，如，昨天 05:30
     *  5）昨天之前的   月+日+时+分，如，1月3日 05:30
     *  6）非本年       年+月+日+时+分，如2017年1月12日 05:30
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time){
        boolean isTodayMessage = false;

        Calendar todayBegin = Calendar.getInstance();
        todayBegin.set(Calendar.HOUR_OF_DAY, 0);
        todayBegin.set(Calendar.MINUTE, 0);
        todayBegin.set(Calendar.SECOND, 0);

        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);

        Calendar messageTime = Calendar.getInstance();
        messageTime.setTime(new Date(time));
        if (messageTime.before(todayEnd) && messageTime.after(todayBegin)) {
            isTodayMessage = true;
        }

        SimpleDateFormat formatter = null;

        int year = messageTime.get(Calendar.YEAR);
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date(System.currentTimeMillis()));
        int currentYear = currentCalendar.get(Calendar.YEAR);

        if (isTodayMessage) {
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - time;
            if (duration < 60 * 1000 && duration >= 0) {
                //60s以内
                return "刚刚";
            } else if (duration >= 60 * 1000 && duration < 60 * 60 * 1000 ) {
                //大于1分钟，小于1小时
                return duration /60 /1000 + "分钟前";
            } else if (duration >= 3600 * 1000 && duration < 3600 * 24 * 1000) {
                //大于1小时
                return duration / 3600 /1000 + "小时前";
            }
        }else if (isYesterday(time)){
            formatter = new SimpleDateFormat("HH:mm");
            return "昨天" + formatter.format(messageTime.getTime());
        }else if (year != currentYear){
            formatter = new SimpleDateFormat("yyyy年M月d日 HH:mm");
        }else {
            formatter = new SimpleDateFormat("M月d日 HH:mm");
        }

        if (formatter == null){
            return "刚刚";
        }else{
            return formatter.format(messageTime.getTime());
        }
    }


    private static boolean isYesterday(long time) {
        boolean isYesterday = false;
        Date date;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
            date = sdf.parse(sdf.format(new Date()));
            assert date != null;
            if (time < date.getTime() && time > (date.getTime() - 24*60*60*1000)) {
                isYesterday = true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isYesterday;
    }

}
