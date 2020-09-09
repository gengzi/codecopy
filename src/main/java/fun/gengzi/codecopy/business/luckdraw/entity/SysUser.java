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
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private String uid;
    //名称
    @Column(name = "uname")
    private String uname;
    //用户类型
    @Column(name = "utype")
    private Integer utype;
    //电话
    @Column(name = "phone")
    private String phone;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createtime")
    private Date createtime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatetime")
    private Date updatetime;
}