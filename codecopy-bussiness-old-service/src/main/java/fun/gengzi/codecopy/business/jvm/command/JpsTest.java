package fun.gengzi.codecopy.business.jvm.command;

import cn.hutool.core.util.RuntimeUtil;
import lombok.SneakyThrows;

public class JpsTest {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("JPS 测试");

        System.out.println("jps -p 进程id");
        System.out.println("jps -m 输出主类名，打印main方法入参");
        System.out.println("jps -v 输出主类名，jvm参数");
        Thread.sleep(20000000);
    }

}
