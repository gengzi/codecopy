package fun.gengzi.codecopy.business.luckdraw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrizeVo {
    //主键
    private Integer id;
    //奖品名称
    private String prizeName;
    //奖品信息
    private String prizeInfo;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date createtime;
}
