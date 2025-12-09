package fun.gengzi.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

public class IsValid {

    public boolean isValid(String s) {
        // 判断长度是否为奇数，直接失败
        char[] charStrs = s.toCharArray();
        if (charStrs.length % 2 != 0) {
            return false;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(")", "(");
        map.put("]", "[");
        map.put("}", "{");
        // 需要通过栈来判断，栈顶元素是否和下一个元素对应，如果对应继续，否则失败
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < charStrs.length; i++) {
            if(map.containsKey(String.valueOf(charStrs[i]))){
                String peek = linkedList.peek();
                if (peek.equals(map.get(String.valueOf(charStrs[i])))) {
                    linkedList.pop();
                }else{
                    return false;
                }
            }else{
                linkedList.push( String.valueOf(charStrs[i]));
            }

            // 获取栈顶元素是否对应
        }
        return linkedList.isEmpty();

    }


    public static void main(String[] args) {
        boolean valid = new IsValid().isValid("()");
        System.out.println(valid);


    }
}
