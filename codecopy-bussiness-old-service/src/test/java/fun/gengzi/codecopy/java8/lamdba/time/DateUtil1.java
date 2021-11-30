package fun.gengzi.codecopy.java8.lamdba.time;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 基于java8封装的时间处理工具类
 * <p>
 * </p>
 */
public class DateUtil1 {


    private static final String HYPHEN = "-";
    private static final String COLON = ":";

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间格式 DateTimeFormatter (Java8) ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    enum FormatEnum {
        /**
         * 返回 DateTimeFormatter "yyyy-MM-dd HH:mm:ss" 时间格式
         */
        FORMAT_DATA_TIME(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)),

        /**
         * 返回 DateTimeFormatter "yyyyMMddHHmmss"的时间格式
         */
        FORMAT_DATA_TIME_NO_SYMBOL(DateTimeFormatter.ofPattern(DATETIME_FORMAT)),

        /**
         * 返回 DateTimeFormatter "yyyy-MM-dd"的时间格式
         */
        FORMAT_DATE(DateTimeFormatter.ofPattern(DATE_FORMAT)),

        /**
         * 返回 DateTimeFormatter "HH:mm:ss"的时间格式
         */
        FORMAT_TIME(DateTimeFormatter.ofPattern(TIME_FORMAT));

        private DateTimeFormatter value;

