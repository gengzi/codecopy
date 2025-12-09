package fun.gengzi.algorithm;

import java.util.HashMap;

public class TwoSum {


    /**
     * 使用hash表
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> numMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            boolean b = numMap.containsKey(target - nums[i]);
            if (b) {
                return new int[]{numMap.get(target - nums[i]), i};
            } else {
                numMap.put(nums[i], i);
            }
        }

        return new int[]{};

    }

}
