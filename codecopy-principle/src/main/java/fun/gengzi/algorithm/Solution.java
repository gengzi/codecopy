package fun.gengzi.algorithm;

import java.util.LinkedList;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // 使用双端队列，队列头存储最大值的数组下标， 锁定在k 个范围内
        LinkedList<Integer> queue = new LinkedList();
        int [] result = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length ; i++) {
            if(queue.size() >0 && queue.getFirst() < i - k + 1){
                queue.removeFirst();
            }

            while(queue.size() > 0  && nums[queue.getLast()] <= nums[i]){
                queue.removeLast();
            }
            queue.add(i);

            if(i >= k -1){
                result[i - k + 1] = nums[queue.getFirst()];

            }


        }
        return result;


    }
}