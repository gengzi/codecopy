package fun.gengzi.codecopy.business.authentication.service;

import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;

/**
 *
 * <h1>接口鉴权认证服务service</h1>
 * @author gengzi
 * @date 2020年6月5日14:07:11
 *
 */
public interface AuthenticationService {

    /**
     * 判断token 是否有效
     * @param token token
     * @return 有效 true 无效 false
     */
    Boolean isValidToken(String token);

    /**
     * 判断签名 是否被篡改
     * @param requestParamEntity 请求参数实体包含 随机码 和 签名sign
     * @return 无 true 有 false
     */
    Boolean isValidSign(RequestParamEntity requestParamEntity);




}
