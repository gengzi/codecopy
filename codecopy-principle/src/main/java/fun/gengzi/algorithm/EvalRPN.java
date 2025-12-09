package fun.gengzi.algorithm;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class EvalRPN {


    public static void main(String[] args) {
        int i = new EvalRPN().evalRPN(new String[]{"4", "13", "5", "/", "+"});
        System.out.println(i);
    }

    /**
     * 输入：tokens = ["2","1","+","3","*"]
     * 输出：9
     * 解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
     *
     * @param tokens
     * @return
     */

    public int evalRPN(String[] tokens) {
        // 利用栈的思想，遇到数字入栈，遇到符号出栈计算，最后栈中只有一个数字，就是结果
        List<String> list = Arrays.asList("+", "-", "*", "/");
        Deque<String> deque = new LinkedList<>();
        for (int i = 0; i < tokens.length; i++) {
            if (!list.contains(tokens[i])) {
                // 数字，入栈
                deque.push(tokens[i]);
            } else if (list.contains(tokens[i])) {
                // 符号
                // 是数字，继续
                if ("+".equals(tokens[i])) {
                    deque.push(Integer.valueOf(deque.pop()) + Integer.valueOf(deque.pop()) + "");
                }
                if ("-".equals(tokens[i])) {
                    String pop = deque.pop();
                    String pop1 = deque.pop();
                    deque.push(Integer.valueOf(pop1) - Integer.valueOf(pop) + "");
                }
                if ("*".equals(tokens[i])) {
                    deque.push(Integer.valueOf(deque.pop()) * Integer.valueOf(deque.pop()) + "");
                }
                if ("/".equals(tokens[i])) {
                    String pop = deque.pop();
                    String pop1 = deque.pop();
                    deque.push(Integer.valueOf(pop1) / Integer.valueOf(pop) + "");
                }

            }


        }
        return Integer.valueOf(deque.pop());
    }

}
