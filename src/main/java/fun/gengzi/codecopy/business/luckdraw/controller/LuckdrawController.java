package fun.gengzi.codecopy.business.luckdraw.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import fun.gengzi.codecopy.business.luckdraw.algorithm.LuckdrawAlgorithlm;
import fun.gengzi.codecopy.business.luckdraw.aop.LuckdrawServiceLimit;
import fun.gengzi.codecopy.business.luckdraw.config.UserSessionThreadLocal;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.business.luckdraw.entity.*;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.business.luckdraw.vo.VerificationVo;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 一些问题：
 * 对于初始化每个用户的积分数，考虑登陆一个用户为该用户在积分表创建初始化积分，并加入缓存
 * <p>
 * 对于活动id，等可以缓存到本地缓存
 * 如果临时需要修改活动信息，比如时间范围，或者停止活动。 可能需要清除缓存，并重新加载缓存  考虑使用 配置中心，noars
 * 是否会出现缓存穿透，缓存击穿，缓存雪崩
 * <p>
 * <p>
 * 对于活动用户积分信息，这张表应该会特别的大，并且执行次数也会多，考虑一个活动一张表，不能将所有的活动用户积分信息存到一张表中，或者做分表处理
 * <p>
 * 对于减积分，减库存，记录发奖，应该设置一个 事物中，如果有一个失败，及时回滚。如果这几个抽离成几个服务，考虑使用分布式事务
 * 控制积分，库存字段，数据库层必须大于0
 * 设置数据库字段为 unsigned
 * 比如 `integral` int(11) unsigned DEFAULT NULL COMMENT '积分',
 * `prize_num` int(11) unsigned DEFAULT NULL COMMENT '奖品数目',
 * 数据库乐观锁的控制
 *
 *
 * <p>
 * 对于抽奖逻辑，是否要加锁。加锁的目的在于，同时多个线程对某个资源的使用，为了防止出现  超过自己的抽奖次数，减库存超过原有库存数，
 * 将资源加锁，当一个线程执行完，再执行下一个线程。 这里使用 mysql的乐观锁，在更新语句中，设置 数量的减少不能小于0
 * 对于分布式系统，使用分布式锁，控制一次只有一个线程，触发抽奖方法
 * <p>
 * 对于大量的请求，是否要增加消息队列，削峰填谷。 增加了消息队列，整个流程变为 异步，前端不能直接拿到抽奖结果，需要轮询查询抽奖结果接口
 * 返回抽奖结果信息。 那么就要考虑怎么保证队列，消息不丢失，消息不重复，消费者应该要做 幂等性校验，帮确保这个消息，只产生了一次
 * 如果加入了消息队列，在消费端，是不是可以不用加锁处理，消息队列保证消息的顺序消费 这个有疑问
 * <p>
 * 是否要限制用户在不同网页端的登陆，对于同一用户更容易出现，超卖情况
 * <p>
 * 对于频繁刷接口的ip，应该建立风控服务，检测某些是否为高仿ip，记录恶意ip，建立黑名单
 * <p>
 * 是否要隐藏抽奖真实接口，可以依靠 用户id 活动id md5 加密的签名，作为路径在后端检验，来隐藏真实的 路径地址
 * <p>
 * 针对运营，应该存在多种 抽奖策略方案，怎么样设计，才能应对不同活动的策略
 * <p>
 * 对于用户积分表，记录获奖信息表，是否要考虑，分库分表，数据量的上升是飞快的
 * <p>
 * 对于一些记录获奖信息完成，发送短信邮件通知用户，可以异步
 * <p>
 * 注意锁和事物的顺序性，都使用锁上移
 * 现在可能存在的问题， 缓存与真实数据不一致
 * 在该项目中的处理，缓存仅作为 加快信息获取的一种方式，不作为数据真实的方式。
 * 但是在某些情况下，比如 缓存扣除积分成功，数据库扣除积分失败。 可以直接返回没有抽到奖品。但是下次用户再进来，
 * 重新刷新了缓存积分，用户会发现多了一次的抽奖机会
 * <p>
 * <p>
 * redis 预减库存，会出现负数
 * <p>
 * 2020年9月24日17:05:05
 * activity 的字段，考虑也放入 本地线程信息中，方便这些字段的使用
 * 对于页面的跳转，尽管有些页面是需要加权限限制的
 */

