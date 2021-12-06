package fun.gengzi.vo;

import fun.gengzi.entity.GoodsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "价格 分")
    private Integer price;
    @ApiModelProperty(value = "描述markdown")
    private String goodsDescriptionMarkDown;
    @ApiModelProperty(value = "描述")
    private String goodsDescription;
    @ApiModelProperty(value = "商品类型")
    private String goodsType;
    @ApiModelProperty(value = "分享图片url")
    private String goodsImgUrl;
    @ApiModelProperty(value = "是否删除")
    private Byte isDel;
    @ApiModelProperty(value = "销量")
    private Integer sales;
}
