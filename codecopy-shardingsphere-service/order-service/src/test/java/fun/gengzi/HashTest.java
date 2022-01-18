package fun.gengzi;

import cn.hutool.core.util.HashUtil;

/**
 * <h1> </h1>
 *
 * @author Administrator
 * @date 2022/1/18 14:41
 */
public class HashTest {

    /**
     * 2 的三十二次方  4294967296
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        int i = HashUtil.fnvHash("1234");
        System.out.println(i);
    }


}
