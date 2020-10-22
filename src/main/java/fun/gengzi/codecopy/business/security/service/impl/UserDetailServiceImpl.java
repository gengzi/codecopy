package fun.gengzi.codecopy.business.security.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h1>用户详细信息服务 实现</h1>
 *
 * @author gengzi
 * @date 2020年10月19日16:11:45
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名  获取  用户信息
        // 判断用户信息是否存在
        // 返回用户信息
        return null;
    }
}
