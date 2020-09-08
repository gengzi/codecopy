package fun.gengzi.codecopy.business.luckdraw.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h1>活动实体类</h1>
 *
 * @author gengzi
 * @date 2020年9月8日12:01:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_activity")
public class TbActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 主键
    @Column(name = "id")
    private String id;
    // 活动名称
    @Column(name = "activity_name")
    private String activityName;
    // 活动信息
    @Column(name = "activity_info")
    private String activityInfo;
    // 开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "starttime")
    private Date starttime;
    // 结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endtime")
    private Date endtime;
    // 是否失效
    @Column(name = "is_invalid")
    private short isInvalid;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private Date createtime;
    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime")
    private Date updatetime;
}