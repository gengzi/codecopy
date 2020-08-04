package fun.gengzi.codecopy.business.authentication.service;

import fun.gengzi.codecopy.business.authentication.entity.OrderInfoEntity;

import java.util.Map;
import java.util.Optional;

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
     * @return publickey 公钥  secretkey  密钥
     */
    Map<String, String> getRSASecretkeyAndPublickey();


    /**
     * 生成 aes 密钥
     *
     * @return aes 密钥
     */
    String getAESkey();

    /**
     * 使用 js rsa 算法私钥，对内容进行加密
     *
     * @param content  数据
     * @param secrekey 私钥
     * @return 加密后的内容
     */
    Optional<String> encryptJSByClientSecrekeyStr(String content, String secrekey);


    /**
     * 将数据和签名发送至支付宝
     *
     * @param orderInfoEntity {@link OrderInfoEntity} 订单信息
     * @return
     */
    Optional<String> sendSignAndDataInfoToZFB(OrderInfoEntity orderInfoEntity);


}
