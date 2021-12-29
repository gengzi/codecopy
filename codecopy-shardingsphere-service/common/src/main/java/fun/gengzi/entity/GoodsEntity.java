package fun.gengzi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)  // 配置实体监听
public class GoodsEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String goodsName;
    private Integer price;
    private String goodsDescriptionMarkDown;
    private String goodsDescription;
    private String goodsType;
    private String goodsImgUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date autoOnlineTime;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
    private Boolean isDel;
    private Integer sales;

}
