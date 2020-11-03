package fun.gengzi.codecopy.business.security.service.impl;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <h1>认证用户信息</h1>
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imgCodeIsRight;

    public boolean getImgCodeIsRight() {
        return this.imgCodeIsRight;
    }

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 传递过来的参数
        String captcha = request.getParameter("captcha");
        //
        HttpSession session = request.getSession();
        String captcha_session = session.getAttribute("captcha").toString();
        if (session.equals(captcha_session)) {
            imgCodeIsRight = true;
        }
        imgCodeIsRight = false;
    }
}