/**
 * <h1>luckdraw</h1>
 *
 * @author gengzi
 * @date 2020年9月7日14:56:38
 */
@Api(value = "luckdraw", tags = {"luckdraw"})
@Controller
@RequestMapping("/luckdraw")
public class LuckdrawController {


    /**
     * 发起抽奖接口，返回抽奖结果
     * 查询当前用户的抽奖记录和发放记录
     * 查看当前用户获得的奖品
     */
    private Logger logger = LoggerFactory.getLogger(LuckdrawController.class);

    @Autowired
    @Qualifier("DefaultLuckdrawAlgorithlm")
    private LuckdrawAlgorithlm luckdrawAlgorithlm;

    @Autowired
    private LuckdrawService luckdrawService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取我的奖品信息", notes = "获取我的奖品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/start/getMyPrizeInfo")
    @ResponseBody
    public ReturnData getMyPrizeInfo(@RequestParam("aid") String aid, HttpServletRequest request) {
        logger.info("获取我的奖品信息，入参:{}", aid);
        // 从token 中获取用户信息
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userinfokey = String.format(LuckdrawContants.USERINFOKEY, token);
        SysUser sysUser = (SysUser) redisUtil.get(userinfokey);
        List<MyAwardeeVo> myPrizeInfo = luckdrawService.getMyPrizeInfo(aid, sysUser.getUid());
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setInfo(myPrizeInfo);
        logger.info("获取我的奖品信息，出参:{}", myPrizeInfo);
        return ret;
    }

    @ApiOperation(value = "获取当前的获奖人信息", notes = "获取当前的获奖人信息，返回五条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/getAwardeeInfo")
    @ResponseBody
    @LuckdrawServiceLimit(limitType = LuckdrawServiceLimit.LimitType.IP)
    public ReturnData getAwardeeInfo(@RequestParam("aid") String aid) {
        logger.info("获取当前活动获奖人最新信息，入参:{}", aid);
        List<AwardeeVo> awardeeInfo = luckdrawService.getAwardeeInfo(aid);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setInfo(awardeeInfo);
        logger.info("获取当前活动获奖人最新信息，出参:{}", awardeeInfo);
        return ret;
    }


    @ApiOperation(value = "获取活动对应的奖品信息", notes = "获取活动对应的奖品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/getPrizeInfo")
    @ResponseBody
    public ReturnData activityToPage(@RequestParam("aid") String aid) {
        List<TbPrize> prizeInfo = luckdrawService.getPrizeInfo(aid);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setInfo(prizeInfo);
        return ret;
    }

