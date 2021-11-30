package fun.gengzi.codecopy.suanfa;

import org.junit.Test;

public class CutRopeTest {

    /**
     *
     */
    @Test
    public void fun01() {

    }

    public int cutRope(int target) {

        int[] ints = new int[target + 1];

        ints[0] = 1;
        ints[1] = 1;
        
        if(target == 2){
            return 2;
        }
        if(target == 3){
            return 3;
        }

        for (int i = 1; i < target; i++) {

            ints[i] = target - i;

            
        }



        // f(max) = f(i)*f(target-i)
        // 比较每次的最大值，返回
        // 需要保存每次 乘积的值临时值



        return 0;

    }
}