        FormatEnum(DateTimeFormatter format) {
            this.value = format;
        }
    }
    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间格式 DateTimeFormatter (Java8) ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间格式 字符串 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 年的时间格式
     * <br/>
     * 返回 "yyyy" 字符串
     */
    public static final String YEAR_FORMAT = "yyyy";

    /**
     * 月的时间格式
     * <br/>
     * 返回 "MM" 字符串
     */
    public static final String MONTH_FORMAT = "MM";

    /**
     * 日的时间格式
     * <br/>
     * 返回 "dd" 字符串
     */
    public static final String DAY_FORMAT = "dd";

    /**
     * 时的时间格式
     * <br/>
     * 返回 "HH" 字符串
     */
    public static final String HOUR_FORMAT = "HH";

    /**
     * 分的时间格式
     * <br/>
     * 返回 "mm" 字符串
     */
    public static final String MINUTE_FORMAT = "mm";

    /**
     * 秒的时间格式
     * <br/>
     * 返回 "ss" 字符串
     */
    public static final String SECOND_FORMAT = "ss";

    /**
     * <span color='red'>年-月-日</span>的时间格式
     * <br/>
     * 返回 "yyyy-MM-dd" 字符串
     */
    public static final String DATE_FORMAT = YEAR_FORMAT + HYPHEN + MONTH_FORMAT + HYPHEN + DAY_FORMAT;

    /**
     * <span color='red'>时:分:秒</span>的时间格式
     * <br/>
     * 返回 "HH:mm:ss" 字符串
     */
    public static final String TIME_FORMAT = HOUR_FORMAT + COLON + MINUTE_FORMAT + COLON + SECOND_FORMAT;

    /**
     * <span color='red'>年-月-日 时:分:秒</span>的时间格式
     * <br/>
     * 返回 "yyyy-MM-dd HH:mm:ss" 字符串
     */
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    /**
     * <span color='red'>年月日时分秒</span>的时间格式（无符号）
     * <br/>
     * 返回 "yyyyMMddHHmmss" 字符串
     */
    public static final String DATETIME_FORMAT = YEAR_FORMAT + MONTH_FORMAT + DAY_FORMAT + HOUR_FORMAT + MINUTE_FORMAT + SECOND_FORMAT;

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间格式 字符串 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/



    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间戳 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取秒级时间戳
     */
    public static Long epochSecond() {
        return localDateTime().toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取毫秒级时间戳
     */
    public static Long epochMilli() {
        return localDateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间戳 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 当前时间相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取当前详细时间，like 2018-08-27 17:20:06
     */
    public static String dateTime() {
        return localDateTime().format(FormatEnum.FORMAT_DATA_TIME.value);
    }

    /**
     * 获取当前详细时间，like 20180827172006
     */
    public static String dateTimeNoSymbol() {
        return localDateTime().format(FormatEnum.FORMAT_DATA_TIME_NO_SYMBOL.value);
    }

    /**
     * 获取当前日期，like 2018-08-27
     */
    public static String date() {
        return localDate() + "";
    }

    /**
     * 获取当前时间，like 17:20:06
     */
    public static String time() {
        return localTime().format(FormatEnum.FORMAT_TIME.value);
    }

    /**
     * 获取当前年
     */
    public static Integer year() {
        return localDate().getYear();
    }

    /**
     * 获取当前月
     */
    public static int month() {
        return localDate().getMonthValue();
    }

    /**
     * 获取当前年中的日
     */
    public static Integer dayOfYear() {
        return localDate().getDayOfYear();
    }

    /**
     * 获取当前月中的日
     */
    public static Integer dayOfMonth() {
        return localDate().getDayOfMonth();
    }

    /**
     * 获取当前星期中的日
     */
    public static Integer dayOfWeek() {
        return localDate().getDayOfWeek().getValue();
    }

    /**
     * 获取当前小时
     */
    public static Integer hour() {
        return localTime().getHour();
    }

    /**
     * 获取当前分钟
     */
    public static Integer minute() {
        return localTime().getMinute();
    }

    /**
     * 获取当前秒
     */
    public static Integer second() {
        return localTime().getSecond();
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 当前时间相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/



    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 未来、历史时间相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取当前年的 前几年 的日期
     * <p>
     *
     * @param years 前几年 正整数
     * @return 当前年的 前几年 的 对应 格式 日期
     */
    public static String minusYears(Long years, FormatEnum formatEnum) {
        return minusOrPlusYears(-years, formatEnum);
    }

    /**
     * 获取当前年的 后几年 的日期
     * <p>
     *
     * @param years 后几年 正整数
     * @return 当前年的 后几年 的 对应 格式 日期
     */
    public static String plusYears(Long years, FormatEnum formatEnum) {
        return minusOrPlusYears(years, formatEnum);
    }

    /**
     * 获取当前月的 前几月 日期
     *
     * @param months     前几月 正整数
     * @param formatEnum 格式
     * @return 当前月的 前几月 的 对应 格式 日期
     */
    public static String minusMonths(Long months, FormatEnum formatEnum) {
        return minusOrPlusMonths(-months, formatEnum);
    }

    /**
     * 获取当前月的 后几月 的日期
     *
     * @param months     后几月 正整数
     * @param formatEnum 格式
     * @return 当前月的 后几月 的 对应 格式 日期
     */
    public static String plusMonths(Long months, FormatEnum formatEnum) {
        return minusOrPlusMonths(months, formatEnum);
    }

    /**
     * 获取当前日的 前几日 的日期
     *
     * @param days       前几日 正整数
     * @param formatEnum 格式
     * @return 当前日的 前几日 的 对应 格式 日期
     */
    public static String minusDays(Long days, FormatEnum formatEnum) {
        return minusOrPlusDays(-days, formatEnum);
    }

    /**
     * 获取当前日的 后几日 的日期
     *
     * @param days       后几日 正整数
     * @param formatEnum 格式
     * @return 当前日的 后几日 的 对应 格式 日期
     */
    public static String plusDays(Long days, FormatEnum formatEnum) {
        return minusOrPlusDays(days, formatEnum);
    }

    /**
     * 获取当前星期的 前几星期 的日期
     *
     * @param weeks      前几星期 正整数
     * @param formatEnum 格式
     * @return 当前星期的 前几星期 的 对应 格式 日期
     */
    public static String minusWeeks(Long weeks, FormatEnum formatEnum) {
        return minusOrPlusWeeks(-weeks, formatEnum);
    }

    /**
     * 获取当前星期的 后几星期 的日期
     *
     * @param weeks      后几星期 正整数
     * @param formatEnum 格式
     * @return 当前星期的 后几星期 的 对应 格式 日期
     */
    public static String plusWeeks(Long weeks, FormatEnum formatEnum) {
        return minusOrPlusWeeks(weeks, formatEnum);
    }

    /**
     * 获取当前小时的 前几小时 的日期
     *
     * @param hours      前几小时 正整数
     * @param formatEnum 格式
     * @return 当前小时的 前几小时 的 对应 格式 日期
     */
    public static String minusHours(Long hours, FormatEnum formatEnum) {
        return minusOrPlusHours(-hours, formatEnum);
    }

    /**
     * 获取当前小时的 后几小时 的日期
     *
     * @param hours      后几小时 正整数
     * @param formatEnum 格式
     * @return 当前小时的 后几小时 的 对应 格式 日期
     */
    public static String plusHours(Long hours, FormatEnum formatEnum) {
        return minusOrPlusHours(hours, formatEnum);
    }

    /**
     * 获取当前分钟的 前几分钟 的日期
     *
     * @param minutes    前几分钟 正整数
     * @param formatEnum 格式
     * @return 当前分钟的 前几分钟 的 对应 格式 日期
     */
    public static String minusMinutes(Long minutes, FormatEnum formatEnum) {
        return minusOrPlusMinutes(-minutes, formatEnum);
    }

    /**
     * 获取当前分钟的 后几分钟 的日期
     *
     * @param minutes    后几分钟 正整数
     * @param formatEnum 格式
     * @return 当前分钟的 后几分钟 的 对应 格式 日期
     */
    public static String plusMinutes(Long minutes, FormatEnum formatEnum) {
        return minusOrPlusMinutes(minutes, formatEnum);
    }

    /**
     * 获取当前秒的 前几秒 的日期
     *
     * @param seconds    前几秒 正整数
     * @param formatEnum 格式
     * @return 当前秒的 前几秒 的 对应 格式 日期
     */
    public static String minusSeconds(Long seconds, FormatEnum formatEnum) {
        return minusOrPlusSeconds(-seconds, formatEnum);
    }

    /**
     * 获取当前秒的 前几秒/后几秒 的日期
     *
     * @param seconds    后几秒 正整数
     * @param formatEnum 格式
     * @return 当前秒的 后几秒 的 对应 格式 日期
     */
    public static String plusSeconds(Long seconds, FormatEnum formatEnum) {
        return minusOrPlusSeconds(seconds, formatEnum);
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 未来、历史时间相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间转换相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     *
     * @param time
     * @param format
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * 这是满足特殊情况，在object类型下转换为String时，转换时间类型会出现异常错误，可以使用这种方式进行转换
     *
     * @param time
     * @return
     */
    public static LocalDateTime conversionStringToDateTime(String time) {
        return Timestamp.valueOf(time).toLocalDateTime();
    }

    /***
     * 将long类型的timestamp转为LocalDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }


    /**
     * Date类型转LocalDateTime
     * <p>
     *
     * @param date date类型时间
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Date类型转LocalDate
     * <p>
     *
     * @param date date类型时间
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * Date类型转LocalTime
     * <p>
     *
     * @param date date类型时间
     * @return LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * LocalDateTime 类型转 Date
     *
     * @param localDateTime localDateTime
     * @return 转换后的Date类型日期
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate类型转Date
     *
     * @param localDate localDate
     * @return 转换后的Date类型日期
     */
    public static Date toDate(LocalDate localDate) {
        return toDate(localDate.atStartOfDay());
    }

    /**
     * LocalTime类型转Date
     *
     * @param localTime localTime
     * @return 转换后的Date类型日期
     */
    public static Date toDate(LocalTime localTime) {
        return toDate(LocalDateTime.of(localDate(), localTime));
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间转换相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 时间间隔相关 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取 endDate-startDate 时间间隔天数
     * <br>创建人： leigq
     * <br>创建时间： 2018-11-07 09:55
     * <br>
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间间隔天数
     */
    public static Long daysInterval(LocalDate startDate, LocalDate endDate) {
        return endDate.toEpochDay() - startDate.toEpochDay();
    }

    /**
     * 获取 endDate-startDate 时间间隔天数
     * <br>创建人： leigq
     * <br>创建时间： 2018-11-07 09:55
     * <br>
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间间隔天数
     */
    public static Long daysInterval(String startDate, String endDate) {
        return daysInterval(LocalDateTime.parse(endDate, FormatEnum.FORMAT_DATA_TIME.value).toLocalDate(),
                LocalDateTime.parse(startDate, FormatEnum.FORMAT_DATA_TIME.value).toLocalDate());
    }

    /**
     * 获取 endDate-startDate 时间间隔天数
     * <br>创建人： leigq
     * <br>创建时间： 2018-11-07 09:55
     * <br>
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间间隔天数
     */
    public static Long daysInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return daysInterval(startDate.toLocalDate(), endDate.toLocalDate());
    }

    /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 时间间隔相关 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

    /*↓↓↓只允许此类调用↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 获取 当前年 的前几年/后几年的日期
     * <p>
     *
     * @param yearsToAddOrSubtract 后几年传正整数，前几年传负数
     * @param formatEnum           格式
     * @return 当前年的前几年/后几年的对应 格式 日期
     */
    private static String minusOrPlusYears(Long yearsToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusYears(yearsToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取 当前月 的前几月/后几月的日期
     *
     * @param monthsToAddOrSubtract 后几月传正整数，前几月传负数
     * @param formatEnum            格式
     * @return 当前月的前几月/后几月的对应 格式 日期
     */
    private static String minusOrPlusMonths(Long monthsToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusMonths(monthsToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取 当前日 的前几日/后几日的日期
     *
     * @param daysToAddOrSubtract 后几日传正整数，前几日传负数
     * @param formatEnum          格式
     * @return 当前日的前几日/后几日的 对应 格式 日期
     */
    private static String minusOrPlusDays(Long daysToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusDays(daysToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取当前星期的前几星期/后几星期的日期
     *
     * @param weeksToAddOrSubtract 后几星期传正整数，前几星期传负数
     * @param formatEnum           格式
     * @return 当前星期的前几星期/后几星期的 对应 格式 日期
     */
    private static String minusOrPlusWeeks(Long weeksToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusWeeks(weeksToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取当前小时的前几小时/后几小时的日期
     *
     * @param hoursToAddOrSubtract 后几小时传正整数，前几小时传负数
     * @param formatEnum           格式
     * @return 当前小时的前几小时/后几小时的 对应 格式 日期
     */
    private static String minusOrPlusHours(Long hoursToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusHours(hoursToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取当前分钟的前几分钟/后几分钟的日期
     *
     * @param minutesToAddOrSubtract 后几分钟传正整数，前几分钟传负数
     * @param formatEnum             格式
     * @return 当前分钟的前几分钟/后几分钟的 对应 格式 日期
     */
    private static String minusOrPlusMinutes(Long minutesToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusMinutes(minutesToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取当前秒的前几秒/后几秒的日期
     *
     * @param secondsToAddOrSubtract 后几秒传正整数，前几秒传负数
     * @param formatEnum             格式
     * @return 当前秒的前几秒/后几秒的 对应 格式 日期
     */
    private static String minusOrPlusSeconds(Long secondsToAddOrSubtract, FormatEnum formatEnum) {
        return localDateTime().plusSeconds(secondsToAddOrSubtract).format(formatEnum.value);
    }

    /**
     * 获取 LocalDate
     */
    private static LocalDate localDate() {
        return localDateTime().toLocalDate();
    }

    /**
     * 获取 LocalTime
     */
    private static LocalTime localTime() {
        return localDateTime().toLocalTime();
    }

    /**
     * 获取 LocalDateTime
     */
    private static LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }

}




