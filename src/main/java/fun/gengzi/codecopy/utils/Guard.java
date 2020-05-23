package fun.gengzi.codecopy.utils;

/**
 * 值有效性验证类
 *
 * @Author: 刘锐
 * Time: 2018/4/8 9:15
 */
public final class Guard {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断金额是否在有效范围内
     *
     * @param amount 金额
     * @return 判断金额是否在有效范围内
     */
    public static boolean validateAmount(double amount) {
        return range(amount, 0.01D, 1000000D);
    }

    /**
     * 判断红包金额是否在有效范围内
     * @param amount 红包金额
     * @return
     */
    public  static  boolean validateRedEnvelopeAmount(double amount){
            return range(amount, 0.01D, 200D);
    }

    /**
     * 判断转账金额是否在有效范围内
     * @param amount 转账金额
     * @return
     */
    public  static  boolean validateTransferAmount(double amount){
        return range(amount, 0.01D, 2000D);
    }

    /**
     * 判断数量是否在有效范围内
     *
     * @param count 数量
     * @return 数量是否在有效范围内
     */
    public static boolean validateCount(int count) {
        return range(count, 1, 1000000);
    }

    /**
     * 判断值是否在特定的范围内
     *
     * @param data  值
     * @param begin 起始值
     * @param end   截止值
     * @return 如果在范围内返回true, 否则返回false
     */
    public static boolean range(int data, int begin, int end) {
        return data >= begin && data <= end;
    }

    /**
     * 判断值是否在特定的范围内
     *
     * @param data  值
     * @param begin 起始值
     * @param end   截止值
     * @return 如果在范围内返回true, 否则返回false
     */
    public static boolean range(double data, double begin, double end) {
        return data >= begin && data <= end;
    }
}
