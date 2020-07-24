package fun.gengzi.codecopy.business.elasticsearch.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>EsSysEntiry 系统级参数</h1>
 *
 * @author gengzi
 * @date 2020年7月24日15:26:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class EsSysEntiry<T> {
    // 文档存储的地方
    @ApiModelProperty(value = "文档存储的地方")
    private String index;
    // 文档代表的对象的类
    @ApiModelProperty(value = "文档代表的对象的类")
    private String type;
    // 文档的唯一标识
    @ApiModelProperty(value = "文档的唯一标识")
    private String id;
    // json字符串
    @ApiModelProperty(value = "documentJson")
    private String documentJson;
    // 实体
    @ApiModelProperty(value = "documentObj")
    private T documentObj;

}
