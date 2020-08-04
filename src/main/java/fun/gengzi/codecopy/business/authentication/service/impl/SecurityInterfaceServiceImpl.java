package fun.gengzi.codecopy.business.authentication.service.impl;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import fun.gengzi.codecopy.business.authentication.constant.SecurityInterfaceConstans;
import fun.gengzi.codecopy.business.authentication.entity.MustParamEntity;
import fun.gengzi.codecopy.business.authentication.entity.OrderInfoEntity;
import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import fun.gengzi.codecopy.utils.StreamUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
public class SecurityInterfaceServiceImpl implements SecurityInterfaceService {


    private Logger logger = LoggerFactory.getLogger(SecurityInterfaceServiceImpl.class);

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

    /**
     * 使用 js rsa 算法私钥，对内容进行加密
     * <p>
     * TODO  对于 ClassLoader.getSystemClassLoader().getResource 中的路径分隔符 必须写成  /  如果写成 \\ 或者 \ 路径中将会出现 %5C
     *
     * @param content  数据
     * @param secrekey 私钥
     * @return 加密后的内容
     */
    @Override
    public Optional<String> encryptJSByClientSecrekeyStr(String content, String secrekey) {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
//            String cryptoPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\crypto-js-4.0.0\\crypto-js.js";
//            String functionPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\other.js";
            String basePath = ClassLoader.getSystemClassLoader().getResource("js/base.js").getPath();
            String cryptoPath = ClassLoader.getSystemClassLoader().getResource("js/jsencrypt/jsencrypt.js").getPath();
            String functionPath = ClassLoader.getSystemClassLoader().getResource("js/RSAUtils.js").getPath();

            logger.info("cryptoPath rsa加密js路径 {}", cryptoPath);
            logger.info("functionPath 实际执行加密方法的js路径 {}", functionPath);
            nashorn.eval(Files.newBufferedReader(Paths.get(basePath.substring(1))));
            nashorn.eval(Files.newBufferedReader(Paths.get(cryptoPath.substring(1))));
            nashorn.eval(Files.newBufferedReader(Paths.get(functionPath.substring(1))));

            Invocable in = (Invocable) nashorn;
            // 调用 js 的function 方法

            content = "hello world";
            secrekey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALbcd" +
                    "LKOtTKOjalffv/LLLOqfyh8Ep4XHjvOivMU3Nb1N0puG4+" +
                    "NTrXBS8GDczgsZ+7J6D7FTcH8JInMKpz85LMCAwEAAQ==";
            // https://www.cnblogs.com/wsss/p/11516318.html
            Object o = in.invokeFunction("encryptRSAByPublicKey", content, secrekey);
            return Optional.of(o.toString());
        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 将数据和签名发送至支付宝
     * <p>
     * <p>
     * https://openapi.alipay.com/gateway.do?timestamp=2013-01-01 08:08:08&method=alipay.trade.wap.pay&app_id=19186&sign_type=RSA2&sign=ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE&version=1.0&charset=GBK&biz_content={"time_expire":"2016-12-31 10:05","extend_params":{"sys_service_provider_id":"2088511833207846","hb_fq_seller_percent":"100","hb_fq_num":"3","industry_reflux_info":"{\\\"scene_code\\\":\\\"metro_tradeorder\\\",\\\"channel\\\":\\\"xxxx\\\",\\\"scene_data\\\":{\\\"asset_name\\\":\\\"ALIPAY\\\"}}","card_type":"S0JP0000"},"settle_info":{"settle_period_time":"7d","settle_detail_infos":[{"amount":0.1,"trans_in":"A0001","settle_entity_type":"SecondMerchant??Store","summary_dimension":"A0001","settle_entity_id":"2088xxxxx;ST_0001","trans_in_type":"cardAliasNo"}]},"subject":"?????","body":"Iphone6 16G","product_code":"QUICK_WAP_WAY","merchant_order_no":"20161008001","sub_merchant":{"merchant_id":"2088000603999128","merchant_type":"alipay: ???????????????????, merchant: ???????????????"},"invoice_info":{"key_info":{"tax_num":"1464888883494","is_support_invoice":true,"invoice_merchant_name":"ABC|003"},"details":"[{\"code\":\"100294400\",\"name\":\"????\",\"num\":\"2\",\"sumPrice\":\"200.00\",\"taxRate\":\"6%\"}]"},"ext_user_info":{"cert_type":"IDENTITY_CARD","cert_no":"362334768769238881","name":"????","mobile":"16587658765","fix_buyer":"F","min_age":"18","need_check_info":"F"},"timeout_express":"90m","disable_pay_channels":"pcredit,moneyFund,debitCardExpress","seller_id":"2088102147948060","royalty_info":{"royalty_type":"ROYALTY","royalty_detail_infos":[{"out_relation_id":"20131124001","amount_percentage":"100","amount":0.1,"batch_no":"123","trans_in":"2088101126708402","trans_out_type":"userId","trans_out":"2088101126765726","serial_no":1,"trans_in_type":"userId","desc":"???????1"}]},"store_id":"NJ_001","quit_url":"http://www.taobao.com/product/113714.html","passback_params":"merchantBizType%3d3C%26merchantBizNo%3d2016010101111","specified_channel":"pcredit","goods_detail":[{"goods_name":"ipad","alipay_goods_id":"20010001","quantity":1,"price":2000,"goods_id":"apple-01","goods_category":"34543238","categories_tree":"124868003|126232002|126252004","body":"??????","show_url":"http://www.alipay.com/xxx.jpg"}],"enable_pay_channels":"pcredit,moneyFund,debitCardExpress","out_trade_no":"70501111111S001111119","total_amount":9.00,"business_params":"{\"data\":\"123\"}","goods_type":"0","auth_token":"appopenBb64d181d0146481ab6a762c00714cC27","promo_params":"{\"storeIdType\":\"1\"}"}
     *
     *
     * <p>
     * 筛选并排序
     * 获取所有请求参数，不包括字节类型参数，如文件、字节流，剔除 sign 字段，剔除值为空的参数，
     * 并按照第一个字符的键值 ASCII 码递增排序（字母升序排序），
     * 如果遇到相同字符则按照第二个字符的键值 ASCII 码递增排序，以此类推。
     * <p>
     * 拼接
     * 将排序后的参数与其对应值，组合成“参数=参数值”的格式，并且把这些参数用 & 字符连接起来，此时生成的字符串为待签名字符串。
     * <p>
     * 以一下部分代码，参考了支付宝提供的sdk 中的源码
     * AlipaySignature.rsaSign
     *
     * @param orderInfoEntity {@link OrderInfoEntity} 订单信息
     * @return
     */
    @Override
    public Optional<String> sendSignAndDataInfoToZFB(OrderInfoEntity orderInfoEntity) {
        // 整理请求参数
        // toJSONString 默认情况下属性值为null的字段不会打印
        String biz_content = JSONObject.toJSONString(orderInfoEntity);
        logger.info("请求实体 biz_content : {}", biz_content);
        MustParamEntity mustParamEntity = new MustParamEntity();
        mustParamEntity.setBiz_content(biz_content);
        mustParamEntity.setNotify_url("http://localhost:8089/api/v2/payMoneyResponse");
        mustParamEntity.setTimestamp(String.valueOf(System.currentTimeMillis()));
        // 获取所有请求参数，不包括字节类型参数，如文件、字节流，剔除 sign 字段，剔除值为空的参数
        // 并排序
        TreeMap<String, String> treeMap = mustParamEntity.mustParamEntityToMap(mustParamEntity);
        treeMap.forEach((k, v) -> {
            logger.info("排序后的 key : {} || value {}", k, v);
        });

        if (!treeMap.isEmpty()) {
            // 需要签名内容
            String signContent = getSignContent(treeMap);
            logger.info("签名内容 signContent : {}", signContent);
            if (StringUtils.isNotBlank(signContent)) {
                // 生成签名
                String sign = createSign(signContent, SecurityInterfaceConstans.MYPRIVATEKEYRSA, SecurityInterfaceConstans.DEFAULT_CHARSET);
                // 发送请求
                logger.info("签名 sign : {}", sign);
                mustParamEntity.setSign(sign);
            }
        }

        if (StringUtils.isNoneBlank(mustParamEntity.getSign())) {
            // 发送请求
            String jsonBody = JSONUtil.parseObj(mustParamEntity, false).toStringPretty();
            String body = HttpRequest.post(SecurityInterfaceConstans.PAYMONEYZFBURL)
                    .body(jsonBody).execute().body();
            return Optional.ofNullable(body);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> sendSignAndDataInfoToSH(MustParamEntity mustParamEntity) {
        mustParamEntity.setTimestamp(String.valueOf(System.currentTimeMillis()));
        // 获取所有请求参数，不包括字节类型参数，如文件、字节流，剔除 sign 字段，剔除值为空的参数
        // 并排序
        TreeMap<String, String> treeMap = mustParamEntity.mustParamEntityToMap(mustParamEntity);
        treeMap.forEach((k, v) -> {
            logger.info("排序后的 key : {} || value {}", k, v);
        });

        if (!treeMap.isEmpty()) {
            // 需要签名内容
            String signContent = getSignContent(treeMap);
            logger.info("签名内容 signContent : {}", signContent);
            if (StringUtils.isNotBlank(signContent)) {
                // 生成签名
                String sign = createSign(signContent, SecurityInterfaceConstans.ZFBPRIVATEKEYRSA, SecurityInterfaceConstans.DEFAULT_CHARSET);
                // 发送请求
                logger.info("签名 sign : {}", sign);
                mustParamEntity.setSign(sign);
            }
        }

        if (StringUtils.isNoneBlank(mustParamEntity.getSign())) {
            // 发送请求
            String jsonBody = JSONUtil.parseObj(mustParamEntity, false).toStringPretty();
            String body = HttpRequest.post(mustParamEntity.getNotify_url())
                    .body(jsonBody).execute().body();
            return Optional.ofNullable(body);
        }
        return Optional.empty();
    }

    /**
     * 支付宝校验签名和请求参数，执行业务，重新回调商户的回调地址
     *
     * @param mustParamEntity
     * @return
     */
    @Override
    public boolean responseSignAndDataInfoToSH(MustParamEntity mustParamEntity) {
        if (StringUtils.isBlank(mustParamEntity.getSign())) {
            throw new RrException("sign 不能为null");
        }
        String sign = mustParamEntity.getSign();
        TreeMap<String, String> treeMap = mustParamEntity.mustParamEntityToMap(mustParamEntity);
        String signContent = getSignContent(treeMap);
        try {
            boolean isSuccess = this.parseSign(sign, SecurityInterfaceConstans.DEFAULT_CHARSET, SecurityInterfaceConstans.MYPUBLICKEYRSA, signContent);
            if (isSuccess) {
                logger.info("验签成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = sortedParams.get(key);
            if (areNotEmpty(key, value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }


    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成签名
     *
     * @param content    加签内容
     * @param privateKey 商户私钥
     * @param charset    字符集 默认 uft-8
     * @return
     */
    private String createSign(String content, String privateKey, String charset) {
        try {
            if (StringUtils.isEmpty(content)) {
                throw new RrException("待签名内容不可为空");
            }
            if (StringUtils.isEmpty(privateKey)) {
                throw new RrException("私钥不可为空");
            }
            if (StringUtils.isEmpty(charset)) {
                charset = SecurityInterfaceConstans.DEFAULT_CHARSET;
            }
            return doSign(content, charset, privateKey);
        } catch (Exception e) {
            String errorMessage = "RSA 签名遭遇异常，请检查私钥格式是否正确。" + e.getMessage() +
                    " content=" + content + "，charset=" + charset + "，privateKeySize=" + privateKey.length();
            throw new RrException(errorMessage, e);
        }
    }


    /**
     * @param content    需要加密的内容
     * @param charset    字符集
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    protected String doSign(String content, String charset, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKeyFromPKCS8(SecurityInterfaceConstans.SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));
        Signature signature = Signature.getInstance(getSignAlgorithm());
        signature.initSign(priKey);
        if (StringUtils.isEmpty(charset)) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }
        byte[] signed = signature.sign();
        return new String(Base64.encodeBase64(signed));
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
                                                    InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }


    protected String getSignAlgorithm() {
        return SecurityInterfaceConstans.SIGN_ALGORITHMS;
    }


    /**
     * 公钥解密
     *
     * @param signStr   签名
     * @param charset   字符编码
     * @param publicKey 公钥 商户的公钥
     * @return
     * @throws Exception
     */
    protected boolean parseSign(String signStr, String charset, String publicKey, String content) throws Exception {
        PublicKey pubkey = getPublicKeyFromX509(SecurityInterfaceConstans.SIGN_TYPE_RSA,
                new ByteArrayInputStream(publicKey.getBytes()));
        Signature signature = Signature.getInstance(getSignAlgorithm());
        signature.initVerify(pubkey);
        // base64 解码
        byte[] bytes = Base64.decodeBase64(signStr);
        // 加载签名内容
        signature.update(content.getBytes(charset));
        // 验证签名
        if (signature.verify(bytes)) {
            return true;
        }
        return false;
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

}
