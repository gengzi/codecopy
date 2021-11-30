package fun.gengzi.codecopy.business.luckdraw.aop;

/**
 * <h1>锁</h1>
 *
 * @gengzi gengzi
 * @date 2020年9月17日10:57:20
 */
public @interface LuckdrawLock {

    LockType name() default LockType.JVM_LOCK_REENTRANTLOCK;

    enum LockType {
        // JVM REENTRANTLOCK 锁
        JVM_LOCK_REENTRANTLOCK,
        // redis 分布式锁
        REDIS_LOCK
    }
}
