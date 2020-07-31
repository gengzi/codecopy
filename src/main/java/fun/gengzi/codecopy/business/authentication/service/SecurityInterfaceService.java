package fun.gengzi.codecopy.business.authentication.service;

import java.util.Map;

/**
 * <h1>接口安全服务层</h1>
 *
 * @author gengzi
 * @date 2020年7月31日14:51:32
 */
public interface SecurityInterfaceService {


    /**
     * 生成 RSA 密钥 和 公钥
     *
     * @return  publickey 公钥  secretkey  密钥
     */
    Map<String, String> getRSASecretkeyAndPublickey();


    /**
     * 生成 aes 密钥
     *
     * @return aes 密钥
     */
    String getAESkey();


}
