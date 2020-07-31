package fun.gengzi.codecopy.business.authentication.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityInterfaceServiceImpl implements SecurityInterfaceService {
    /**
     * 生成 RSA 密钥 和 公钥
     *
     * @return publickey 公钥  secretkey  密钥
     */
    @Override
    public Map<String, String> getRSASecretkeyAndPublickey() {
        return RSAUtils.generatekeyToMap();
    }

    /**
     * 生成 aes 密钥
     *
     * @return aes 密钥
     */
    @Override
    public String getAESkey() {
        return AESUtils.generatekey();
    }
}
