package fun.gengzi.codecopy.constant;

/**
 * <h1>普通(通用)常量定义</h1>
 * @author gengzi
 * @date 2020年5月23日18:42:38
 * 全局的常量定义
 */
public class Constants {

    // 【强制】不允许任何魔法值 （ 即未经预先定义的常量 ） 直接出现在代码中。
    // 【强制】常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长
    public static final String START_TIME = "start_time";
    //【强制】在 long 或者 Long 赋值时，数值后使用大写的 L，不能是小写的 l，小写容易跟数字 1 混淆，造成误解。
    public static final Long LIST_SIZE = 22L;
    // 【推荐】在常量与变量的命名时，表示类型的名词放在词尾，以提升辨识度
    // 如：终止线程数
    public static final String TERMINATED_THREAD_COUNT = "terminated_thread_count";
    // 【推荐】不要使用一个常量类维护所有常量，要按常量功能进行归类，分开维护。
    /**
     * 说明：大而全的常量类，杂乱无章，使用查找功能才能定位到修改的常量，不利于理解和维护。
     * 正例：缓存相关常量放在类 CacheConsts 下；系统配置相关常量放在类 ConfigConsts 下。
     */


}
