package fun.gengzi.codecopy.business.redis.controller;

public class SkipListNode {

    /**
     * 对象
     */
    String obj;
    /**
     * 分数
     */
    Double score;
    /**
     * 后向指针
     */
    SkipListNode backWord;

    /**
     * 层级
     */
    SkipListLevel[] level;

    public SkipListNode(Integer size, double score,String obj) {
        level=new SkipListLevel[size];
        for(int i=0;i<size;i++) level[i]=new SkipListLevel();
        this.score = score;
        this.obj=obj;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public SkipListNode getBackWord() {
        return backWord;
    }

    public void setBackWord(SkipListNode backWord) {
        this.backWord = backWord;
    }

    public SkipListLevel[] getLevel() {
        return level;
    }

    public void setLevel(SkipListLevel[] level) {
        this.level = level;
    }
}