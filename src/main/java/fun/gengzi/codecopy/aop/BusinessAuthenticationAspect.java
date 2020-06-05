package fun.gengzi.codecopy.aop;


import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>接口鉴权校验 aop</h1>
 *
 * @author gengzi
 * @date 2020年6月5日13:49:03
 */
@Aspect
@Configuration
public class BusinessAuthenticationAspect {

    private Logger logger = LoggerFactory.getLogger(BusinessAuthenticationAspect.class);

    // 鉴权服务接口url地址
    @Value("${token.url.validToken}")
    private String validToken;
    // AES 密钥
    @Value("${token.aeskey}")
    private String aeskey;

    //切入点
    @Pointcut("@annotation(fun.gengzi.codecopy.aop.BusinessAuthentication)")
    public void BusinessAuthenticationAspect() {

    }

    /**
     * <h2>环绕通知</h2>
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
        logger.info("鉴权 - BusinessAuthenticationAspect start");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BusinessAuthentication businessAuthentication = method.getAnnotation(BusinessAuthentication.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 校验token 是否有效
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 获取controller 方法名称
        final String name = method.getName();
        logger.info("被鉴权的方法 - BusinessAuthenticationAspect method name : {}", name);
        // 如果没有 token，进行记录并抛出异常，响应前台
        if (StringUtils.isBlank(token)) {
            logger.info("无token结束 - BusinessAuthenticationAspect no token, end !");
            throw new RrException("无权限", RspCodeEnum.NOTOKEN.getCode());
        }
        // 调用鉴权服务接口，鉴权
        Boolean flag = reqValidToken(businessAuthentication, token);
        Object obj;
        try {
            if (flag) {
                // 鉴权成功，允许调用指定接口
                logger.info("鉴权成功 - BusinessAuthenticationAspect success !");
                obj = joinPoint.proceed();
            } else {
                logger.info("鉴权失败- BusinessAuthenticationAspect failure !");
                throw new RrException("无权限");
            }
        } catch (Throwable e) {
            logger.error("鉴权失败，出现异常- BusinessAuthenticationAspect failure ! , exception : {} ", e.getMessage());
            throw new RrException("无权限");
        }
        return obj;
    }

    /**
     * <h2>调用接口鉴权服务</h2>
     * <p>
     * // 调用鉴权服务，进行 token 校验，并返回该用户信息
     * // 先定义 aes 秘钥，定义 rsa 的公钥和秘钥
     * // 使用 aes 秘钥对 组装后的参数加密，再base64 转码 生成一个签名
     * // 调用服务端， token 设置在请求头，请求体是 签名 ， 其他固定的几个参数
     * <p>
     * // 服务端校验 token， 解析请求体参数，将签名 使用 base64 解开，然后 aes 解密
     * // 解密完成，比较 参数是否一致，一致说明，参数没有在传递时发生更改。
     * // 服务端鉴权完毕，响应数据，将数据使用 rsa 私钥加密，使用base64 转码
     * <p>
     * // 客户端获取响应数据，对应字段使用base64 转码，使用 rsa 公钥解密，解密完成，正式成功
     *
     * @param businessAuthentication 注解
     * @param token                  认证token
     * @return 鉴权成功 true ，鉴权失败 false
     */
    private Boolean reqValidToken(BusinessAuthentication businessAuthentication, String token) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        StringBuilder signBuilder = new StringBuilder();
        // 获取注解中的字段
        final int callNumber = businessAuthentication.callNumber();
        // 随机码
        String reqNum = IdUtil.randomUUID();
        // 组拼参数，例如： reqNum=uuid值&callNumber=33&
        if (callNumber > 0) {
            concurrentHashMap.put(AuthenticationConstans.CALLNUMBER, String.valueOf(callNumber));
        }
        concurrentHashMap.put(AuthenticationConstans.REQNUM, reqNum);
        concurrentHashMap.forEach((key, value) -> signBuilder.append(key).append("=").append(value).append("&"));
        // 将参数加密
        String signStr = AESUtils.encrypt(signBuilder.toString(), aeskey)
                .orElseThrow(() -> new RrException("error", RspCodeEnum.FAILURE.getCode()));
        // 调用鉴权服务接口
        logger.info("调用鉴权接口参数- BusinessAuthenticationAspect qryParams token :{},reqNum :{} ,signStr :{}", token, reqNum, signStr);
        ReturnData returnData = getReturnData(token, reqNum, signStr);
        logger.info("调用鉴权接口结果- BusinessAuthenticationAspect result", returnData.toString());

        Boolean flag = false;
        if (RspCodeEnum.SUCCESS.getCode() == returnData.getStatus()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 调用鉴权服务接口
     *
     * @param token   token
     * @param reqNum  随机码
     * @param signStr 签名
     * @return {@link ReturnData}
     */
    private ReturnData getReturnData(String token, String reqNum, String signStr) {
        // 封装请求参数
        RequestParamEntity requestParamEntity = new RequestParamEntity();
        requestParamEntity.setReqNum(reqNum);
        requestParamEntity.setSign(signStr);
        String jsonBody = JSONUtil.parseObj(requestParamEntity, false).toStringPretty();
        String body = HttpRequest.post(validToken)
                .header(Header.AUTHORIZATION, token)
                .body(jsonBody).execute().body();
        return JSONUtil.toBean(body, ReturnData.class);
    }

}
