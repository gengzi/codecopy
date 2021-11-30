package fun.gengzi.codecopy.business.subdata.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import fun.gengzi.codecopy.vo.PageData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class BussinessTableToDicVo {
    //主键ID
    @ApiModelProperty(value = "主键")
    private Long id;
    // 字段1
    @ApiModelProperty(value = "字段-name")
    private String name;
    // 字段2
    private String code;
    // 字段2
    private String codename;
    // 地区code
    private String addresscode;
    //  地区名称
    private String addressValue;
    // 数据字典对应code
    private String diccode;
    // 数据字典对应汉字
    private String dicValue;
    //创建日期
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date createdate;
    //修改日期
    @ApiModelProperty(value = "修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date updatedate;
    //是否删除, 0 否 1 是
    private short isDel;
    //日志 ID, 可用作日志的查询
    private String guid;
    //版本号
    private String dataVersion;


}