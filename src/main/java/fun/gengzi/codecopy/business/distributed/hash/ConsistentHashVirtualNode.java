package fun.gengzi.codecopy.business.distributed.hash;


import cn.hutool.core.util.RandomUtil;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *  一致性Hash算法，带虚拟算法
 *
 *
 * 解决数据倾斜的问题
 *
 *
 *
 *
 */
public class ConsistentHashVirtualNode {



    // 请求的ip地址
    public static String req_ip_address[] = {"11.130.12.11", "12.10.12.12", "13.130.12.122", "14.130.12.11"};
    // 服务器ip地址
    public static String rps_ip_address[] = {"77.58.22.23", "77.58.22.24", "77.58.22.25", "77.58.22.26"};

    public static void main(String[] args) {
        // 使用TreeMap 实现 顺序化 hash环
        TreeMap<Integer, String> serverHashtoIp = new TreeMap<>();
        for (int i = 0; i < rps_ip_address.length; i++) {
            // 处理虚拟节点
            // 虚拟策略，每个服务器ip分为五份
            for (int j = 0; j < 5; j++) {
                StringBuilder builder = new StringBuilder();
                builder.append(rps_ip_address[i]).append("#").append(RandomUtil.randomInt(1000,10000));
                int hashcode = hashStrategy(builder.toString().hashCode());
                serverHashtoIp.put(hashcode, rps_ip_address[i]);
            }
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
