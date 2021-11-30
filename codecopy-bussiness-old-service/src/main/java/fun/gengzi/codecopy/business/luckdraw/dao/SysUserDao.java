package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import fun.gengzi.codecopy.business.luckdraw.entity.TbPrize;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <h1>用户dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月9日16:01:17
 */
@Repository
public interface SysUserDao extends JpaRepository<SysUser, String> {


    /**
     * 获取用户信息
     * @param phone 手机号
     * @return {@link SysUser} 用户信息
     */
    SysUser findByPhone(String phone);


}
