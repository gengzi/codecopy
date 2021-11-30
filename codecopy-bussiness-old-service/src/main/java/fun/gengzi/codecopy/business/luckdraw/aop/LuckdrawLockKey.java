package fun.gengzi.codecopy.business.luckdraw.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * <h1>锁-key</h1>
 * <p>
 * 指定锁的key字段
 *
 * @gengzi gengzi
 * @date 2020年9月17日13:46:38
 */
@Target(ElementType.PARAMETER)
public @interface LuckdrawLockKey {

}
