//package fun.gengzi.codecopy.business.security.service.impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MyAuthenticationProviderBycapacha extends DaoAuthenticationProvider {
//
//    public MyAuthenticationProviderBycapacha(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.setPasswordEncoder(passwordEncoder);
//        this.setUserDetailsService(userDetailsService);
//    }
//
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        // 增加验证码的认证
//        MyWebAuthenticationDetails myWebAuthenticationDetails = (MyWebAuthenticationDetails) authentication.getDetails();
//        boolean imgCodeIsRight = myWebAuthenticationDetails.getImgCodeIsRight();
//        if (!imgCodeIsRight) {
//            throw new RuntimeException("验证码输入错误");
//        }
//        // 调用父类的用户认证
//        super.additionalAuthenticationChecks(userDetails, authentication);
//    }
//}
