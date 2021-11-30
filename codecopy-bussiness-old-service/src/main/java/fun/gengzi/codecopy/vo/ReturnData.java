package fun.gengzi.codecopy.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * <h1>通用的响应对象</h1>
 *
 * @author gengzi
 * @version 1.0
 * @date 2020年5月23日19:33:20
 */
// jackSon JsonInclude.Include.NON_NULL 如果某些字段为null 就不序列号该字段
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnData {

    //操作状态码
    @ApiModelProperty(value = "状态码")
    private int status;

    //返回数据
    @ApiModelProperty(value = "返回数据")
    private Object info;

    //错误信息
    @ApiModelProperty(value = "错误信息", example = "失败！")
    private Object message;


    /**
     * 创建ReturnData对象
     *
     * @return
     */
    public static ReturnData newInstance() {
        return new ReturnData();
    }


    /**
     * 设置通用失败信息
     *
     * @param message 失败信息
     */
    public void setFailure(Object message) {
        this.status = RspCodeEnum.ERROR.getCode();
        this.message = message;
        this.info = new HashMap<String, String>();
    }

    /**
     * 设置不带数据的通用成功信息
     */
    public void setSuccess() {
        this.status = RspCodeEnum.SUCCESS.getCode();
        this.message = RspCodeEnum.SUCCESS.getDesc();
        this.info = new HashMap<String, String>();
    }


    /**
     * 设置带数据通用成功信息
     *
     * @param data 响应数据
     */
    public void setSuccess(Object data) {
        this.status = RspCodeEnum.SUCCESS.getCode();
        this.message = RspCodeEnum.SUCCESS.getDesc();
        this.info = data == null ? new HashMap<String, Object>() : data;
    }

}

