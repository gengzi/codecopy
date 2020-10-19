package fun.gengzi.codecopy.config;

import fun.gengzi.codecopy.utils.HttpResponseUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import org.I0Itec.zkclient.InMemoryConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>security 配置</h1>
 * <p>
 * 401 用户认证失败
 * 403 用户授权失败
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 认证方式：httpBasic
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // http 基本认证方式  默认提供了一个简单的登陆页面
        // http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

        // 自定义表单认证方式
        http.authorizeRequests()
                .antMatchers("/securityBase/adminApi/**").hasRole("ADMIN")
                .antMatchers("/securityBase/userApi/**").hasRole("USER")
                .antMatchers("/securityBase/publicApi/**").permitAll()
                .anyRequest().authenticated()   // 验证所有的请求
                .and()// and 作为中断上一个属性的配置分隔
                .formLogin() // 表单登陆
                .loginPage("/login.html")// 登陆的页面
                .loginProcessingUrl("/login")// 登陆的url
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    // 成功登录所要做的操作
                    final ReturnData ret = ReturnData.newInstance();
                    ret.setSuccess();
                    ret.setMessage("登陆成功");
                    ret.setInfo(authentication);
                    HttpResponseUtils.responseResult(httpServletResponse, ret);
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    // 登陆失败所要做的操作
                    final ReturnData ret = ReturnData.newInstance();
                    ret.setFailure("登陆失败");
                    HttpResponseUtils.responseResult(httpServletResponse, ret);

                })
                .permitAll()// 设置登陆页面和登陆路径 不设限访问
                .and()
                .csrf().disable(); // csrf 防止跨站脚本攻击
    }


    /**
     * 用户详细信息服务
     *
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("user").password("111").roles("USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("111").roles("USER", "ADMIN").build());
        return inMemoryUserDetailsManager;
    }


}
