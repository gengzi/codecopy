package fun.gengzi.codecopy.sys.service.impl;

import fun.gengzi.codecopy.sys.dao.SysPermissionDao;
import fun.gengzi.codecopy.sys.entity.SysPermission;
import fun.gengzi.codecopy.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    /**
     * 根据pid 查找pid 下面的菜单项
     *
     * @param pid 菜单父id
     * @return {@link List < SysPermission >}
     */
    @Cacheable(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", key = "'result_pid_'+#pid")
    @Override
    public List<SysPermission> getAllInfoByPid(Integer pid) {
        return sysPermissionDao.findAllByPid(pid);
    }

    /**
     * 添加一条系统菜单数据，并把这条数据缓存在本地
     *
     * @param sysPermission {@link SysPermission}
     * @return {@link SysPermission}
     */
    @CachePut(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", key = "#result.pid")
    @Override
    public SysPermission addSysPermissionInfo(SysPermission sysPermission) {
        return sysPermissionDao.save(sysPermission);
    }

    /**
     * 根据id 更新菜单名称，并更新到缓存
     * <p>
     * cacheput 将方法结果返回到缓存中
     * condition 如果这个条件，返回 true 才会缓存，false 不缓存
     *
     * @return
     */
    @CachePut(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", condition = "#result != 'null'", key = "#result.pid")
    @Override
    public SysPermission updateNameById(SysPermission sysPermission) {
        return sysPermissionDao.save(sysPermission);
    }

    /**
     * 根据id 删除菜单
     * CacheEvict 清除指定缓存
     * beforeInvocation 标识，在执行方法之前移除缓存，还是再执行方法之后移除缓存 默认值 false 在方法之后移除
     * key  移除的键
     *
     * @return
     */
    @CacheEvict(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", key = "#id", beforeInvocation = true)
    @Override
    public void removeSysPermissionInfo(SysPermission sysPermission) {
        sysPermissionDao.delete(sysPermission);
    }
}
