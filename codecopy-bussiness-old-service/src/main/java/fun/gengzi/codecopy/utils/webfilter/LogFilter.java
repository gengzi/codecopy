package fun.gengzi.codecopy.utils.webfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.context.support.UiApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志采集器
 */
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        //获取请求参数
//        if ("POST".equalsIgnoreCase(request.getMethod())) {
//            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("@timestamp", DateUtil.getCurrentDatetime());
//            jsonObject.put("remote_addr", getIpAddr(request));
//            jsonObject.put("remote_port", request.getServerPort());
//            jsonObject.put("request_method", request.getMethod());
//            jsonObject.put("request_uri", request.getRequestURI());
//            jsonObject.put("request_header", JSON.toJSONString(getHeadersInfo(request)));
//            String body = HttpHelper.getBodyString(requestWrapper);
//            jsonObject.put("request_body", body);
//            //LogMgr.sysInfo("buriedMessage###" + jsonObject.toString());
//            KafkaProducterInstance  kafkaProducterInstance = SpringContextUtils.getBean(KafkaProducterInstance.class);
//            kafkaProducterInstance.defaultGeneric().sendMessage("social-action-buried",null,"buriedMessage###"+jsonObject.toString(),true);
//            filterChain.doFilter(requestWrapper, servletResponse);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
    }


    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求IP
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public void destroy() {

    }
}
