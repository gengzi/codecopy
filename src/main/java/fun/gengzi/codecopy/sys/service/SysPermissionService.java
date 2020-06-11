package fun.gengzi.codecopy.sys.service;

import fun.gengzi.codecopy.sys.entity.SysPermission;

import java.util.List;

/**
 * <h1>系统菜单权限service</h1>
 *
 * @author gengzi
 * @date 2020年6月11日15:12:51
 */
public interface SysPermissionService {

    /**
     * 根据pid 查找pid 下面的菜单项
     *
     * @param pid 菜单父id
     * @return {@link List<SysPermission>}
     */
    List<SysPermission> getAllInfoByPid(Integer pid);

    /**
     * 根据type 菜单类型，查找菜单项
     *
     * @param type 菜单父id
     * @return {@link List<SysPermission>}
     */
    List<SysPermission> getInfoByType(String type);


    /**
     * 根据 key 清除根据菜单类型缓存的数据
     *
     * @param key
     * @return
     */
    boolean clearTypeCache(Integer key);


}
