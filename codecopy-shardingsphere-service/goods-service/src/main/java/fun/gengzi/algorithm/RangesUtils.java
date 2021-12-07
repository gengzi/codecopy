package fun.gengzi.algorithm;

import com.google.common.collect.Range;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.util.NumberUtil;

import java.math.RoundingMode;
import java.util.*;

@Slf4j
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

        HashMap<Range<Long>, Long> hashMap = new LinkedHashMap<>();
        Range<Long> open1 = Range.closed(1L, 10L);
        Range<Long> open2 = Range.closed(11L, 31L);
        Range<Long> open3 = Range.closed(41L, 43L);
        Range<Long> open4 = Range.closed(50L, 100L);

        hashMap.put(open1, 1L);
        hashMap.put(open2, 2L);
        hashMap.put(open3, 3L);
        hashMap.put(open4, 4L);
        Object o = rangesUtils.ascOrderFixedLengthRange(hashMap, 67L);
        System.out.println(o);

    }


    /**
     * 升序区间，计算当前数在那个区间范围内
     * 例如
     * [1,10] [13,20] [21,50]
     * 1       2       3
     * 使用二分查找，判断
     *
     * @param allrange   现有的区间集合 和 对应数据
     * @param indexValue 当前数值
     * @return 返回当前数在第几个区间
     */
    public Object ascOrderFixedLengthRange(Map<Range<c>, Object> allrange, c indexValue) {
        final ArrayList<Long> allrangeList = new ArrayList<>(allrange.size() * 2);
        final ArrayList<Object> indexs = new ArrayList<>(allrange.size());
        // 将上界下界生成升序数据
        allrange.keySet().stream().forEach(
                range -> {
                    allrangeList.add(range.lowerEndpoint());
                    allrangeList.add(range.upperEndpoint());
                    indexs.add(allrange.get(range));
                }
        );
        Long[] rangeArr = allrangeList.toArray(new Long[]{});
        // 获取对应坐标
        int i = binarySearch(rangeArr,  indexValue);
        if(i == -1){
            throw  new RuntimeException("选择区间失败");
        }
        return indexs.get(i - 1);
    }


    private int binarySearch(Long[] arr,long searchNumber) {
        int low = 0;
        int high = arr.length-1;
        int mid;
        log.info("arr length:{}",arr.length);
        while (low <= high) {
            if(low == high){
                mid = low;
                // 最后一次
                if (arr[mid] > searchNumber) {
                    break;
                } else if (arr[mid] < searchNumber) {
                    break;
                } else {
                    return IntMath.divide(mid, 2, RoundingMode.DOWN) + 1;
                }
            }else{
                mid = (low + high) / 2;
                if (arr[mid] > searchNumber) {
                    high = mid - 1;
                } else if (arr[mid] < searchNumber) {
                    low = mid + 1;
                } else {
                    return IntMath.divide(mid, 2, RoundingMode.DOWN) + 1; //待查找的数刚好在区间边界上
                }
            }
        }
        log.info("");
        //low > high
        if (low > arr.length - 1 || high < 0) {//待查找的数比最大的数还要大,或者比最小的数还要小
            return -1;//not found
        }
        // 区间范围外
        boolean odd = isOdd(high);
        if (odd) {
            long value = arr[high + 1];
            boolean contains = Range.open(arr[high], value).contains(searchNumber);
            if (contains) {
                // 计算位置， 除2 + 1
                return IntMath.divide(high, 2, RoundingMode.DOWN) + 1;
            }
        } else {
            long value = arr[high - 1];
            boolean contains = Range.open(value, arr[high]).contains(searchNumber);
            if (contains) {
                // 计算位置， 除2 + 1
                return IntMath.divide(high, 2, RoundingMode.DOWN) + 1;
            }
        }
        return -1;
    }


    /**
     * 判断奇数还是偶数
     *
     * @param num 判断的数字
     * @return
     */
    public boolean isOdd(int num) {
        if ((num & 1) != 1) {   //是奇数
            return true;
        }
        return false;
    }


}
