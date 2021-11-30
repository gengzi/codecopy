package fun.gengzi.codecopy.business.redis.controller.skiplist;

import java.util.Random;


public class SkipList {

    public int level = 0;
    public SkipListNode top = null;

    public SkipList() {
        this(4);
    }

    //跳跃表的初始化
    public SkipList(int level) {
        this.level = level;
        SkipListNode skipListNode = null;
        SkipListNode temp = top;
        SkipListNode tempDown = null;
        SkipListNode tempNextDown = null;
        int tempLevel = level;
        while(tempLevel -- != 0){
            skipListNode = createNode(Integer.MIN_VALUE);
            temp = skipListNode;
            skipListNode = createNode(Integer.MAX_VALUE);
            temp.setNext(skipListNode);
            temp.setDownNext(tempDown);
            temp.getNext().setDownNext(tempNextDown);
            tempDown = temp;
            tempNextDown = temp.getNext();
        }
        top = temp;
    }

    //随机产生数k，k层下的都需要将值插入
    public int randomLevel(){
        int k = 1;
        while(new Random().nextInt()%2 == 0){
            k ++;
        }
        return k > level ? level : k;
    }

    //查找
    public SkipListNode find(int value){
        SkipListNode node = top;
        while(true){
            while(node.getNext().getValue() < value){
                node = node.getNext();
            }
            if(node.getDownNext() == null){
                //返回要查找的节点的前一个节点
                return node;
            }
            node = node.getDownNext();
        }
    }

    //删除一个节点
    public boolean delete(int value){
        int tempLevel = level;
        SkipListNode skipListNode = top;
        SkipListNode temp = skipListNode;
        boolean flag = false;
        while(tempLevel -- != 0){
            while(temp.getNext().getValue() < value){
                temp = temp.getNext();
            }
            if(temp.getNext().getValue() == value){
                temp.setNext(temp.getNext().getNext());
                flag = true;
            }
            temp = skipListNode.getDownNext();
        }
        return flag;
    }

    //插入一个节点
    public void insert(int value){
        SkipListNode skipListNode = null;
        int k = randomLevel();
        SkipListNode temp = top;
        int tempLevel = level;
        SkipListNode tempNode = null;
        //当在第n行插入后，在第n - 1行插入时需要将第n行backTempNode的DownNext域指向第n - 1的域
        SkipListNode backTempNode = null;
        int flag = 1;
        while(tempLevel-- != k){
            temp = temp.getDownNext();
        }

        tempLevel++;
        tempNode = temp;
        //小于k层的都需要进行插入
        while(tempLevel-- != 0){
            //在第k层寻找要插入的位置
            while(tempNode.getNext().getValue() < value){
                tempNode = tempNode.getNext();
            }
            skipListNode = createNode(value);
            //如果是顶层
            if(flag != 1){
                backTempNode.setDownNext(skipListNode);
            }
            backTempNode = skipListNode;
            skipListNode.setNext(tempNode.getNext());
            tempNode.setNext(skipListNode);
            flag = 0;
            tempNode = tempNode.getDownNext();
        }
    }

    //创建一个节点
    private SkipListNode createNode(int value){
        SkipListNode node =  new SkipListNode();
        node.setValue(value);
        return node;
    }
}