package fun.gengzi.algorithm;

import com.google.common.collect.Range;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import org.apache.calcite.util.NumberUtil;

import java.math.RoundingMode;
import java.util.Map;

public class RangesUtils<c extends Long> {


    /**
     * 从1升序连续固定区间长度，计算当前数在那个区间范围内
     * 例如
     * [1,10] [11,20] [21,30]
     * <p>
     * 利用 当前数除以区间长度取整+1 得到当前数应该在第几个区间范围
     * 比如 8/11+1 = 1  11/11+1 = 2
     *
     * @param rangeLength 区间长度
     * @param indexValue  当前数值
     * @return 当前数在第几个区间
     */
    public Long ascOrderFixedLengthRange(Long rangeLength, Long indexValue) {
        // RoundingMode.DOWN 向 0 取整。（这是 Java 除法的行为。）
        return LongMath.divide(indexValue, rangeLength, RoundingMode.DOWN) + 1L;
    }


    public static void main(String[] args) {
        RangesUtils rangesUtils = new RangesUtils();
        Long aLong = rangesUtils.ascOrderFixedLengthRange(11L, 10L);
        System.out.println(aLong);
    }


    /**
     * 升序区间，计算当前数在那个区间范围内
     * 例如
     * [1,10] [13,20] [21,50]
     *   1       2       3
     * 使用二分查找，判断
     *
     * @param allrange   现有的区间集合 和 对应数据
     * @param indexValue 当前数值
     * @return 返回当前数在第几个区间
     */
    public Object ascOrderFixedLengthRange(Map<Range<c>, Object> allrange, c indexValue) {





        return null;
    }


    private int binarySearch(long[] arr, int low, int high, long searchNumber) {
        int mid;
        System.out.println("arr len:" + arr.length);
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] > searchNumber) {
                high = mid - 1;
            } else if (arr[mid] < searchNumber) {
                low = mid + 1;
            } else {
                return mid;//待查找的数刚好在区间边界上
            }
        }


        // 区间范围内
        System.out.println("low=" + low + ", high=" + high);

        //low > high
        if (low > arr.length - 1 || high < 0) {//待查找的数比最大的数还要大,或者比最小的数还要小
            return -1;//not found
        }
        // 区间范围外
        boolean odd = isOdd(high);
        if(odd){
            long value = arr[high+1];
            boolean contains = Range.open(arr[high],value).contains(searchNumber);
            if (contains){
                // 计算位置， 除2 + 1
                return IntMath.divide(high, 2, RoundingMode.DOWN) + 1;
            }
        }else {
            long value = arr[high-1];
            boolean contains = Range.open(value, arr[high]).contains(searchNumber);
            if (contains){
                // 计算位置， 除2 + 1
                return IntMath.divide(high, 2, RoundingMode.DOWN) + 1;
            }
        }
        return high;
    }


    public boolean isOdd(int a){
        if((a&1) != 1){   //是奇数
            return true;
        }
        return false;
    }


}
