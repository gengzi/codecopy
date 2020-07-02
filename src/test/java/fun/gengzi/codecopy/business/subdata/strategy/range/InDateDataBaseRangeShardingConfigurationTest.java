package fun.gengzi.codecopy.business.subdata.strategy.range;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import org.junit.Test;

import java.util.Date;

public class InDateDataBaseRangeShardingConfigurationTest {

    @Test
    public void fun01(){
        Range<Date> singleton = Range.singleton(new Date());
        Date date = singleton.lowerEndpoint();
        Date date1 = singleton.upperEndpoint();
        String dateStr = DateUtil.formatDateTime(date);
        String dateStr1 = DateUtil.formatDateTime(date1);
        System.out.println(dateStr);
        System.out.println(dateStr1);


        Long i = 10000L;
        Range<Long> singleton1 = Range.singleton(i);
        System.out.println(singleton1.lowerEndpoint());
        System.out.println(singleton1.upperEndpoint());


    }

}