package fun.gengzi.codecopy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页
 * @author gengzi
 * @date 2020年6月23日14:25:53
 *
 * @param <T> 数据类型
 *
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("当前页码")
    private Integer currentPage;

    @ApiModelProperty("总页码")
    private Integer totalPage;

    @ApiModelProperty("当前页数据")
    private List<T> rows;

}
