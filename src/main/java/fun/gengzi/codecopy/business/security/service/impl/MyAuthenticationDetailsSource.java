//package fun.gengzi.codecopy.business.security.service.impl;
//
//import org.springframework.security.authentication.AuthenticationDetailsSource;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//
//
///**
// * <h1>身份信息认证来源</h1>
// */
//@Service
//public class MyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,WebAuthenticationDetails> {
//
//
//    /**
//     * 构建信息
//     * @param request
//     * @return
//     */
//    @Override
//    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
//        // 将request 中的内容加入到用户信息中
//        return new MyWebAuthenticationDetails(request);
//    }
//}
