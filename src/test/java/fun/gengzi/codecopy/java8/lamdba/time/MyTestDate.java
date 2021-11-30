package fun.gengzi.codecopy.java8.lamdba.time;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class MyTestDate {


    @Test
    public void fun01() {
        // date 提供几个时间类
        // LocalDate  本地时间，年-月-日
        // LocalTime   本地时间，时-分-秒-毫秒
        // LocalDateTime 本地时间，年-月-日 时-分-秒-毫秒
        // DateTimeFormatter  线程安全，格式化时间

        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant1 = clock.instant();
        java.util.Date from1 = Date.from(instant1);


        Instant instant = clock.instant();
        java.util.Date from = Date.from(instant);

        System.out.println(from);

        LocalDate date = LocalDate.of(2020, 11, 22);
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dow = date.getDayOfWeek();
        int len = date.lengthOfMonth();
        boolean leap = date.isLeapYear();


        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTimeFormatter.format(now);
        System.out.println(format);

        LocalDate localDate = LocalDateTime.now().toLocalDate();
        LocalTime localTime = LocalDateTime.now().toLocalTime();

    }

    @Test
    public void fun02() {
//        LocalDateTime currentLocalDateTime = DateUtil.getCurrentLocalDateTime();
//        System.out.println(currentLocalDateTime.toString());

        boolean timeInRange = isTimeInRange("00:55:11", "22:55:11");
        System.out.println(timeInRange);


    }
    public static final String DATE_FMT_8 = "HH:mm:ss";

    /**
     * 判断当前时分秒，是否在规定范围中
     * @param start 开始时间
     * @param end  结束时间
     * @return 在，true ，不在 false
     */
    public boolean isTimeInRange(String start, String end) {
        if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
            throw new IllegalArgumentException("some date parameters is null or blank");
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FMT_8);
        LocalTime startTime = LocalTime.from(dateTimeFormatter.parse(start));
        LocalTime endTime = LocalTime.from(dateTimeFormatter.parse(end));
        if(startTime.isAfter(endTime)){
            throw new IllegalArgumentException("some date parameters is dateBein after dateEnd");
        }
        LocalTime now = LocalTime.now();
        return (startTime.isBefore(now) && endTime.isAfter(now) || startTime.compareTo(now) == 0 || endTime.compareTo(now) == 0);
    }


}
