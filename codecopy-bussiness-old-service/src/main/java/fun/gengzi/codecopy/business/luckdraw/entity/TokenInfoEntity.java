package fun.gengzi.codecopy.business.luckdraw.entity;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>活动用户基本信息</h1>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoEntity {
    // 用户名
    private String uname;
    // 积分
    private String Integral;

}
