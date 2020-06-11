package fun.gengzi.codecopy.sys.service.impl;

import fun.gengzi.codecopy.sys.dao.SysPermissionDao;
import fun.gengzi.codecopy.sys.entity.SysPermission;
import fun.gengzi.codecopy.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @Override
    public List<SysPermission> getAllInfoByPid(Integer pid) {
        return sysPermissionDao.findAllByPid(pid);
    }

    /**
     * 根据type 菜单类型，查找菜单项
     *
     * @param type 菜单父id
     * @return {@link List<SysPermission>}
     */
    @Override
    public List<SysPermission> getInfoByType(String type) {
        return sysPermissionDao.findByType(type);
    }

    /**
     * 根据 key 清除根据菜单类型缓存的数据
     *
     * @param key
     * @return
     */
    @CacheEvict(cacheManager = "localhostCacheManager", value = "DATADIC_GLOBAL_MENU", key = "#key")
    @Override
    public boolean clearTypeCache(Integer key) {
        return true;
    }
}
