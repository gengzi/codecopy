package fun.gengzi.codecopy.business.luckdraw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAwardeeVo {
    //奖品名称
    private String prizeName;
    //获奖时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date awardeeTime;
    //发放时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date grantTime;
    //奖品是否发放 1 以发放 0 未发放
    private short isGrant;
    //奖品id
    private Integer prizeId;
}
