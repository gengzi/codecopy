package fun.gengzi.codecopy.business.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>zsetadd 实体</h1>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZsetAddEntity {
    // 数据集合
    private Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
    // 集合个数
    private Integer size;

}
