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
    private String address;
    private String password;
    private List<Integer> databases = new ArrayList<>();
}
