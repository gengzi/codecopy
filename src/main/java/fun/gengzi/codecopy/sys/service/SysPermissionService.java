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
     * 根据pid 查找pid 的缓存的菜单项
     *
     * @param pid 菜单父id
     * @return {@link List<SysPermission>}
     */
    List<SysPermission> getAllInfoByPid(Integer pid);


    /**
     * 添加一条系统菜单数据，并把这条数据缓存在本地
     *
     * @param sysPermission {@link SysPermission}
     * @return {@link SysPermission}
     */
    SysPermission addSysPermissionInfo(SysPermission sysPermission);


    /**
     * 根据id 更新菜单名称，并更新到缓存
     *
     * @param sysPermission {@link SysPermission}
     * @return
     */
    SysPermission updateNameById(SysPermission sysPermission);


    /**
     * 根据id 删除菜单
     *
     * @param sysPermission {@link SysPermission}
     * @return
     */
    void removeSysPermissionInfo(SysPermission sysPermission);


}
