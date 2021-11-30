package fun.gengzi.codecopy.business.authentication.service;

import fun.gengzi.codecopy.business.authentication.entity.MustParamEntity;
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

    /**
     * 将数据和签名发送至商户
     *
     * @param mustParamEntity {@link MustParamEntity}
     * @return
     */
    Optional<String> sendSignAndDataInfoToSH(MustParamEntity mustParamEntity);


    /**
     * 支付宝校验签名和请求参数，执行业务，重新回调商户的回调地址
     *
     * @param mustParamEntity
     * @return
     */
    boolean responseSignAndDataInfoToSH(MustParamEntity mustParamEntity);


    /**
     * 商户验证支付宝的 签名和请求参数，执行自身的业务
     *
     * @param mustParamEntity
     * @return
     */
    boolean responseSignAndDataInfoToZFB(MustParamEntity mustParamEntity);

    /**
     * java 调用js 进行加密
     */
    void paramEncryptionToJs();


    /**
     * 返回字段签名
     *
     * @param salt   盐
     * @param fields 字段集合
     * @return 摘要
     */
    String signFields(String salt, String... fields);

    /**
     * 校验字段签名
     *
     * @param signKey 签名
     * @param salt    盐
     * @param fields  字段集合
     * @return true 校验成功， false 校验失败
     */
    boolean checkSignField(String signKey, String salt, String... fields);


}
