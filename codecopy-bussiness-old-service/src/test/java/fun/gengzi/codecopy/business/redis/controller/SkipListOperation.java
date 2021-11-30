package fun.gengzi.codecopy.business.redis.controller;

import java.util.Random;

/**
 * 跳跃表插入与删除操作的实现
 */
public class SkipListOperation {

    static final Integer SKIPLIST_MAXLEVEL = 32;
    static final Double SKIPLIST_P = 0.25;
    private Random random = new Random();

    /**
     * 随机数生成算法，保证上一层的节点个数是下一层的1/p
     *
     * @return
     */
    int randomLevel() {
        int level = 1;
        while ((random.nextInt() & 0xFFFF) < (SKIPLIST_P * 0xFFFF)) {
            level++;
        }
        return (level < SKIPLIST_MAXLEVEL) ? level : SKIPLIST_MAXLEVEL;
    }

    public SkipList skipListCreate() {
        SkipList skipList = new SkipList();
        skipList.setLevel(1);
        skipList.setLength(0);
        skipList.setHeader(new SkipListNode(SKIPLIST_MAXLEVEL, 0, null));
        return skipList;
    }

    /**
     * 跳跃表的插入实现，详细注释
     *
     * @param skipList
     * @param score
     * @param obj
     * @return
     */
    public SkipListNode insert(SkipList skipList, Double score, String obj) {
        //寻找元素过程中，每层能到达的最右节点
        SkipListNode[] updates = new SkipListNode[SKIPLIST_MAXLEVEL];
        //寻找元素过程中，每层所跨越的节点个数
        int[] rank = new int[SKIPLIST_MAXLEVEL];
        //头节点
        SkipListNode x = skipList.getHeader();
        //头节点全部层级
        SkipListLevel[] levels = x.getLevel();
        //跳跃表当前最大层级
        int maxLevel = skipList.getLevel();

        //从最高层级进行遍历
        for (int i = maxLevel - 1; i >= 0; i--) {
            //记录一下跨越过的节点
            rank[i] = i == (maxLevel - 1) ? 0 : rank[i + 1];
            //当前层级的第一个节点
            SkipListNode forward = levels[i].getForward();
            //寻找当前层级的合适节点，如果下一个节点不为空且分数小于要插入的节点，或者分数相等但是对象小于要插入的对象，即继续向右查找
            while (forward != null && (forward.getScore() < score || forward.getScore().equals(score) && forward.getObj().compareTo(obj) < 0)) {
                //记录跨越过的节点数量
                rank[i] += levels[i].getSpan();
                x = forward;
                forward = x.getLevel()[i].getForward();
            }
            //记录下每层到达的最右节点
            updates[i] = x;
        }

        //生成新的随机层数
        int currLevel = randomLevel();

        //新的层数比当前最大层数还要大，需要做一个处理
        if (currLevel > maxLevel) {
            for (int i = maxLevel; i < currLevel; i++) {
                //将当前层级最右边节点设置为头节点
                updates[i] = skipList.getHeader();
                //跨度初始化为跳表节点个数
                updates[i].getLevel()[i].setSpan(skipList.getLength());
            }
            //更新跳表最大层级
            skipList.setLevel(currLevel);
        }

        //创建跳表节点
        x = new SkipListNode(currLevel, score, obj);
        for (int i = 0; i < currLevel; i++) {
            //更新新节点的forward
            x.getLevel()[i].setForward(updates[i].getLevel()[i].getForward());
            //更新新节点前面的节点的forward
            updates[i].getLevel()[i].setForward(x);

            //更新新节点的span
            x.getLevel()[i].setSpan(updates[i].getLevel()[i].getSpan() - (rank[0] - rank[i]));
            //更新新节点前面节点的span
            updates[i].getLevel()[i].setSpan(rank[0] - rank[i] + 1);
        }

        //更新高层节点的span值
        for (int i = currLevel; i < skipList.getLevel(); i++) {
            updates[i].getLevel()[i].setSpan(updates[i].getLevel()[i].getSpan() + 1);
        }

        //设置后退指针
        x.setBackWord(updates[0] == skipList.getHeader() ? null : updates[0]);

        //设置新节点的下一个节点的后退指针
        if (x.getLevel()[0].getForward() != null) {
            x.getLevel()[0].getForward().setBackWord(x);
        } else {
            skipList.setTail(x);
        }

        skipList.setLength(skipList.getLength() + 1);

        return x;
    }

    //删除操作
    public int delete(SkipList skipList, Double score, String obj) {
        SkipListNode[] updates = new SkipListNode[SKIPLIST_MAXLEVEL];
        SkipListNode x = skipList.getHeader();
        Integer maxLevel = skipList.getLevel();
        SkipListLevel[] levels = x.getLevel();
        //遍历所有层，记录删除节点后需要被修改的节点到update数组
        for (int i = maxLevel - 1; i >= 0; i--) {
            SkipListNode forward = levels[i].getForward();
            while (forward != null && (forward.getScore() < score || forward.getScore().equals(score) && forward.getObj().compareTo(obj) < 0)) {
                x = forward;
                forward = x.getLevel()[i].getForward();
            }
            updates[i] = x;
        }
        x = x.getLevel()[0].getForward();
        //多个不同的obj可能有相同的score，确保匹配成功后删除
        if (x != null && score.equals(x.getScore()) && x.getObj().equals(obj)) {
            deleteNode(skipList, x, updates);
            return 1;
        }
        return 0;
    }

    private void deleteNode(SkipList skipList, SkipListNode x, SkipListNode[] updates) {

        //修改相应的指针和span值
        for (int i = 0; i < skipList.getLevel(); i++) {
            if (updates[i].getLevel()[i].getForward() == x) {
                updates[i].getLevel()[i].setSpan(updates[i].getLevel()[i].getSpan() + x.getLevel()[i].getSpan() - 1);
                updates[i].getLevel()[i].setForward(x.getLevel()[i].getForward());
            } else {
                updates[i].getLevel()[i].setSpan(updates[i].getLevel()[i].getSpan() - 1);
            }
        }

        //处理表头和表尾节点
        if (x.getLevel()[0].getForward() != null) {
            x.getLevel()[0].getForward().setBackWord(x.getBackWord());
        } else {
            skipList.setTail(x.getBackWord());
        }

        //收缩level
        while (skipList.getLevel() > 1 && skipList.getHeader().getLevel()[skipList.getLevel() - 1].getForward() == null) {
            skipList.setLevel(skipList.getLevel() - 1);
        }

        skipList.setLength(skipList.getLength() - 1);

    }

}