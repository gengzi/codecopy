package fun.gengzi.codecopy.vo;

import fun.gengzi.codecopy.constant.RspCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>token校验结果实体</h1>
 *
 * @author gengzi
 * @date 2020年6月4日17:29:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRsp<T> {
    // code
    private Integer code;
    // 信息
    private String msg;
    // 数据
    private T data;


    @Getter
    @AllArgsConstructor
    public enum TokenResponseEnum {
        // 成功
        SUCCESS(200, "success"),
        // 失败
        ERROR(0, "error");
        /**
         * 返回码
         */
        private Integer code;
        /**
         * 描述
         */
        private String desc;
    }
}
