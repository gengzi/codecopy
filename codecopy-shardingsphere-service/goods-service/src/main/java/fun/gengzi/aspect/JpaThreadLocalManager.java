package fun.gengzi.aspect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;


/**
 * <h1>用于切分新旧库本地线程变量配置</h1>
 * <p>
 * 仿照sharding jdbc 的 HintManager 实现
 *
 * @author gengzi
 * @date 2022年1月4日10:01:10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)  // 私有的空参构造方法 单例模式
public final class JpaThreadLocalManager implements AutoCloseable {

    // 本地线程
    private static final ThreadLocal<JpaThreadLocalManager> JPATHREADLOCAL = new ThreadLocal<>();

    // 存储的db 信息，可以不使用 Multimap ，使用一个String 变量就可以，直接复制过来，不再做修改
    private final Multimap<String, Comparable<?>> databaseChoice = ArrayListMultimap.create();


    /**
     * Get a new instance for {@code HintManager}.
     *
     * @return {@code HintManager} instance
     */
    public static JpaThreadLocalManager getInstance() {
        Preconditions.checkState(null == JPATHREADLOCAL.get(), "当前本地线程已经存在, 请清除后再使用");
        JpaThreadLocalManager result = new JpaThreadLocalManager();
        JPATHREADLOCAL.set(result);
        return result;
    }

    /**
     * Set sharding value for database sharding only.
     *
     * <p>The sharding operator is {@code =}</p>
     *
     * @param value sharding value
     */
    public void setDatabaseShardingValue(final Comparable<?> value) {
        databaseChoice.clear();
        databaseChoice.put("", value);
    }

    /**
     * Get database sharding values.
     *
     * @return database sharding values
     */
    public static Collection<Comparable<?>> getDatabaseShardingValues() {
        return getDatabaseShardingValues("");
    }

    /**
     * Get database sharding values.
     *
     * @param logicTable logic table
     * @return database sharding values
     */
    public static Collection<Comparable<?>> getDatabaseShardingValues(final String logicTable) {
        return null == JPATHREADLOCAL.get() ? Collections.emptyList() : JPATHREADLOCAL.get().databaseChoice.get(logicTable);
    }


    /**
     * Clear thread local for hint manager.
     */
    public static void clear() {
        JPATHREADLOCAL.remove();
    }

    /**
     * Clear sharding values.
     */
    public void clearShardingValues() {
        databaseChoice.clear();
    }

    /**
     * Judge whether hint manager instantiated or not.
     *
     * @return whether hint manager instantiated or not
     */
    public static boolean isInstantiated() {
        return null != JPATHREADLOCAL.get();
    }

    @Override
    public void close() {
        clear();
    }
}
