package fun.gengzi.codecopy.business.luckdraw.filter;

import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.business.luckdraw.dao.ActivityDao;
import fun.gengzi.codecopy.business.luckdraw.entity.TbActivity;
import fun.gengzi.codecopy.utils.HttpResponseUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>活动过滤器</h1>
 *
 * @author gengzi
 * @date 2020年9月7日16:38:20
 */
//@Component//无需添加此注解，在启动类添加@ServletComponentScan注解后，会自动将带有@WebFilter的注解进行注入！
@WebFilter(filterName = "activityFilter", urlPatterns = "/luckdraw/*")
//指定过滤器的执行顺序,值越大越靠后执行
@Order(1)
public class ActivityFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ActivityFilter.class);

    @Autowired
    private ActivityDao activityDao;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 判断当前活动是否失效，失效，直接返回，并过滤无效活动id
        // 查询活动列表，先查询当前缓存，缓存失效，查询数据库
        final ReturnData ret = ReturnData.newInstance();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String aidStr = request.getParameter("aid");
//        String aidStr = request.getParameter("aid");
        if (StringUtils.isBlank(aidStr)) {
            logger.warn("活动id不存在!");
            ret.setFailure(LuckdrawEnum.ERROR_ACTIVITY_NOEXISTS.getMsg());
            HttpResponseUtils.responseResult((HttpServletResponse) response, ret);
            return;
        }
        List<TbActivity> tbActivities = activityDao.getEffectiveActivityInfo(new Date());
        if (CollectionUtils.isEmpty(tbActivities)) {
            logger.warn("当前无活动!");
            ret.setFailure(LuckdrawEnum.ERROR_ACTIVITY_NOEXISTS.getMsg());
            HttpResponseUtils.responseResult((HttpServletResponse) response, ret);
            return;
        }
        Optional<TbActivity> tbActivityInfo = tbActivities.stream().filter(tbActivity -> aidStr.equals(tbActivity.getId())).findFirst();
        tbActivityInfo.ifPresent(tbActivity ->
                {
                    long startTime = tbActivity.getStarttime().getTime();
                    long endTime = tbActivity.getEndtime().getTime();
                    long time = new Date().getTime();
                    if (time >= startTime && time <= endTime) {
                        try {
                            chain.doFilter(request, response);
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }
                    } else {
                        logger.warn("活动已经过期，感谢关注!");
                        ret.setFailure(LuckdrawEnum.ERROR_ACTIVITY_NOEXISTS.getMsg());
                        HttpResponseUtils.responseResult((HttpServletResponse) response, ret);
                        return;
                    }

                }
        );
    }
}
