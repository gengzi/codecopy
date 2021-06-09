package fun.gengzi.codecopy.business.distributed.hash;


/**
 * 通用Hash 算法
 * <p>
 * <p>
 * hash 策略 除模取余 法
 * <p>
 * <p>
 * 模拟请求ip 访问了0-5 这几台服务的，路由策略
 * 根据 ip 地址，获取hashcode
 * <p>
 * 当一台服务器宕机，会出现访问失败，所以需要修正。大量的请求，将会转移到另外的服务器上，进而丢失会话信息
 */
public class GeneralHash {

    // 请求的ip地址
    public static String ip_address[] = {"10.130.12.11", "10.130.12.12", "10.130.12.13", "10.130.12.11"};
    // 服务器台数
    public static int serverNum = 5;

    public static void main(String[] args) {
        for (int i = 0; i < ip_address.length; i++) {
            int index = hashStrategy(5, ip_address[i].hashCode());
            System.out.println("请求：" + ip_address[i] + "路由到了第" + (index + 1) + "台服务器");
        }
    }


    /**
     * hash 算法
     *
     * @return 获取数组的一个位置
     */
    public static int hashStrategy(int severNum, int hashCode) {
        // hashcode 可能为负数，需要取绝对值
        return Math.abs(hashCode) % severNum;
    }

}
