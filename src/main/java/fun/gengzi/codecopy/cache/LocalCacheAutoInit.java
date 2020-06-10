package fun.gengzi.codecopy.cache;


import fun.gengzi.codecopy.business.shorturl.dao.ShortUrlGeneratorTestDao;
import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>本地缓存加载</h1>
 *
 * @author gengzi
 * @date 2020年6月10日16:57:11
 */
@Service
public class LocalCacheAutoInit implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    @Autowired
    private ShortUrlGeneratorTestDao shortUrlGeneratorTestDao;

    @Autowired
    @Qualifier("localhostCacheManager")
    private CacheManager cacheManager;


    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("系统本地缓存 SysLocalCacheinit start");
        List<Shorturl> byShorturl = shortUrlGeneratorTestDao.findByShorturl("http://localhost:8089/u/1C");
        byShorturl.forEach(shorturl -> {
            logger.info("成功加载: {} ", shorturl.getId());
        });
        logger.info("系统本地缓存 SysLocalCacheinit end");

//        logger.info("从缓存中获取信息");
//        List<Shorturl> byShorturl1 = shortUrlGeneratorTestDao.findByShorturl("http://localhost:8089/u/1C");
//        byShorturl1.forEach(shorturl -> {
//            logger.info("获取信息: {} ", shorturl.getId());
//        });

        // 手动获取缓存中的数据
        Cache mycache = cacheManager.getCache("mycache");
        Cache.ValueWrapper valueWrapper = mycache.get("http://localhost:8089/u/1C");
        Object object = valueWrapper.get();
        Shorturl shorturl = null;
        if (object instanceof ArrayList) {
            Object o = ((ArrayList) object).get(0);
            if (o instanceof Shorturl) {
                shorturl = (Shorturl) o;
                logger.info("获取信息: {} ", shorturl.getId());
            }
        }
    }
}
