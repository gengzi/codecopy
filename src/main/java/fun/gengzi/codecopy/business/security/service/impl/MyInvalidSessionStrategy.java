//package fun.gengzi.codecopy.business.security.service.impl;
//
//import org.springframework.security.web.session.InvalidSessionStrategy;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// *  配置当 session 失效，需要执行的策略
// */
//public class MyInvalidSessionStrategy implements InvalidSessionStrategy {
//    @Override
//    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().write("session失效了");
//    }
//}
