package fun.kwok.wechatpush.utils;

import com.github.heqiao2010.lunar.LunarCalendar;

import java.util.Calendar;
import java.util.Date;
public  class DateUtil {
    public static int getBirthdayDays(Date birthday,Boolean isChineseCalendar){
        Calendar today= Calendar.getInstance();
        if (isChineseCalendar){
            //农历生日
            Calendar nextBirthday=LunarCalendar.lunar2Solar(today.get(Calendar.YEAR),birthday.getMonth()+1,birthday.getDate(),false);
            if (!nextBirthday.before(today)){
                //今年生日未过
                return daysBetween(today,nextBirthday);
            }else {
                //今年生日已过，下次生日在明年
                nextBirthday=LunarCalendar.lunar2Solar(today.get(Calendar.YEAR)+1,birthday.getMonth()+1,birthday.getDate(),false);
                return daysBetween(today,nextBirthday);
            }
        }else {
            //公历生日
            Calendar nextBirthday=Calendar.getInstance();
            nextBirthday.set(today.get(Calendar.YEAR),birthday.getMonth(),birthday.getDate());
            if (!nextBirthday.before(today)){
                //今年生日未过
                return daysBetween(today,nextBirthday);
            }else {
                //今年生日已过，下次生日在明年
                nextBirthday.set(today.get(Calendar.YEAR)+1,birthday.getMonth(),birthday.getDate());
                return daysBetween(today,nextBirthday);
            }
        }
    }
    public static int getAnniversaryDays(Date anniversary,Boolean isChineseCalendar){
        Calendar today= Calendar.getInstance();
        if (isChineseCalendar){
            //农历纪念日
            Calendar Anniversary=LunarCalendar.lunar2Solar(anniversary.getYear(),anniversary.getMonth()+1,anniversary.getDate(),false);
            return daysBetween(Anniversary,today);
        }else {
            //公历纪念日
            Calendar Anniversary=Calendar.getInstance();
            Anniversary.setTime(anniversary);
            return daysBetween(Anniversary,today);
        }

    }

    public static boolean isAnniversary(Date anniversary,Boolean isChineseCalendar){
        Calendar today= Calendar.getInstance();
        Calendar Anniversary=Calendar.getInstance();
        if (isChineseCalendar){
            //农历纪念日
             Anniversary=LunarCalendar.lunar2Solar(anniversary.getYear(),anniversary.getMonth()+1,anniversary.getDate(),false);
        }else {
            //公历纪念日
             Anniversary=Calendar.getInstance();
            Anniversary.setTime(anniversary);
        }
        return Anniversary.get(Calendar.MONTH) == today.get(Calendar.MONTH) && Anniversary.get(Calendar.DATE) == today.get(Calendar.DATE);
    }

    public static int daysBetween(Calendar date1,Calendar date2){
        long time1 = date1.getTimeInMillis();
        long time2 = date2.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
