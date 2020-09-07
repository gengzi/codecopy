package fun.gengzi.codecopy.business.luckdraw.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * <h1>活动过滤器</h1>
 *
 * @author gengzi
 * @date 2020年9月7日16:38:20
 *
 */
@WebFilter(filterName = "ActivityFilter" , urlPatterns = "/luckdraw")
public class ActivityFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 判断当前活动是否失效，失效，直接返回
        // 查询活动列表，先查询当前缓存，缓存失效，查询数据库

    }
}
