package fun.gengzi.algorithm;

import com.google.common.collect.Range;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
import java.util.*;


/**
 * <h1>区间工具类</h1>
 * <p>
 * <p>
 * 主要用于计算，区间范围对应的下标
 * <p>
 * 注意：** 此类实现的方法，都未校验手动赋值的区间是否正确 ** 请确保赋值的区间不存在重叠和乱序
 *
 * @author gengzi
 * @date 2021年12月8日16:38:22
 */
@Slf4j
public class RangesUtils<c extends Long, t extends Object> {


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

    /**
     * 计算当前数在那个区间范围内
     * <p>
     * 比如 [1,10] [13,20] [21,50] 判断 17 在第几个区间范围内
     * 使用二分查找，判断当前值是否在区间范围内
     *
     * @param rangeArr   排序后的数组，用于二分查找
     * @param indexValue 需求判断的当前数值
     * @return 返回当前数在第几个区间(从1开始)
     */
    public int ascOrderFixedLengthRange(c[] rangeArr, c indexValue) {
        log.info("create rangearr :{}", rangeArr);
        // 通过二分查找，计算区间下标
        int sectionIndex = binarySearch(rangeArr, indexValue);
        if (sectionIndex == -1) {
            throw new RuntimeException("选择区间失败");
        }
        return sectionIndex;
    }


    /**
     * 将构造的范围map 转换为对象数组
     *
     * @param allrange 区间map集合
     * @return {@link Entity} 对象数据
     */
    public Entity mapToArr(LinkedHashMap<Range<c>, t> allrange) {
        // list 记录有追加顺序
        final ArrayList<Long> allrangeList = new ArrayList<>(allrange.size() * 2);
        final ArrayList<Object> indexValObjs = new ArrayList<>(allrange.size());
        // 将上界下界生成升序数组
        allrange.keySet().stream().forEach(
                range -> {
                    allrangeList.add(range.lowerEndpoint());
                    allrangeList.add(range.upperEndpoint());
                    indexValObjs.add(allrange.get(range));
                }
        );
        Entity entity = new Entity();
        entity.setAllRangep(allrangeList.toArray(new Long[]{}));
        entity.setVal(indexValObjs.toArray(new Object[]{}));
        return entity;
    }


    /**
     * 升序区间，计算当前数在那个区间范围内，对应的对象值
     * 例如
     * [1,10] [13,20] [21,50]
     * A       B       C
     * 使用二分查找，判断 33 在 C 的区间中
     * <p>
     * 这里定义必须为 LinkedHashMap 用来设值的顺序
     *
     * @param allrange   区间集合，此区间必须升序赋值
     * @param indexValue 需求判断的当前数值
     * @return 返回当前数在第几个区间(从1开始), 映射的数据
     */
    public Object ascOrderFixedLengthRange(LinkedHashMap<Range<c>, t> allrange, c indexValue) {
        Entity entity = mapToArr(allrange);
        log.info("create rangearr info :{}", entity);
        return ascOrderFixedLengthRange(entity, indexValue);
    }

    /**
     * 通过entity对象来执行二分查找，确定对比数在那个区间中
     *
     * @param entity     数据集合
     * @param indexValue 需要判断对比的值
     * @return 返回当前数在第几个区间(从1开始), 映射的数据
     */
    public Object ascOrderFixedLengthRange(Entity entity, c indexValue) {
        // 通过二分查找，计算区间下标
        int sectionIndex = binarySearch(entity.getAllRangep(), indexValue);
        if (sectionIndex == -1) {
            throw new RuntimeException("选择区间失败");
        }
        return entity.getVal()[sectionIndex - 1];
    }

    /**
     * 二分查找，计算区间下标
     *
     * @param arr          排序数组
     * @param searchNumber 比较值
     * @return 区间下标 -1 为匹配区间失败
     */
    private int binarySearch(Long[] arr, long searchNumber) {
        // 开始坐标
        int low = 0;
        int high = arr.length - 1; // 结束坐标
        int mid; // 中间值
        log.info("arr length:{}", arr.length);
        while (low <= high) {
            if (low == high) {
                // 出现下界坐标与上届坐标重合，就是执行到最后一次的判断
                mid = low;
                // 最后一次特殊处理，除了判断当前数在区间值上，其他情况都不做处理
                if (arr[mid] > searchNumber) {
                    break;
                } else if (arr[mid] < searchNumber) {
                    break;
                } else {
                    return IntMath.divide(mid, 2, RoundingMode.DOWN) + 1;
                }
            } else {
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
        // 区间范围判断，当值不出现区间边界上，那么存在俩种情况
        // 情况1：在某个区间范围内
        // 情况2：不在区间范围内
        // 判断策略，当前low 和 high 是相同值，已知每组区间坐标为两个值，上界位于数组下标必定是偶数，下界必定是奇数
        // 计算当前停下的数组下标是偶数还是奇数.如果为偶数，说明当前下标值属于区间的下界位置。那么+1 就是区间的上界对应的数组下标
        // 根据上述原理，可以计算出现在与判断当前数，最近的区间判断。在此区间判定，是否在区间范围中。
        //
        long lower = -1;
        long upper = -1;
        // 判断当前下标是奇数还是偶数
        if (isOdd(high)) {
            // 偶数，说明当前下标值属于区间的下界位置
            // 当前坐标+1,则是区间的上界
            lower = arr[high];
            upper = arr[high + 1];
        } else {
            // 奇数，说明当前下标值属于区间的上界位置
            // 当前坐标-1.则是区间的下界
            lower = arr[high - 1];
            upper = arr[high];
        }
        // 这里使用closed 其实没有必要，在上面的判断包含了区间值的判断
        if (Range.open(lower, upper).contains(searchNumber)) {
            // 每个区间，占用两个下标，利用当前下标值 整除 2 得到倍数，因为倍数最小为 0 ，进行+1 就可以获取下标
            return IntMath.divide(high, 2, RoundingMode.DOWN) + 1;
        }
        // 返回 -1 表示，对比数不存在任何的区间范围中
        return -1;
    }


    /**
     * 判断奇数还是偶数
     *
     * @param num 判断的数字
     * @return true 偶数 false 奇数
     */
    public static boolean isOdd(int num) {
        if ((num & 1) != 1) {   //是奇数
            return true;
        }
        return false;
    }


    /**
     * 范围区间数据转化为数组集合实体
     *
     * @author gengzi
     * @date 2021年12月8日14:48:34
     */
    @Data
    public static class Entity {
        // map映射排序数组 比如 {1,20,30,40,50,60}
        private Long[] allRangep;
        // 每个区间，对应的信息 比如 {"A","B","C"}
        private Object[] val;
    }


}