    @ApiOperation(value = "获取登陆随机验证码", notes = "获取登陆随机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true),
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/getLoginCode")
    @ResponseBody
    public String getLoginCode(@RequestParam("aid") String aid, @RequestParam("code") String code, HttpServletResponse response) {
        ReturnData ret = ReturnData.newInstance();
        if (StringUtils.isBlank(code)) {
            return "error";
        }
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        String verificationCode = captcha.getCode();
        // 存入redis中
        redisUtil.set(String.format(LuckdrawContants.VALIDCODEKEY, code), verificationCode, 180);
        //图形验证码写出，可以写出到文件，也可以写出到流
        return "data:image/png;base64," + captcha.getImageBase64();
    }

    @ApiOperation(value = "验证图片验证码", notes = "验证图片验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true),
            @ApiImplicitParam(name = "VerificationVo", value = "verificationVo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/checkLoginCode")
    @ResponseBody
    public ReturnData checkLoginCode(@RequestParam("aid") String aid, @RequestBody VerificationVo verificationVo) {
        ReturnData ret = ReturnData.newInstance();
        if (verificationVo != null && StringUtils.isAnyBlank(verificationVo.getCode(), verificationVo.getValidCode())) {
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        }
        // 仅校验一下随机验证码
        String validCodeByRedis = (String) redisUtil.get(String.format(LuckdrawContants.VALIDCODEKEY, verificationVo.getCode()));
        if (!verificationVo.getValidCode().equals(validCodeByRedis)) {
            ret.setFailure(LuckdrawEnum.ERROR_PAGE_VALIDCODE.getMsg());
            return ret;
        }
        //TODO 发送短信验证码
        ret.setSuccess();
        return ret;
    }


    /**
     * 本地地址
     * http://localhost:8089/luckdraw/activity?aid=hd_001
     * <p>
     * 联通抽奖地址
     * http://m.client.10010.com/dailylottery/view/dailylotteryshare.jsp?encryptusernumber=7d1300cffb536a6c3aea78b1aa175016&areaId=076&JiFenflag=2
     *
     * @param aid
     * @param request
     * @return
     */
    @ApiOperation(value = "跳转页面", notes = "跳转页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/activity")
    public String activityToPage(@RequestParam("aid") String aid, HttpServletRequest request) {
        logger.info("luckdraw quest param ,aid:{} ", aid);
        return "activityLogin";
    }

    /**
     * 本地地址
     * http://localhost:8089/luckdraw/activity?aid=hd_001
     * <p>
     * 联通抽奖地址
     * http://m.client.10010.com/dailylottery/view/dailylotteryshare.jsp?encryptusernumber=7d1300cffb536a6c3aea78b1aa175016&areaId=076&JiFenflag=2
     *
     * @param aid
     * @param request
     * @return
     */
    @ApiOperation(value = "跳转页面", notes = "跳转页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @GetMapping("/activityMain")
    public String activityToPageLuckdraw(@RequestParam("aid") String aid, HttpServletRequest request) {
        return "startluckdraw";
    }


    /**
     * 抽奖方法
     * 梳理本地缓存和redis缓存的使用地方，看是否会出现缓存穿透，缓存雪崩，缓存击穿
     * 考虑实现前端页面，了解前端页面的静态化，cdn加速等
     *
     * @param aid
     * @param request
     * @return
     */
    @ApiOperation(value = "luckdraw", notes = "luckdraw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TbActivity", value = "TbActivity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/start")
    @ResponseBody
    @LuckdrawServiceLimit(limitType = LuckdrawServiceLimit.LimitType.IP)
    public ReturnData start(@RequestParam("aid") String aid, HttpServletRequest request) {
        logger.info("luckdraw quest param ,aid:{} ", aid);
        ReturnData ret = ReturnData.newInstance();
        TbPrize luckdraw = luckdrawService.luckdraw(aid);
        // 移除ThreadLocal 持有的用户信息
        UserSessionThreadLocal.removeUser();
        ret.setSuccess();
        if (luckdraw == null || luckdraw.getId() == 0) {
            ret.setInfo("积分不足哦！");
        } else {
            final PrizeVo prizeVo = new PrizeVo();
            BeanUtils.copyProperties(luckdraw, prizeVo);
            ret.setInfo(prizeVo);
        }
        return ret;
    }


    /**
     * 引入消息队列的版本
     *
     * @param aid
     * @param request
     * @return
     */
    @ApiOperation(value = "luckdraw_mq", notes = "luckdraw_mq")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TbActivity", value = "TbActivity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/start/mq")
    @ResponseBody
    @LuckdrawServiceLimit(limitType = LuckdrawServiceLimit.LimitType.IP)
    public ReturnData startByKafka(@RequestParam("aid") String aid, HttpServletRequest request) {
        logger.info("startByKafka quest param ,aid:{} ", aid);
        ReturnData ret = ReturnData.newInstance();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        LuckdrawByMqEntity luckdrawByMqEntity = luckdrawService.luckdrawByMq(aid, token);
        if (luckdrawByMqEntity != null && luckdrawByMqEntity.getLuckdrawEnum().equals(LuckdrawEnum.SUCCESS_DEFAULT)) {
            // 消息已上送到mq
            long currentTime = luckdrawByMqEntity.getCurrentTime();
            ret.setSuccess();
            ret.setInfo(currentTime);
            ret.setStatus(luckdrawByMqEntity.getLuckdrawEnum().getCode());
        }
        return ret;
    }


    /**
     * 查询抽奖结果,mq 的形式
     *
     * @param aid
     * @param request
     * @return
     */
    @ApiOperation(value = "luckdraw_mq", notes = "luckdraw_mq")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TbActivity", value = "TbActivity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/start/qryLuckdrawResult")
    @ResponseBody
    public ReturnData qryLuckdrawResult(@RequestParam("aid") String aid, @RequestParam("currentTime") String currentTime, HttpServletRequest request) {
        logger.info("qryLuckdrawResult quest param ,aid:{},currentTime:{} ", aid, currentTime);
        ReturnData ret = ReturnData.newInstance();
        TbPrize myPrizeInfoByMq = luckdrawService.getMyPrizeInfoByMq(aid, currentTime);
        // 注意设置 success 和 设置 info 的顺序，前者的构造，是会替换info 的数据
        ret.setSuccess();
        final PrizeVo prizeVo = new PrizeVo();
        BeanUtils.copyProperties(myPrizeInfoByMq, prizeVo);
        ret.setInfo(prizeVo);
        return ret;
    }


    /**
     * 使用手机号，验证码来进入抽奖页面
     *
     * @param aid
     * @param verificationVo
     * @param response
     * @return
     */
    @ApiOperation(value = "验证用户信息", notes = "验证用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "VerificationVo", value = "VerificationVo", required = true),
            @ApiImplicitParam(name = "aid", value = "aid", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/verificationUserInfo")
    @ResponseBody
    @LuckdrawServiceLimit(limitType = LuckdrawServiceLimit.LimitType.IP)
    public ReturnData verificationUserInfo(@RequestParam("aid") String aid, @RequestBody VerificationVo verificationVo, HttpServletResponse response) {
        logger.info("VerificationVo :{} ", verificationVo);
        final ReturnData ret = ReturnData.newInstance();
        if (verificationVo != null && StringUtils.isAnyBlank(verificationVo.getPhone(), verificationVo.getValidCode(), verificationVo.getPhoneValidCode())) {
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        }
        // 仅校验一下随机验证码
        String validCodeByRedis = (String) redisUtil.get(String.format(LuckdrawContants.VALIDCODEKEY, verificationVo.getCode()));
        if (!verificationVo.getValidCode().equals(validCodeByRedis)) {
            ret.setFailure(LuckdrawEnum.ERROR_PAGE_VALIDCODE.getMsg());
            return ret;
        }
        // 移除随机验证码的缓存
        redisUtil.del(String.format(LuckdrawContants.VALIDCODEKEY, verificationVo.getCode()));

        // TODO 默认校验短信验证码成功
        boolean flag = true;
        // 根据手机号获取用户信息
        if (flag) {
            SysUserDTO userInfoByPhoneNum = luckdrawService.getUserInfoByPhoneNum(verificationVo.getPhone());
            if (userInfoByPhoneNum != null) {
                // 获取该用户对应该活动的积分信息
                TbIntegral integralInfo = luckdrawService.getIntegralInfo(aid, userInfoByPhoneNum.getUid());
                if (integralInfo != null) {
                    // 返回token 其他数据
                    final TokenInfoEntity tokenInfoEntity = new TokenInfoEntity();
                    tokenInfoEntity.setUname(userInfoByPhoneNum.getUname());
                    tokenInfoEntity.setIntegral(String.valueOf(integralInfo.getIntegral()));
                    // 设置响应头 token
                    response.setHeader(HttpHeaders.AUTHORIZATION, userInfoByPhoneNum.getToken());

                    ret.setSuccess();
                    ret.setInfo(tokenInfoEntity);
                    return ret;
                }
            } else {
                // 用户信息不存在修改为直接新增用户信息，初始化积分9000分
                SysUser sysUser = new SysUser();
                sysUser.setUid(verificationVo.getPhone());
                sysUser.setPhone(verificationVo.getPhone());
                sysUser.setUtype(1);
                sysUser.setUname(verificationVo.getPhone().substring(8, 11));
                sysUser.setCreatetime(new Date());
                SysUserDTO sysUserDTO = luckdrawService.initUserInfo(aid, sysUser);
                // 返回token 其他数据
                final TokenInfoEntity tokenInfoEntity = new TokenInfoEntity();
                tokenInfoEntity.setUname(sysUserDTO.getUname());
                tokenInfoEntity.setIntegral(String.valueOf(sysUserDTO.getIntegral()));
                // 设置响应头 token
                response.setHeader(HttpHeaders.AUTHORIZATION, sysUserDTO.getToken());
                ret.setSuccess();
                ret.setInfo(tokenInfoEntity);
                return ret;
            }
            ret.setFailure(LuckdrawEnum.ERROR_VALIDCODE.getMsg());
            return ret;
        } else {
            ret.setFailure(LuckdrawEnum.ERROR_VALIDCODE.getMsg());
            return ret;
        }
    }


    // 可以考虑增加 手动更新 奖品库存信息 并 更新缓存

    // 手动增加用户积分，并更新缓存信息

    // 查看抽奖信息


}

