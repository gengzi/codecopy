package fun.gengzi.codecopy.java8.lamdba.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * java8 时间
 */
public class MyTime {

    /**
     * LocalDate 只包含简单的日期
     */
    @Test
    public void  fun01(){
        // 获取当前的日期
        LocalDate now = LocalDate.now();
        System.out.println(now.toString()); // 2020-06-01

        LocalDate of = LocalDate.of(2019, 1, 10);
        System.out.println(now.getDayOfMonth()); // 获取day
        System.out.println(now.lengthOfMonth());  // 返回该日期表示的月的长度。


    }


    @Test
    public void fun02(){
        // 获取当前的时分秒毫秒
        LocalTime now = LocalTime.now();
        System.out.println(now.toString());
        int minute = now.getMinute();
        int hour = now.getHour();
        int second = now.getSecond();


        LocalTime of = LocalTime.of(23, 22, 22);

        LocalTime parse = LocalTime.parse("22:22:22");
        System.out.println(parse);

    }

    @Test
    public void fun03(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toString());
    }


}
