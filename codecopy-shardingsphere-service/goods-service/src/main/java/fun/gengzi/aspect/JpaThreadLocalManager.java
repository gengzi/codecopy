package fun.gengzi.aspect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JpaThreadLocalManager implements AutoCloseable {

    private static final ThreadLocal<JpaThreadLocalManager> JPATHREADLOCAL = new ThreadLocal<>();

    private final Multimap<String, Comparable<?>> databaseChoice = ArrayListMultimap.create();


    /**
     * Get a new instance for {@code HintManager}.
     *
     * @return {@code HintManager} instance
     */
    public static JpaThreadLocalManager getInstance() {
        Preconditions.checkState(null == JPATHREADLOCAL.get(), "存在数据, 请清除");
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
