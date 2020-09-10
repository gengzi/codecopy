package fun.gengzi.codecopy.business.luckdraw.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_integral")
public class TbIntegral implements Serializable {
    private static final long serialVersionUID = -6105491137297093022L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    //用户id
    @Column(name = "uid")
    private String uid;
    //活动id
    @Column(name = "activityid")
    private String activityid;
    //用户名称
    @Column(name = "uname")
    private String uname;
    //积分
    @Column(name = "integral")
    private Integer integral;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "createtime")
    private Date createtime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "updatetime")
    private Date updatetime;
}