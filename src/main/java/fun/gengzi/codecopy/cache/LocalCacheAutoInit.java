package fun.gengzi.codecopy.cache;


import fun.gengzi.codecopy.sys.entity.SysPermission;
import fun.gengzi.codecopy.sys.service.SysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>本地缓存加载</h1>
 * 在spring 容器加载后，初始化缓存数据
 *
 * @author gengzi
 * @date 2020年6月10日16:57:11
 */
@Service
public class LocalCacheAutoInit implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(LocalCacheAutoInit.class);

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    @Qualifier("localhostCacheManager")
    private CacheManager cacheManager;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        


//        logger.info("系统本地缓存 SysLocalCacheinit start");
//        sysPermissionService.getAllInfoByPid(310512);
//        sysPermissionService.getInfoByType("F");
//        logger.info("系统本地缓存 SysLocalCacheinit end");
//
//
//        logger.info("验证缓存是否存在1");
//        List<SysPermission> allInfoByPid = sysPermissionService.getAllInfoByPid(310512);
//        allInfoByPid.forEach(
//                sysPermission -> {
//                    System.out.println(sysPermission.toString());
//                }
//        );
//        logger.info("验证缓存是否存在2");
//        // 每次都会执行sql ，把缓存放进去
//        List<SysPermission> f = sysPermissionService.getInfoByType("F");
//        f.forEach(sysPermission -> System.out.println(sysPermission.toString()));
//
//        logger.info("清除缓存");
//        sysPermissionService.clearTypeCache(310512);
//
//        logger.info("验证缓存是否存在3");
//        List<SysPermission> allInfoByPid2 = sysPermissionService.getAllInfoByPid(310512);
//        allInfoByPid2.forEach(
//                sysPermission -> {
//                    System.out.println(sysPermission.toString());
//                }
//        );


//        logger.info("系统本地缓存 SysLocalCacheinit end");
//
////        logger.info("从缓存中获取信息");
////        List<Shorturl> byShorturl1 = shortUrlGeneratorTestDao.findByShorturl("http://localhost:8089/u/1C");
////        byShorturl1.forEach(shorturl -> {
////            logger.info("获取信息: {} ", shorturl.getId());
////        });
//
//        // 手动获取缓存中的数据
//        Cache mycache = cacheManager.getCache("mycache");
//        Cache.ValueWrapper valueWrapper = mycache.get("http://localhost:8089/u/1C");
//        Object object = valueWrapper.get();
//        Shorturl shorturl = null;
//        if (object instanceof ArrayList) {
//            Object o = ((ArrayList) object).get(0);
//            if (o instanceof Shorturl) {
//                shorturl = (Shorturl) o;
//                logger.info("获取信息: {} ", shorturl.getId());
//            }
//        }
    }
}
