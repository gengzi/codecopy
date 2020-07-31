package fun.gengzi.codecopy.business.authentication.controller;

import com.alibaba.fastjson.JSONObject;
import fun.gengzi.codecopy.business.authentication.entity.ParamFieldEntity;
import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.business.authentication.entity.SignEntity;
import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * <h1>接口安全实践</h1>
 * <p>
 * 业务场景： 短信验证码
 *
 * @author gengzi
 * @date 2020年7月30日16:37:41
 */
@Api(value = "接口安全", tags = {"接口安全"})
@Controller
@RequestMapping("/api/v2")
public class SecurityInterfaceController {
    private Logger logger = LoggerFactory.getLogger(SecurityInterfaceController.class);
    // rsa 公钥
    private static final String publickey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALbcdLKOtTKOjalffv/LLLOqfyh8Ep4XHjvOivMU3Nb1N0puG4+NTrXBS8GDczgsZ+7J6D7FTcH8JInMKpz85LMCAwEAAQ==";
    // rsa 密钥
    private static final String secretkey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAttx0so61Mo6NqV9+/8sss6p/KHwSnhceO86K8xTc1vU3Sm4bj41OtcFLwYNzOCxn7snoPsVNwfwkicwqnPzkswIDAQABAkBGw9Xda+Cvaf9kdnJdZzErbmW7Mxi5WVT37BxVqdM01BTjudKSADlLn53fEeWl7pmfMkMuXZ7uPNdqmLWVLMNxAiEA6LXvDTKtEZNyTvjXs4nDJweiIT9kZtZmYD3hVcQueJUCIQDJKV+PcdKehVw8U+hdeE4/NZDFCHRzaGM4Zs5YRRbuJwIgSG0fSn9EKB04zWVbVNCCgWo5xplBOVRvJnL758KYKAUCIDdpmzZDb3ZVXCwOHRMqYbuNwNxV0OY9mh9eSncMSR2/AiEApSModT03Kr+nHxhgzAyOvzLcKE0IPMJ+ny3mjdyBjWc=";

    @Autowired
    private SecurityInterfaceService securityInterfaceService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 反爬虫，不透露接口入参真实值，防止中间人截取 真实数据
     * <p>
     * 模拟用户在页面提交数据到后台接口流程
     * <p>
     * // 使用java8 js引擎，执行加密js，将参数加密，提交至后台接口
     * // 后台接口解密，获取真实数据，执行业务
     * <p>
     * <p>
     * 请求：
     * 1. 服务器端(server)和客户端(client)分别生成自己的密钥对
     * 2. server和client分别交换自己的公钥
     * 3. client生成AES密钥(aesKey)
     * 4. client使用自己的RSA私钥(privateKey)对请求明文数据(params)进行数字签名
     * 5. 将签名加入到请求参数中，然后转换为json格式
     * 6. client使用aesKey对json数据进行加密得到密文(data)
     * 7. client使用sever的RSA公钥对aesKey进行加密(encryptkey)
     * 8. 分别将data和encryptkey作为参数传输给服务器端
     *
     * @return
     */
    @ApiOperation(value = "接口参数加密与解密", notes = "接口参数加密与解密")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/testInteface")
    @ResponseBody
    public ReturnData testInteface() {
        //前端 一套 rsa 公钥密钥  后台 一套 rsa 公钥 密钥
        // 前端请求参数 使用自己的私钥加密，作为签名。
        // 将签名 和 请求参数 使用 aes 加密 得到 密文
        // 前端将 后台的 rsa 公钥 对 aes key 进行加密


        // 后台接受参数， 先那自己的 密钥，对 加密的aes 进行解密,得到 aes key
        // 拿着 aes key 对data 进行解密，得到json数据
        // 拿着 前端的公钥，对签名字段，进行解密，得到请求参数 ， 对比 json 中的请求参数 ，是否一致。
        // 一致后，表示接口参数未被修改。

        final Map<String, String> rsaSecretkeyAndPublickey = securityInterfaceService.getRSASecretkeyAndPublickey();
        final String aeSkey = securityInterfaceService.getAESkey();
        final String secrekeyStr = rsaSecretkeyAndPublickey.getOrDefault("secretkey", secretkey);
        final String publickeyStr = rsaSecretkeyAndPublickey.getOrDefault("publickey", publickey);
        // 拼接前端参数
        final ParamFieldEntity paramFieldEntity = new ParamFieldEntity();
        paramFieldEntity.setCode("1");
        paramFieldEntity.setImageUrl("/img/1.png");
        paramFieldEntity.setPhoneNum("12135");
        paramFieldEntity.setReqNum(String.valueOf(new Random().nextInt()));

        String needSignData = ParamFieldEntity.toHttpGetParam(paramFieldEntity);
        logger.info("待加密的data: {}", needSignData);

        String s = securityInterfaceService.encryptJSByClientSecrekeyStr(needSignData, secrekeyStr).orElse("");
        logger.info("JS加密后的data: {}", s);


        String sign = RSAUtils.encrypt(needSignData, secrekeyStr).orElseThrow(() -> new RrException("加密失败", RspCodeEnum.RSA_FAULURE.getCode()));
        logger.info("加密后的data: {}", sign);
        paramFieldEntity.setSign(sign);

        String jsonData = JSONObject.toJSONString(paramFieldEntity);
        logger.info("待加密的data: {}", jsonData);
        String data = AESUtils.encrypt(jsonData, aeSkey).orElseThrow(() -> new RrException("加密失败", RspCodeEnum.AES_FAULURE.getCode()));
        logger.info("加密后的data: {}", data);

        final Map<String, String> rsaSecretkeyAndPublickeyByServer = securityInterfaceService.getRSASecretkeyAndPublickey();
        final String secrekeyStrByServer = rsaSecretkeyAndPublickeyByServer.getOrDefault("secretkey", secretkey);
        final String publickeyStrByServer = rsaSecretkeyAndPublickeyByServer.getOrDefault("publickey", publickey);
        logger.info("publickeyStrByServer: {}", publickeyStrByServer);
        String encryptkey = RSAUtils.encryptByPublic(aeSkey, publickeyStrByServer).orElseThrow(() -> new RrException("加密失败", RspCodeEnum.RSA_FAULURE.getCode()));

        SignEntity signEntity = new SignEntity(encryptkey, data);
        logger.info(signEntity.toString());

        ReturnData ret = ReturnData.newInstance();
        ret.setFailure("failure");
        return ret;

        // 前端提交参数 不是敏感数据，不需要加密
        // 后台响应结果，结果中的数据，需要作为下个接口的入参，下个接口要求，提交的参数必须符合规则
        // 后台响应结果时，对字段进行了md5 签名，当下次提交如果参数被修改，生产的签名内容和提交的签名不一致。拒绝访问接口
        // 比如 接口是发短信的接口，提交参数有 手机号，业务类型 ，如果修改了业务类型 ，如果在指定范围的业务类型中，将会为这个手机号一直发短信

        // 对于一些敏感信息，加密后再返回。比如手机号，身份证号，有可能有些人无意看到接口返回结果。   这些参数可能作为一些接口的入参，修改了某些参数将会影响最终的结果。
        // 比如

    }

}
