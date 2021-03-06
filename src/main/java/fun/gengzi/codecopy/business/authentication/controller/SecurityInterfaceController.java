package fun.gengzi.codecopy.business.authentication.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import fun.gengzi.codecopy.business.authentication.entity.*;
import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
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

    // AES 密钥
    @Value("${token.aeskey}")
    private String aeskey;

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
     * <p>
     * <p>
     * 合法用户（本系统的人）   不合法用户（任何人）
     * <p>
     * <p>
     * 分析了 微博登陆的流程：
     * 账号 base64 加密 密码  几个参数拼在一起 rsa 加密（公钥加密）
     * <p>
     * 解决问题，避免中间人获取用户真实账号 和密码
     * <p>
     * 支付宝支付流程
     * 先使用客户端密钥加密，请求的参数，生成签名。签名字段和请求参数一起到后台。
     * 支付宝使用客户端公钥解密，比对请求参数是否一致，一致说明是 真实的客户发送的请求
     * <p>
     * 支付宝完成业务，返回数据，使用支付宝的私钥加密，生成签名，调用客户端接口
     * 客户端拿到支付宝公钥，解析签名，比对响应参数，成功后，执行自己的业务逻辑
     * <p>
     * 保证请求响应中，检测数据不会被篡改。
     * <p>
     * 在现有业务中
     * <p>
     * <p>
     * <p>
     * 防止接口参数修改后，能够一直调用该接口，只有是从服务器返回的数据和签名才能请求该接口
     * <p>
     * 对接口的响应数据，进行签名。当下次提交数据，再将签名回传，校验请求参数是否跟签名一致。
     * <p>
     * <p>
     * 防止越权访问
     * 本用户只能查询本用户下的数据，不允许查询到别人的数据。
     * 所以在设计接口的时候，要考虑前端请求的参数，是否能请求到不属于 本用户的数据。
     * <p>
     * 比如根据身份证号查询数据，如果循环身份证号，那就可以把整个系统的所有数据都查到。
     * 增加 用户登陆，验证码等方法，保证用户鉴权后，接口才能被调用，而且限定查询中，只能查询当前用户的数据
     * <p>
     * <p>
     * 在一些公共接口中，只验证了一次用户的手机号。在前端页面是没有问题的，但是一旦用户调用接口，在没有权限判断的情况下，
     * 接口就是公开透明的。即使是公开的接口，在验证用户的手机号后，依然将用户信息添加到 session中，对于前后端分离的项目
     * 可以把用户信息，放在redis，返回token 给前端，当接下来的所有请求，都需要携带token 来请求，每次进行的操作，都去redis中
     * 拿用户数据，检查是否查询是本用户可查询的数据等。
     * <p>
     * <p>
     * 参数循环，获取整个数据
     * <p>
     * 当用户登陆后，对一些公共接口，谁都可以查询的接口。也要关注，避免因为修改请求参数，导致一些额外数据泄露
     * 比如 查询活动列表接口，当接口中参数是可被循环的，比如 活动id=33 ，当我循环了这个 活动id ，有可能就把
     * 系统中，还未上线的活动 也都查询出来。导致未来的一些计划被人知道。
     * <p>
     * 对于接口返回敏感信息的加密处理。防止泄露数据。 aes
     * <p>
     * <p>
     * <p>
     * 所有安全措施的目的不都是提高破解成本
     * <p>
     * 爬虫反爬虫是一种博弈，随着系统的反爬虫的升级，系统的复杂性会越来越高，成本就会越高，当成本大大高于，被爬取数据信息的成本。
     * 过度的设计，反倒得不偿失。
     * <p>
     * 爬虫操作，大多数情况下，认为是一种合法用户对于系统的数据过度获取。
     * <p>
     * <p>
     * 数据加密，防止撞库
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * https://github.com/JetLei/MblogLoginDemo/blob/323a93c7bbe9406946040f99f5e11c77b82cdd7b/encrypt.js
     * <p>
     * 可以参考微博的
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


        // 前端将后台的 rsa 公钥，对请求参数的拼接进行加密，密文 携带请求参数 json 请求接口

        // 后台拿 rsa 密钥，进行解密。解密后对比请求参数是否一致，一致说明 参数没有被修改。

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


    /**
     * 请求参数：
     * <p>
     * {
     * "buyer_id": "202008041411269735742",
     * "goods_detail": "88.88",
     * "out_trade_no": "Phone+Xs+Max+256G",
     * "seller_id": "2088102175953034",
     * "subject": "ss",
     * "total_amount": "2088102175107499"
     * }
     *
     * @param orderInfoEntity
     * @return
     */
    @ApiOperation(value = "服务与服务之间参数的加密与解密-第一步", notes = "服务与服务之间参数的加密与解密- " +
            "演示一个支付流程（参考支付宝支付流程对于数据的加密与解密）本接口演示商户客户端发送请求到支付宝（不是真正的调用支付宝）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "OrderInfoEntity", value = "请求参数实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/payMoney")
    @ResponseBody
    public ReturnData payMoney(@RequestBody OrderInfoEntity orderInfoEntity) {
        // 支付流程 分商户和支付宝 两个服务
        // 准备一套商户RSA 私钥公钥，准备一套支付宝 RSA 私钥公钥
        // 将支付宝公钥发给商户，商户公钥发给支付宝
        // 商户准备数据，通过自己的私钥加密，生成签名字段， 发往支付宝
        // 支付宝通过商户的公钥解密，比对请求参数，是否一致，一致后，处理业务
        // 支付宝自身业务处理完，返回数据，使用支付宝私钥加密，生成签名字段，
        // 商户通过支付宝公钥解密，比对响应参数，是否一致，一致后，处理自身业务。
        // 完成
        logger.info("用户请求商户，发起付款");
        logger.info("商户调用支付宝，发起付款");
        securityInterfaceService.sendSignAndDataInfoToZFB(orderInfoEntity);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    /**
     * 请求参数
     * <p>
     * {"biz_content":"{\"buyer_id\":\"2020080414112697357422\",\"goods_detail\":\"88.88\",\"out_trade_no\":\"Phone+Xs+Max+256G\",\"seller_id\":\"2088102175953034\",\"subject\":\"ss\",\"total_amount\":\"2088102175107499\"}","notify_url":"http://localhost:8089/api/v2/payMoneyResponse","sign":"XrUN996fT1e5tP4nLj5tDaXVJNd1rkBY+OP/qgg2yVt7EGZ04jwi/lrEufq0EQP1n7r1YlEA8LDIIYt4k4hthXaL+W81gv3M57fwTLW6oE77uN/n0e/eWah8ekKsSIm9MqcGzCAzNs2xamKRzNl0bJHno2kEbMbpJH3FL0mS25w=","timestamp":"1596535463426"}
     *
     * @param mustParamEntity
     * @return
     */
    @ApiOperation(value = "服务与服务之间参数的加密与解密-第二步", notes = "服务与服务之间参数的加密与解密- " +
            "演示一个支付流程（参考支付宝支付流程对于数据的加密与解密）本接口演示支付宝接受商户请求，并回调商户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MustParamEntity", value = "请求参数实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/payMoneyByZFB")
    @ResponseBody
    public ReturnData payMoneyByZFB(@RequestBody MustParamEntity mustParamEntity) {
        logger.info("支付宝接受商户请求");
        logger.info("mustParamEntity : {}", mustParamEntity.toString());
        logger.info("mustParamEntity to json : {} ", JSONObject.toJSONString(mustParamEntity));
        // 验签
        boolean sign = securityInterfaceService.responseSignAndDataInfoToSH(mustParamEntity);
        if (sign) {
            logger.info("用户使用支付宝付款");
            String biz_content = mustParamEntity.getBiz_content();
            OrderInfoEntity orderInfoEntity = JSONUtil.toBean(biz_content, OrderInfoEntity.class);
            String total_amount = orderInfoEntity.getTotal_amount();
            BigDecimal bigDecimal = new BigDecimal(total_amount);
            //TODO 修改金额这个参数，响应
            // 手续费
            BigDecimal fee = BigDecimal.valueOf(0.04);
            bigDecimal.add(fee);
            orderInfoEntity.setTotal_amount(bigDecimal.toString());
            String new_biz_content = JSONObject.toJSONString(orderInfoEntity);
            // 回调
            logger.info("完成付款，支付宝回调商户接口，通知商户，用户已经付款");
            securityInterfaceService.sendSignAndDataInfoToSH(mustParamEntity);
        }
        ReturnData ret = ReturnData.newInstance();
        ret.setFailure("error");
        return ret;
    }

    @ApiOperation(value = "服务与服务之间参数的加密与解密-第三步", notes = "服务与服务之间参数的加密与解密- " +
            "演示一个支付流程（参考支付宝支付流程对于数据的加密与解密）本接口演示商户接收支付宝请求，完成对自己的业务处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "MustParamEntity", value = "请求参数实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/payMoneyBySH")
    @ResponseBody
    public ReturnData payMoneyBySH(@RequestBody MustParamEntity mustParamEntity) {
        logger.info("商户接收支付宝通知信息");
        ReturnData ret = ReturnData.newInstance();
        logger.info("mustParamEntity : {}", mustParamEntity.toString());
        logger.info("mustParamEntity to json : {} ", JSONObject.toJSONString(mustParamEntity));
        // 验签
        boolean sign = securityInterfaceService.responseSignAndDataInfoToZFB(mustParamEntity);
        if (sign) {
            // TODO 验签成功，执行自身业务
            logger.info("为该用户办理业务，发货");
            ret.setSuccess();
            return ret;
        }
        ret.setFailure("失败，请稍后再试！");
        return ret;
    }


    @ApiOperation(value = "前端-后端：提交参数的加密与解密", notes = "前端-后端：提交参数的加密与解密" +
            "演示前端js将提交参数加密")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/paramEncryptionToJS")
    @ResponseBody
    public ReturnData paramEncryptionToJS() {
        securityInterfaceService.paramEncryptionToJs();
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    /**
     * 其实前端对参数的加密，来保证安全，就好像是一个纸老虎。但是依然能提高接口的安全程度。
     * <p>
     * 敏感数据加密后，攻击者无法仅通过网络抓包来详细了解敏感数据的内容。
     * 为了让这个纸老虎更加逼真，对前端js 混淆，添加一些无效参数，记录用户行为，将这些组合在一起，提升攻击者的难度。
     * 可能攻击者分析分析着，就放弃了。
     * <p>
     * 该接口演示，使用微博登陆的加密js，对用户名和密码进行加密处理，并提交至后台。
     *
     * @return
     */
    @ApiOperation(value = "前端-后端：提交参数的加密与解密", notes = "前端-后端：提交参数的加密与解密" +
            "演示后台解密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ParamEncryptionEntity", value = "ParamEncryptionEntity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/paramEncryption")
    @ResponseBody
    public ReturnData paramEncryption(@RequestBody ParamEncryptionEntity paramEncryptionEntity) {
        // 编写加密js 的function 方法
        // java 调用这些js 方法，执行加密操作，发送ajax 请求
        // 前端js混淆，引入额外参数，替换页面的原属性值
        // 后台接受 ajax 请求，获取参数，将参数解密处理，执行具体业务
        logger.info("paramEncryptionEntity : {}", paramEncryptionEntity.toString());

        String nonce = paramEncryptionEntity.getNonce();
        String su = Base64.decodeStr(paramEncryptionEntity.getSu());
        logger.info("su : {}", su);

        // 跟 nonce 获取 publickey 省略
        Optional<String> sp = RSAUtils.decryptByPublic(paramEncryptionEntity.getSp(), secretkey);
        logger.info("sp : {}", sp.orElse(""));
        // 比对参数
        String signStr = sp.orElse("");
        String password = null;
        if (signStr.contains(paramEncryptionEntity.getServiceTime() + "\t" + paramEncryptionEntity.getNonce() + "\n")) {
            String[] split = signStr.split("\n");
            password = split[split.length - 1];
        }

        //TODO 与数据库比对账号密码，一致登陆成功
        logger.info("username :{} and password : {}", su, password);


        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    /**
     * 防止越权访问常用策略：
     * 对于鉴权用户，对于所查询的数据，验证一切来自客户端的参数，重点是权限相关的参数，比如用户id，或者角色权限id 等
     * 将 sessin id 和 认证token 做绑定，放在服务器的会话中，不发生给客户端。
     * <p>
     * 也就是，对于关乎于不同用户的数据，不能出现本用户，查询到其他用户的数据。
     * 如果通过session 或者 token 控制，很简单。 在查询的数据前，校验前端请求的参数是否是 本用户的数据，如果是 允许查询，不是拒绝。
     * 例子，比如前端参数中有，用户id这个参数，到后台后，如果不做判断，当修改用户id ，将会查询到其他用户的数据。
     * 所以在设计上应该避免，前端传递 用户id 等参数，而是通过 sessionid 或者 token 来获取当前请求用户的用户id ，执行业务操作。
     * <p>
     * 但是假如说，在之前的业务中，存在必须使用关乎权限的参数，请求后台，就有可能出现越权访问。
     * 比如,前台传递了订单id，有一个接口可以根据订单id，查询订单信息。如果这个接口，没有进行鉴权操作。可能会出现，循环订单id，查询到其他订单的数据
     * 有一个办法，在返回订单id的时候，加密处理。在查询订单信息的时候，再解密订单id，防止 循环订单id
     *
     * @return
     */
    @ApiOperation(value = "防止越权访问信息", notes = "防止越权访问信息")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/preventUnauthorizedAccess")
    @ResponseBody
    public ReturnData preventUnauthorizedAccess() {
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    @ApiOperation(value = "针对查询接口，返回脱敏和需要加密的信息和签名字段", notes = "针对查询接口，返回脱敏和需要加密的信息和签名字段")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/qryUserInfo")
    @ResponseBody
    public ReturnData qryUserInfo() {
        // TODO 执行业务，返回数据
        ShowUserInfoVo showUserInfoVo = new ShowUserInfoVo();
        showUserInfoVo.setUserName("张三");
        showUserInfoVo.setAddress("北京市朝阳");
        showUserInfoVo.setBankCard("9559480089071474413");
        showUserInfoVo.setIdCard("410329188905068899");
        showUserInfoVo.setEmail("11640@qq.com");
        showUserInfoVo.setPhone("14535634523");
        // 加密 idCard 字段
        Optional<String> encrypt = AESUtils.encrypt(showUserInfoVo.getIdCard(), aeskey);
        showUserInfoVo.setIdkey(encrypt.orElse("error"));
        // 摘要信息，假如在其他接口，需要用到这个接口的返回信息，为了保证数据的完整性和正确性，将某几个字段组拼生成摘要，在下个接口中验证
        // 这里将 手机号 和 身份证id 加盐处理
        String sign = securityInterfaceService.signFields(SecurityInterfaceController.class.getName(), showUserInfoVo.getIdCard(), showUserInfoVo.getPhone());
        showUserInfoVo.setSignkey(sign);
        // 敏感数据的脱敏
        ShowUserInfoVo maskObj = (ShowUserInfoVo) BeanDataMaskUtils.maskObj(showUserInfoVo);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(maskObj);
        return ret;
    }


    /**
     * {
     * "userName": "*三",
     * "phone": "145*****523",
     * "email": "1***0@qq.com",
     * "bankCard": "9559*************13",
     * "address": "北****",
     * "idCard": "41032***********99",
     * "idkey": "DYJjryX7Bv3iQ3CY/qGFhwHuBKjsrSCf6y0C84MLq8s=",
     * "signkey": "b87d8b5d855568b551aa5674af12fda9"
     * }
     *
     * @param showUserInfoVo
     * @return
     */
    @ApiOperation(value = "提交接口，验证签名,解密数据", notes = "提交接口，验证签名，解密数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShowUserInfoVo", value = "ShowUserInfoVo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/commitInfo")
    @ResponseBody
    public ReturnData commitInfo(@RequestBody ShowUserInfoVo showUserInfoVo) {
        logger.info("ShowUserInfoVo ：{}", showUserInfoVo);
        ReturnData ret = ReturnData.newInstance();
        String signkey = showUserInfoVo.getSignkey();
        if (StringUtils.isBlank(signkey)) {
            throw new RrException(RspCodeEnum.ERROR.getDesc(), RspCodeEnum.ERROR.getCode());
        }
        // 解密身份证数据
        String idkey = showUserInfoVo.getIdkey();
        Optional<String> decrypt = AESUtils.decrypt(idkey, aeskey);
        showUserInfoVo.setIdCard(decrypt.orElse("error"));
        logger.info("解密后的IdCard：{}",showUserInfoVo.getIdCard());
        // 验证签名
        boolean flag = securityInterfaceService.checkSignField(signkey, SecurityInterfaceController.class.getName(), showUserInfoVo.getIdCard(), showUserInfoVo.getPhone());
        if (!flag) {
            ret.setFailure("参数校验失败，请重新尝试");
        }
        ret.setSuccess();
        ret.setMessage("校验成功，返回数据");
        return ret;
    }

}
