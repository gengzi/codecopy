package fun.gengzi.codecopy.business.authentication.service.impl;

import fun.gengzi.codecopy.business.authentication.constant.AuthenticationConstans;
import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.business.authentication.service.AuthenticationService;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    // AES 的密钥
    @Value("${token.aeskey}")
    private String aeskey;

    /**
     * 判断token 是否有效
     *
     * @param token token
     * @return 有效 true 无效 false
     */
    @Override
    public Boolean isValidToken(String token) {
        //TODO 默认成功
        return true;
    }

    /**
     * 判断签名 是否被篡改
     *
     * 防止请求重放
     *  //TODO 可以将 sign 在redis 中存储，比如reids 中，如果遇到 一致的 sign ，表示 同一个请求，请求了两次，可以拒绝
     *
     *
     *
     * @param requestParamEntity 请求参数实体包含 随机码 和 签名sign
     * @return 无 true 有 false
     */
    @Override
    public Boolean isValidSign(RequestParamEntity requestParamEntity) {
        final String reqNum = requestParamEntity.getReqNum();
        final String sign = requestParamEntity.getSign();
        // 解密
        final String reqParams = AESUtils.decrypt(sign, aeskey)
                .orElseThrow(() -> new RrException("认证失败", RspCodeEnum.FAILURE.getCode()));
        // 解析参数，对比 随机码 跟签名中的随机码是否一致
        String[] split = reqParams.split("&");
        final HashMap<String, String> paramKeyValue = new HashMap<>();
        Arrays.asList(split).forEach(s -> {
            String[] keyvalue = s.split("=");
            paramKeyValue.put(keyvalue[0], keyvalue[1]);
        });
        paramKeyValue.get(AuthenticationConstans.CALLNUMBER);
        String reqNumByMap = paramKeyValue.get(AuthenticationConstans.REQNUM);
        if (StringUtils.isNoneBlank(reqNum) && reqNum.equals(reqNumByMap)) {
            return true;
        }
        return false;
    }
}
