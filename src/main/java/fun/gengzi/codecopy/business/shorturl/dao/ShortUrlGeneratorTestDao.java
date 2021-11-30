package fun.gengzi.codecopy.business.shorturl.dao;

import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>测试 jpa 相关</h1>
 * Spring JPA查询，JPA 根据方法名字查询详细介绍
 * https://www.sojson.com/blog/295.html
 *
 * @author gengzi
 * @date 2020年6月10日14:28:37
 */
@Repository
public interface ShortUrlGeneratorTestDao extends JpaRepository<Shorturl, Long> {


    @Cacheable(cacheManager="localhostCacheManager",value="mycache",key="#shorurl")
    // jpa 可以根据 方法名称构建sql 语句查询
    //select shorturl0_.id as id1_0_, shorturl0_.createtime as createti2_0_, shorturl0_.isoverdue as isoverdu3_0_, shorturl0_.longurl as longurl4_0_, shorturl0_.shorturl as shorturl5_0_, shorturl0_.termtime as termtime6_0_, shorturl0_.updatetime as updateti7_0_ from shorturl shorturl0_ where shorturl0_.shorturl=?
    List<Shorturl> findByShorturl(String shorurl);

}
