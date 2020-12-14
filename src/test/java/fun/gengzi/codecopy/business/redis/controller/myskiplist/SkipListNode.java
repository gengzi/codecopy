package fun.gengzi.codecopy.business.redis.controller.myskiplist;


import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 元素节点
 */

@Data
public class SkipListNode<T> {
    public Integer key;
    public Object value;
    public SkipListNode<T> up, down, left, right;

    public static final Integer HEAD_KEY = Integer.MIN_VALUE;
    public static final Integer TAIL_KEY = Integer.MAX_VALUE;


    public SkipListNode(Integer key, Object value) {
        this.key = key;
        this.value = value;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        SkipListNode node = null;
        if (obj instanceof SkipListNode) {
            node = (SkipListNode) obj;
            Integer key = node.getKey();
            Object value = node.getValue();
            if (key == this.getKey() && value == this.getValue()) {
                return true;
            }
        }
        return false;
    }

}
