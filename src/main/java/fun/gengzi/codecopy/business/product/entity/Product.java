package fun.gengzi.codecopy.business.product.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 7562274499822060288L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id 主键
    @Column("id")
    private Long id;
    //名称
    @Column("pname")
    private String pname;
    //类型
    @Column("type")
    private String type;
    //金额
    @Column("pmoney")
    private BigDecimal pmoney;
    //数目
    @Column("num")
    private Integer num;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column("createtime")
    private Date createtime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column("updatetime")
    private Date updatetime;
}

