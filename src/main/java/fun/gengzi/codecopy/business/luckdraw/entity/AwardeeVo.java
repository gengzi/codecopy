package fun.gengzi.codecopy.business.luckdraw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import fun.gengzi.codecopy.utils.datamask.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardeeVo {
    //活动id
    private String activityId;
    //活动名称
    private String activityName;
    //奖品名称
    private String prizeName;
    //获奖人姓名
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Name)
    private String awardeeName;

}
