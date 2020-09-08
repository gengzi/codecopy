package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbActivity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * <h1>活动dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月8日13:57:00
 */
@Repository
public interface ActivityDao extends JpaRepository<TbActivity, String> {

    /**
     * 获取当前时间有效的活动列表，并将活动信息缓存到本地，设置五秒钟失效。
     *
     * @param currentTime 当前时间
     * @return {@link List<TbActivity>} 活动信息列表
     */
    @Cacheable(cacheManager = "localhostCacheManager", value = "LUCKDRAW_LOCALCACHE", key = "getMethodName()")
    @Query(" select t from TbActivity t where  isInvalid  = 0 and  ?1 >= starttime and  ?1 <= endtime ")
    List<TbActivity> getEffectiveActivityInfo(Date currentTime);


}
