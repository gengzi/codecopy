package fun.gengzi.algorithm;

import java.util.LinkedList;

public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        // 使用滑动窗口，不断扩大窗口，并计数。如果遇到重复的就缩小窗口，并重新开始计数
        char[] charArray = s.toCharArray();
        LinkedList<Character> list = new LinkedList();
        int size = 0;
        for (int i = 0; i < charArray.length; i++) {
            if(list.contains(charArray[i])){
                size = Math.max(size, list.size());
                // 清空，并计数
                char remove = list.removeFirst();
                while (remove != charArray[i]) {
                    remove =  list.removeFirst();
                }

            }
            list.add(charArray[i]);
        }
        return size;
    }
}
