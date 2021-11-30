package fun.gengzi.codecopy.business.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>lua 脚本执行，传参的默认实体</h1>
 *
 * @author gengzi
 * @date 2020年12月23日14:31:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuaScriptExecEntity {
    // json数据源
    private Object info;
    // 过期时间（秒）
    private Integer ttl;
}
