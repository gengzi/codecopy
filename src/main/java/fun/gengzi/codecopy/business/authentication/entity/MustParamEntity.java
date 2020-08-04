package fun.gengzi.codecopy.business.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * <h1>必传参数</h1>
 *
 * @author gengzi
 * @date 2020年8月4日15:03:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MustParamEntity {
    // 签名
    private String sign;
    // 时间戳
    private String timestamp;
    // 回调url
    private String notify_url;
    // 请求参数的集合
    private String biz_content;


    /**
     * 对象转map
     * 并按照第一个字符的键值 ASCII 码递增排序（字母升序排序），
     * 如果遇到相同字符则按照第二个字符的键值 ASCII 码递增排序，以此类推。
     *
     * @param mustParamEntity
     * @return
     */
    public TreeMap<String, String> mustParamEntityToMap(MustParamEntity mustParamEntity) {
        final TreeMap<String, String> treeMap = new TreeMap<>();
        final HashMap<String, String> map = new HashMap<>();
        final Field[] fields = mustParamEntity.getClass().getDeclaredFields();
        Stream.of(fields).forEach((field) -> {
            String name = field.getName();
            if ("sign".equals(name)) {
                return;
            }
            boolean accessible = field.isAccessible();
            // 修改访问控制权限
            field.setAccessible(true);
            try {
                Object o = field.get(mustParamEntity);
                if (o != null && StringUtils.isNotBlank(o.toString().trim())) {
                    map.put(name, o.toString().trim());
                    // 恢复访问控制权限
                    field.setAccessible(accessible);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }

//    public static void main(String[] args) {
//        MustParamEntity mustParamEntity = new MustParamEntity();
//        mustParamEntity.setNotify_url("http://localhost:8089/api/v2/payMoneyResponse");
//        mustParamEntity.setTimestamp(String.valueOf(System.currentTimeMillis()));
//        TreeMap<String, String> treeMap = mustParamEntity.mustParamEntityToMap(mustParamEntity);
//        treeMap.forEach((k, v) -> {
//            System.out.println(k + ":" + v);
//        });
//
//    }


}
