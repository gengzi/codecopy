package fun.gengzi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    @ApiModelProperty(value = "商品id")
    private Long id;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "价格 分")
    private Integer price;
    @ApiModelProperty(value = "商品类型")
    private String goodsType;
    @ApiModelProperty(value = "购买数量")
    private Integer sales;
    @ApiModelProperty(value = "用户id--- 手动传")
    private String userid;

}
