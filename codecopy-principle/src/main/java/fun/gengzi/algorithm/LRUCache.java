package fun.gengzi.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class LRUCache {
    Map<Integer, Integer> map = new HashMap();
    LinkedList<Integer> values = new LinkedList();
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            Integer i = Integer.valueOf(key);
            values.remove(i);
            values.add(i);
            return map.get(key);
        }
        return -1;
    }

    public synchronized void put(int key, int value) {
        // 判断当前列表是否存在该元素，存在该元素进行值的替换，并且把key移动到链表尾，更新 map

        if (values.size() >= capacity) {
            // 插入链表尾，更新map
            if(map.containsKey(key)){
                map.put(key, value);
                values.remove(key);
                values.add(key);
            }else{
                map.put(key, value);
                Integer key1 = values.pollFirst();
                map.remove(key1);
            }

            // 如果不存在，缓存列表已满，开始剔除最久没有使用的元素，将链表头移除，更新map


        } else {
            if(map.containsKey(key)){
                map.put(key, value);
                values.remove(key);
                values.add(key);
            }else{
                map.put(key, value);
                values.add(key);
            }


        }

    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */