package fun.gengzi.codecopy.business.luckdraw.config;

import fun.gengzi.codecopy.business.luckdraw.controller.LuckdrawController;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>ThreadLocal 用户信息</h1>
 *
 * @author gengzi
 * @date 2020年9月23日15:46:09
 */
public class UserSessionThreadLocal {

    private static Logger logger = LoggerFactory.getLogger(UserSessionThreadLocal.class);

    private static ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    /**
     * 设置本地线程的用户信息
     *
     * @param user 用户信息
     */
    public static void setUser(SysUser user) {
        logger.info("当前线程name:{},设置的用户信息:{}", Thread.currentThread().getName(), user);
        userHolder.set(user);
    }

    /**
     * 返回本地线程的用户信息
     *
     * @return {@link SysUser} 用户信息
     */
    public static SysUser getUser() {
        logger.info("当前线程name:{},返回的用户信息:{}", Thread.currentThread().getName(), userHolder.get());
        return userHolder.get();
    }

    /**
     * 移除用户信息
     */
    public static void removeUser() {
        userHolder.remove();
    }

}
