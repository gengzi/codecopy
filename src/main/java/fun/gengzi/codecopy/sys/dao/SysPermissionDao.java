package fun.gengzi.codecopy.sys.dao;

import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.config.CacheConfigEnum;
import fun.gengzi.codecopy.sys.entity.SysPermission;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>系统菜单dao</h1>
 * 使用三个缓存注解
 *
 */
@Repository
public interface SysPermissionDao extends JpaRepository<SysPermission, Integer> {

    // 如果有缓存，就读缓存，没有就去执行这个方法
    @Cacheable(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", key = "#pid")
    List<SysPermission> findAllByPid(Integer pid);

    // 如果有缓存，就读缓存，没有就去执行这个方法
    @CachePut(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU_TYPE_F", key = "#type")
    List<SysPermission> findByType(String type);





}
