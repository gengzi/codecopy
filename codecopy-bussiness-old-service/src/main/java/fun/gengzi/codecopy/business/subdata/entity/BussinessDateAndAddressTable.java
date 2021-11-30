package fun.gengzi.codecopy.business.subdata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel
@Table(name = "t_bussiness_region")
public class BussinessDateAndAddressTable {
    //主键ID
    @ApiModelProperty(value = "主键")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 字段1
    @ApiModelProperty(value = "字段-name")
    @Column(name = "name")
    private String name;
    // 字段2
    @Column(name = "code")
    private String code;
    // 地区code
    @Column(name = "addresscode")
    private String addresscode;
    // 数据字典对应code
    @Column(name = "diccode")
    private String diccode;

    //创建日期
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 时分秒需要设置为 TIMESTAMP
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdate")
    private Date createdate;
    //修改日期
    @ApiModelProperty(value = "修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 时分秒需要设置为 TIMESTAMP
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedate")
    private Date updatedate;
    //是否删除, 0 否 1 是
    @Column(name = "is_del")
    private short isDel;
    //日志 ID, 可用作日志的查询
    @Column(name = "guid")
    private String guid;
    //版本号
    // 版本号，参与分片。  算法是 dataVersion % 2 , 那么这个类型，就不能是 String ，否则会导致错误  No signature of method: java.lang.String.mod()
    @Column(name = "data_version")
    private Integer dataVersion;


}