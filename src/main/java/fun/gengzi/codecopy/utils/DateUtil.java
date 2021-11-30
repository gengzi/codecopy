package fun.gengzi.codecopy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @DESCRIPTION
 * @AUTHER NING
 * @CREATE 2018/4/10
 */
public class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private final static Calendar c = Calendar.getInstance();
    private static final String formate = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    //static final String formate = "yyyy-MM-dd";
    private static final DateFormat df = new SimpleDateFormat(formate);

    public static String timeMillisToStr(String timeSt) {
        try {
            long s = Long.parseLong(timeSt);
            c.setTimeInMillis(s);
            return df.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("转换时间格式异常");
        }
        return null;
    }

    public static String dateToUTCStr(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("转换时间格式异常");
        }
        return null;
    }

    public static String timeStrToMillis(String timeSt) {
        try {
            Date date = df.parse(timeSt);
            return "" + date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            logger.info("转换时间格式异常");
        }
        return null;
    }
}
