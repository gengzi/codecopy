package fun.gengzi.codecopy.business.redis.controller;

/**
 * 跳跃表
 */
public class SkipList {
    // 节点
    private SkipListNode header, tail;
    // list 的长度
    private Integer length;
    // 层级
    private Integer level;


    public SkipListNode getHeader() {
        return header;
    }

    public SkipListNode getTail() {
        return tail;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getLevel() {
        return level;
    }

    public void setHeader(SkipListNode header) {
        this.header = header;
    }

    public void setTail(SkipListNode tail) {
        this.tail = tail;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}