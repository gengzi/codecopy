package fun.gengzi.codecopy.business.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>redisconfig 配置实体类</h1>
 *
 * @author gengzi
 * @date 2020年12月16日14:09:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedissondbConfigEntity implements Serializable {
    // 地址
    private String address;
    // 密码
    private String password;
    // 所有的db序号
    private List<Integer> databases = new ArrayList<>();
}
