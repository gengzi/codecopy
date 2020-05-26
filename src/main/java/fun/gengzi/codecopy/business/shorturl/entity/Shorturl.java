package fun.gengzi.codecopy.business.shorturl.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


/**
 * <h1>查询支付状态输入参数类型</h1>
 *
 * @author gengzi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shorturl")
public class Shorturl implements Serializable {

    private static final long serialVersionUID = 1223138960064561253L;
    @javax.persistence.Id
    @Id
    //主键
    @Column("id")
    private Long id;
    //短链接
    @Column("shorturl")
    private String shorturl;
    //普通的链接
    @Column("longurl")
    private String longurl;
    //期限
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column("termtime")
    private Date termtime;
    //是否过期
    @Column("isoverdue")
    private Integer isoverdue;
    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column("createtime")
    private Date createtime;
    //更新日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column("updatetime")
    private Date updatetime;
}