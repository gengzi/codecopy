package fun.gengzi.codecopy.business.jvm.gc;


/**
 * <h1>Gc日志分析</h1>
 * <p>
 * <p>
 * -Xms20M -Xmx20M -Xmn10M -XX:+UseParNewGC -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * 设置最小堆内存和最大堆内存都是20M，年轻代内存设置10M，然后进行内存分配，来观察Gc日志
 * <p>
 * 年轻待，eden区域与 survivor0  和 survivor1的比例分配为 8:1:1
 */
public class GcTestCode01 {

    public static final int _1M = 1024 * 1024;


    public static void main(String[] args) {
        byte[] bytes1, bytes2, bytes3, bytes4;
        bytes1 = new byte[2 * _1M];
        bytes2 = new byte[2 * _1M];
        bytes3 = new byte[2 * _1M];
        bytes4 = new byte[4 * _1M];  // 触发 Minor GC
    }
}
