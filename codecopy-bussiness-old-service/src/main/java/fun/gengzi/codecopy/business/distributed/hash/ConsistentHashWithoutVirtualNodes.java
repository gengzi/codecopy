package fun.gengzi.codecopy.business.distributed.hash;


import java.util.*;

/**
 * 一致性Hash 算法（无虚拟节点）
 * <p>
 * <p>
 * 解决上述的问题，当某台服务器宕机，仅迁移一部分请求到新的服务器上
 * <p>
 * 一致性Hash 算法，提出了一个 Hash环  ，将服务器分散到 这个环上，请求过来的ip，在这个顺时针找最近的服务器节点
 * <p>
 * <p>
 * 0-2三十二次方-1
 * <p>
 * 0-4,294,967,295     index    10    20   1000  3000
 * <p>
 * hashcode     5  10  20  40    将顺时针 转为一个 short 排序的
 * <p>
 * <p>
 * 结果输出：
 * <p>
 * 请求：10.130.12.11路由到了第77.58.22.26台服务器
 * 请求：10.10.12.12路由到了第77.58.22.26台服务器
 * 请求：10.130.12.122路由到了第77.58.22.26台服务器
 * 请求：10.130.12.11路由到了第77.58.22.26台服务器
 * <p>
 * <p>
 * 此演示案例： 会出现数据倾斜的问题
 */
public class ConsistentHashWithoutVirtualNodes {


    // 请求的ip地址
    public static String req_ip_address[] = {"10.130.12.11", "10.10.12.12", "10.130.12.122", "10.130.12.11"};
    // 服务器ip地址
    public static String rps_ip_address[] = {"77.58.22.23", "77.58.22.24", "77.58.22.25", "77.58.22.26"};

    public static void main(String[] args) {
        // 使用TreeMap 实现 顺序化 hash环
        TreeMap<Integer, String> serverHashtoIp = new TreeMap<>();
        for (int i = 0; i < rps_ip_address.length; i++) {
            int hashcode = hashStrategy(rps_ip_address[i].hashCode());
            serverHashtoIp.put(hashcode, rps_ip_address[i]);
        }

        for (int i = 0; i < req_ip_address.length; i++) {
            int index = hashStrategy(req_ip_address[i].hashCode());
            // 使用 tailMap  找到当前比当前key 大的 hashmap
            SortedMap<Integer, String> clockwiseMap = serverHashtoIp.tailMap(index);
            if (clockwiseMap.isEmpty()) {
                // 说明当前请求ip 后已经没有服务器了。直接找第一个服务器
                System.out.println("请求：" + req_ip_address[i] + "路由到了第" + serverHashtoIp.get(serverHashtoIp.firstKey()) + "台服务器");
            } else {
                // 顺时针查找第一个key
                System.out.println("请求：" + req_ip_address[i] + "路由到了第" + clockwiseMap.get(clockwiseMap.firstKey()) + "台服务器");
            }
        }
    }


    /**
     * hash 算法
     *
     * @return 获取数组的一个位置
     */
    public static int hashStrategy(int hashCode) {
        // hashcode 可能为负数，需要取绝对值
        return Math.abs(hashCode);
    }




}
