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


    List<SysPermission> findAllByPid(Integer pid);


    List<SysPermission> findByType(String type);





}
