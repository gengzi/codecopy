package fun.gengzi.codecopy.business.redis.controller;

public class SkipListLevel {
    /**
     * 前进指针
     */
    private SkipListNode forward;
    /**
     * 跨度
     */
    private Integer span;

    public SkipListLevel() {
        this.forward=null;
        this.span=0;
    }

    public SkipListLevel(SkipListNode forward, Integer span) {
        this.forward = forward;
        this.span = span;
    }

    public SkipListNode getForward() {
        return forward;
    }

    public void setForward(SkipListNode forward) {
        this.forward = forward;
    }

    public Integer getSpan() {
        return span;
    }

    public void setSpan(Integer span) {
        this.span = span;
    }
}