package fun.gengzi.codecopy.business.luckdraw.entity;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_awardee")
public class TbAwardee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    //活动id
    @Column(name = "activity_id")
    private String activityId;
    //活动名称
    @Column(name = "activity_name")
    private String activityName;
    //奖品id
    @Column(name = "prize_id")
    private Integer prizeId;
    //奖品名称
    @Column(name = "prize_name")
    private String prizeName;
    //获奖人id
    @Column(name = "awardee_id")
    private String awardeeId;
    //获奖人姓名
    @Column(name = "awardee_name")
    private String awardeeName;
    //获奖时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column(name = "awardee_time")
    private Date awardeeTime;
    //获奖个数
    @Column(name = "prize_num")
    private Integer prizeNum;
//奖品是否发放 1 以发放 0 未发放
    @Column(name = "is_grant")
    private short isGrant;
    //发放时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column(name = "grant_time")
    private Date grantTime;
    //奖品发放表id
    @Column(name = "grant_id")
    private Integer grantId;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column(name = "createtime")
    private Date createtime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column(name = "updatetime")
    private Date updatetime;
}
