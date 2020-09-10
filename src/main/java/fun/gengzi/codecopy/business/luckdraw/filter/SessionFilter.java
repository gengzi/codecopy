package fun.gengzi.codecopy.business.luckdraw.filter;

import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.utils.HttpResponseUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>用户信息过滤器</h1>
 *
 * @author gengzi
 * @date 2020年9月9日14:14:37
 */
//@Component//无需添加此注解，在启动类添加@ServletComponentScan注解后，会自动将带有@WebFilter的注解进行注入！
//  filterName 首字母请小写
@WebFilter(filterName = "sessionFilter", urlPatterns = "/luckdraw/start/*")
//指定过滤器的执行顺序,值越大越靠后执行
@Order(2)
public class SessionFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 校验是否包含 AUTHORIZATION ，是否有效
        // 根据token获取用户信息，校验用户的积分是否足够
        final ReturnData ret = ReturnData.newInstance();
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String aidStr = request.getParameter("aid");

        if (StringUtils.isBlank(token)) {
            logger.info("无token结束 - BusinessAuthenticationAspect no token, end !");
            ret.setFailure(LuckdrawEnum.ERROR_INTEGRAL_LITTLE.getMsg());
            HttpResponseUtils.responseResult(httpServletResponse, ret);
            return;
        }

        String userInfokey = LuckdrawContants.USERPREFIX + token;
        boolean flag = redisUtil.hasKey(userInfokey);

        if (flag) {
            SysUser sysUser = (SysUser) redisUtil.get(userInfokey);
            logger.info("sysUser info :{} ", sysUser);
            if (sysUser != null && StringUtils.isNoneBlank(sysUser.getUid(), aidStr)) {
                // 重新设置token 过期时间
                redisUtil.expire(userInfokey, LuckdrawContants.INVALIDTIME);
                // 从redis 获取当前用户对应活动的积分数据
                String integralKey = LuckdrawContants.INTEGRAL_PREFIX + aidStr + LuckdrawContants.REDISKEYSEPARATOR + sysUser.getUid();
                TbIntegral tbIntegral = (TbIntegral) redisUtil.get(integralKey);
                Integer integral = tbIntegral.getIntegral();
                if (integral > 0) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    // 积分不足
                    ret.setFailure(LuckdrawEnum.ERROR_INTEGRAL_LITTLE.getMsg());
                    HttpResponseUtils.responseResult(httpServletResponse, ret);
                    return;
                }
            } else {
                // 无效用户信息
                ret.setFailure(LuckdrawEnum.ERROR_USERINFO_NOEXISTS.getMsg());
                HttpResponseUtils.responseResult(httpServletResponse, ret);
                return;
            }
        } else {
            // 无效token
            ret.setFailure(LuckdrawEnum.ERROR_USERINFO_NOEXISTS.getMsg());
            HttpResponseUtils.responseResult(httpServletResponse, ret);
            return;
        }
    }
}
