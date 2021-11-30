package fun.gengzi.codecopy.business.datastructure;

import java.util.HashMap;

/**
 * <h1>hashMap简单实现</h1>
 */
public class MyHashMap {

    public static int arrLength = 8;


    // 数组
    ListNode[] listNode = new ListNode[arrLength];


    /**
     * 设置key 和 value
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {

        // 计算数组下标
        int index = Math.abs(key.hashCode()) % arrLength;
        // 根据数组下标做判断
        ListNode listNode = this.listNode[index];
        // 判断当前listNode 是否为null
        if (listNode == null) {
            // 说明当前数组下标没有存放单项链表
            ListNode listNode1 = new ListNode();
            listNode1.head = new Node(key, value, null);
            this.listNode[index] = listNode1;
        } else {
            // 说明当前数组下有存放单项链表
            Node head = listNode.head;
            Node temp = head;


            // 循环该链表，对比每个节点的key是否一致，一致则替换当前key的value ，如果循环结束，没有匹配到，则使用尾链法加入新的节点
            while (temp != null) {
                String nodekey = head.getKey();
                if (nodekey.equals(key)) {
                    temp.setValue(value);
                    break;
                } else {
                    if (temp.getNext() == null) {
                        // 说明已经循环到结束
                        Node node = new Node(key, value, null);
                        temp.setNext(node);
                        break;
                    } else {
                        // 不一致，继续循环
                        temp = temp.getNext();
                    }
                }
            }
        }
    }


    /**
     * 根据key 获取value
     *
     * @param key
     */
    public String get(String key) {
        // 计算数组下标
        int index = Math.abs(key.hashCode()) % arrLength;
        // 根据数组下标做判断
        ListNode listNode = this.listNode[index];
        // 判断当前节点上是否有值
        if (listNode == null) {
            // 不存在值，直接返回
            return "";
        } else {
            // 存在单项链表，开始循环取值
            Node head = listNode.head;
            Node temp = head;
            while (temp != null) {
                String nodeKey = temp.getKey();
                if (nodeKey.equals(key)) {
                    // 取当前值
                    return temp.getValue();
                } else {
                    // 下一个节点判断
                    temp = temp.getNext();
                    if (temp == null) {
                        // 无下一个节点，没找到
                        return "";
                    }
                }
            }
        }
        return "";
    }

    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.put("key1", "value1");
        myHashMap.put("key2", "value2");
        myHashMap.put("key3", "value3");
        myHashMap.put("key4", "value4");
        myHashMap.put("key5", "value5");
        myHashMap.put("key6", "value6");
        myHashMap.put("key6", "value7");
        myHashMap.put("key8", "value8");
        myHashMap.put("key9", "value9");
        myHashMap.put("key10", "value10");
        myHashMap.put("key11", "value11");
        myHashMap.put("key12", "value12");
        myHashMap.put("key13", "value13");
        myHashMap.put("key14", "value14");
        myHashMap.put("key15", "value15");
        myHashMap.put("key16", "value16");
        myHashMap.put("key17", "value17");
        myHashMap.put("key18", "value18");
        myHashMap.put("key19", "value19");


        // 获取值
        String key6 = myHashMap.get("key6");
        String key611 = myHashMap.get("key611");
        String key19 = myHashMap.get("key19");
        System.out.println("key6:" + key6);
        System.out.println("key19:" + key19);
        System.out.println("key611:" + key611);


    }


}
