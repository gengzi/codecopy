package fun.gengzi.codecopy.business.jvm.gc;


/**
 * 设置最小堆内存和最大堆内存都是20M，年轻代内存设置10M，然后进行内存分配，来观察Gc日志
 * <p>
 * 年轻待，eden区域与 survivor0  和 survivor1的比例分配为 8:1:1
 */
public class GcTestCode01 {

    public static final int _1M = 1024 * 1024;

    /**
     *
     * -Xms20M -Xmx20M -Xmn10M -XX:UserParNewGC -XX:+PrintGCDeatils -XX:SurvivorRatio=8
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        byte[] bytes1 = new byte[2 * _1M];
        byte[] bytes2 = new byte[2 * _1M];
        byte[] bytes3 = new byte[2 * _1M];
        byte[] bytes4 = new byte[4 * _1M]; // Minor GC 年轻的GC
    }


}




