package fun.gengzi.codecopy.business.connection.entity;

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
@Table(name = "mysql")
public class MysqlDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //主键
    @Column(name = "id")
    private Long id;
    //ip
    @Column(name = "ip")
    private String ip;
    //端口
    @Column(name = "port")
    private String port;
    //用户名
    @Column(name = "username")
    private String username;
    //密码
    @Column(name = "password")
    private String password;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdate")
    private Date createdate;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedate")
    private Date updatedate;
}