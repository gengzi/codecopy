package fun.gengzi.codecopy.business.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    private Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);
    @Autowired
    private UserDetailServiceImpl userDetailService;

    /**
     * 自定义校验
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 判断密码不能为null
        String password = userDetails.getPassword();

        // 获取凭证
        Object credentials = authentication.getCredentials();
        if(credentials == null){
            throw  new RuntimeException("passwd is null");
        }
        // 密码
        String passwd = credentials.toString();
        logger.info(passwd);
        // 密码对比
    }

    /**
     * 检索用户
     * @param username
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
       return userDetailService.loadUserByUsername(username);
    }
}
