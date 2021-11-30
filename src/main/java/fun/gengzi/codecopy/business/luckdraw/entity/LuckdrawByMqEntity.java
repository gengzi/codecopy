package fun.gengzi.codecopy.business.luckdraw.entity;

import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>抽奖mq相关实体</h1>
 *
 * @author gengzi
 * @date 2020年9月27日09:49:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuckdrawByMqEntity {

    // 此条抽奖数据的 mq 标识
    private long currentTime;
    // 具体的响应信息，或者使用 throw new 的形式返回
    private LuckdrawEnum luckdrawEnum;


}
