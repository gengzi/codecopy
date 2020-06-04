package fun.gengzi.codecopy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>token校验后响应的用户信息</h1>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUserInfoResp extends TokenRsp<TokenUserInfoResp.UserinfoData> {
    @Override
    public String toString() {
        return super.toString();
    }

    @Data
    public static class UserinfoData {
        private String certificated;
        private String certificateType;
        private String certificateNo;
        private String mobile;
        private String name;
    }

}
