package fun.gengzi.codecopy.config;

import fun.gengzi.codecopy.business.security.service.impl.MyAuthenticationDetailsSource;
import fun.gengzi.codecopy.business.security.service.impl.MyInvalidSessionStrategy;
import fun.gengzi.codecopy.business.security.service.impl.UserDetailServiceImpl;
import fun.gengzi.codecopy.utils.HttpResponseUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.sql.DataSource;

/**
 * <h1>security 配置</h1>
 * <p>
 * 401 用户认证失败
 * 403 用户授权失败
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationDetailsSource myAuthenticationDetailsSource;

    @Qualifier("myAuthenticationProvider")
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Autowired
    private UserDetailServiceImpl userDetailService;


    /**
     * 可以用来配置 认证的用户
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Autowired
    private FindByIndexNameSessionRepository sessionRegistry;

    /**
     * 该类是 spring session 为 spring security 提供的 在集群环境下控制会话并发的会话注册表实现类
     *
     * @return
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(this.sessionRegistry);
    }

    /**
     * 认证方式：httpBasic
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用 security 提供的 操作 记住我 表的 dao层
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // super.configure(http);
        // http 基本认证方式  默认提供了一个简单的登陆页面
        // http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

        // 自定义表单认证方式
        http.authorizeRequests()
                .antMatchers("/securityBase/adminApi/**").hasRole("ADMIN")  //管理员 才能访问
                .antMatchers("/securityBase/userApi/**").hasRole("USER")   //登录用户 才能访问
                .antMatchers("/securityBase/publicApi/**").permitAll()  // 都可以访问
                .anyRequest().authenticated()   // 验证所有的请求
                .and()// and 作为中断上一个属性的配置分隔
                .formLogin() // 表单登陆
                .authenticationDetailsSource(myAuthenticationDetailsSource)
                .loginPage("/login.html")// 登陆的页面
                .loginProcessingUrl("/login")// 登陆的url
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {  // 成功登录的处理方法
                    // 成功登录所要做的操作
                    final ReturnData ret = ReturnData.newInstance();
                    ret.setSuccess();
                    ret.setMessage("登陆成功");
                    ret.setInfo(authentication);
                    HttpResponseUtils.responseResult(httpServletResponse, ret);
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {  // 认证失败的处理方法
                    // 登陆失败所要做的操作
                    final ReturnData ret = ReturnData.newInstance();
                    ret.setFailure("登陆失败");
                    HttpResponseUtils.responseResult(httpServletResponse, ret);

                })
                .permitAll()// 设置登陆页面和登陆路径 不设限访问
                .and()
                .csrf().disable() // csrf 防止跨站脚本攻击
                .formLogin()
                .and()
                .rememberMe()// 记住我
                .userDetailsService(userDetailService)
                .tokenRepository(jdbcTokenRepository)
                .and()
                .logout()
                .logoutSuccessUrl("/").and() // 注销登陆
                .sessionManagement().invalidSessionStrategy(new MyInvalidSessionStrategy()) // session失效后的策略
                .and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()); // 使用 spring session 提供的会话注册表
    }


//    /**
//     * 用户详细信息服务
//     *
//     * @return
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(User.withUsername("user").password("111").roles("USER").build());
//        inMemoryUserDetailsManager.createUser(User.withUsername("admin").password("111").roles("USER", "ADMIN").build());
//        return inMemoryUserDetailsManager;
//    }

//    @Bean
//    public HttpSessionEventPublisher getHttpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }


}
