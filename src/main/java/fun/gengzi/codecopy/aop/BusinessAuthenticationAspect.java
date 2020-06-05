package fun.gengzi.codecopy.aop;


import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import fun.gengzi.codecopy.business.authentication.constant.AuthenticationConstans;
import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口权限校验 aop
 */
@Aspect
@Configuration
public class BusinessAuthenticationAspect {

    @Value("${token.url.validToken}")
    private String validToken;

    @Value("${token.aeskey}")
    private String aeskey;

    //切入点
    @Pointcut("@annotation(fun.gengzi.codecopy.aop.BusinessAuthentication)")
    public void BusinessAuthenticationAspect() {

    }

    /**
     * // 根据controller 配置的注解，执行该方法
     * // 获取请求信息中的 token 校验，存在，执行 token 校验，不存在，阻断
     * // 校验 token ，失败，阻断
     * // 获取该注解配置的字段信息
     * // 校验该用户是否还有调用次数 ， 无 ，阻断
     * // 校验该用户是否在允许的ip 范围， 无，阻断
     * // 放行
     *
     * @param joinPoint
     * @return
     */
    @Around("BusinessAuthenticationAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessAuthentication businessAuthentication = method.getAnnotation(BusinessAuthentication.class);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 校验token 是否有效
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 获取controller 方法名称
        final String name = method.getName();
        // 如果没有 token，进行记录并抛出异常，响应前台
        if (StringUtils.isBlank(token)) {
            throw new RrException("无权限", RspCodeEnum.NOTOKEN.getCode());
        }
        // 组拼http 请求
        final int callNumber = businessAuthentication.callNumber();
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        String reqNum = IdUtil.randomUUID();
        concurrentHashMap.put(AuthenticationConstans.REQNUM, reqNum);
        StringBuilder signBuilder = new StringBuilder();
        if (callNumber > 0) {
            concurrentHashMap.put(AuthenticationConstans.REQNUM, String.valueOf(callNumber));
        }
        concurrentHashMap.forEach((key, value) -> {
            signBuilder.append(key).append("=").append(value).append("&");
        });
        String signStr = AESUtils.encrypt(signBuilder.toString(), aeskey)
                .orElseThrow(() -> new RrException("error", RspCodeEnum.FAILURE.getCode()));

        RequestParamEntity requestParamEntity = new RequestParamEntity();
        requestParamEntity.setReqNum(reqNum);
        requestParamEntity.setSign(signStr);

        String jsonBody = JSONUtil.parseObj(requestParamEntity, false).toStringPretty();

        String body = HttpRequest.post(validToken)
                .header(Header.AUTHORIZATION, token)
                .body(jsonBody).execute().body();
        ReturnData returnData = JSONUtil.toBean(body, ReturnData.class);

        // 非阻塞的获取令牌 可以实时返回结果
        Boolean flag = false;
        if (RspCodeEnum.SUCCESS.getCode() == returnData.getStatus()) {
            flag = true;
        }

        // 调用鉴权服务，进行 token 校验，并返回该用户信息
        // 先定义 aes 秘钥，定义 rsa 的公钥和秘钥
        // 使用 aes 秘钥对 组装后的参数加密，再base64 转码 生成一个签名
        // 调用服务端， token 设置在请求头，请求体是 签名 ， 其他固定的几个参数

        // 服务端校验 token， 解析请求体参数，将签名 使用 base64 解开，然后 aes 解密
        // 解密完成，比较 参数是否一致，一致说明，参数没有在传递时发生更改。
        // 服务端鉴权完毕，响应数据，将数据使用 rsa 私钥加密，使用base64 转码

        // 客户端获取响应数据，对应字段使用base64 转码，使用 rsa 公钥解密，解密完成，正式成功


        Object obj;
        try {
            if (flag) {
                obj = joinPoint.proceed();
            } else {
                throw new RrException("无权限");
            }
        } catch (Throwable e) {
            throw new RrException("无权限");
        }
        return obj;
    }

}
