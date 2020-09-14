package fun.gengzi.codecopy.business.luckdraw.controller;

import fun.gengzi.codecopy.business.luckdraw.algorithm.LuckdrawAlgorithlm;
import fun.gengzi.codecopy.business.luckdraw.aop.LuckdrawServiceLimit;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.business.luckdraw.entity.*;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.business.luckdraw.vo.VerificationVo;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一些问题：
 * 对于初始化每个用户的积分数，考虑登陆一个用户为该用户在积分表创建初始化积分，并加入缓存
 * <p>
 * 对于活动id，等可以缓存到本地缓存
 * 如果临时需要修改活动信息，比如时间范围，或者停止活动。 可能需要清除缓存，并重新加载缓存  考虑使用 配置中心，noars
 * 是否会出现缓存穿透，缓存击穿，缓存雪崩
 * <p>
 * <p>
 * 对于活动用户积分信息，这张表应该会特别的大，并且执行次数也会多，考虑一个活动一张表，不能将所有的活动用户积分信息存到一张表中
 * <p>
 * 对于减积分，减库存，记录发奖，应该设置一个 事物中，如果有一个失败，及时回滚。如果这几个抽离成几个服务，考虑使用分布式事务
 * 控制积分，库存字段，数据库层必须大于0
 * 设置数据库字段为 unsigned
 * 比如 `integral` int(11) unsigned DEFAULT NULL COMMENT '积分',
 * `prize_num` int(11) unsigned DEFAULT NULL COMMENT '奖品数目',
 *
 *
 *
 *
 * <p>
 * 对于抽奖逻辑，是否要加锁。加锁的目的在于，同时多个线程对某个资源的使用，为了防止出现  超过自己的抽奖次数，减库存超过原有库存数，
 * 将资源加锁，当一个线程执行完，再执行下一个线程。 这里使用 mysql的乐观锁，在更新语句中，设置 数量的减少不能小于0
 * <p>
 * 对于大量的请求，是否要增加消息队列，削峰填谷。 增加了消息队列，整个流程变为 异步，前端不能直接拿到抽奖结果，需要轮询查询抽奖结果接口
 * 返回抽奖结果信息。 那么就要考虑怎么保证队列，消息不丢失，消息不重复，消费者应该要做 幂等性校验，帮确保这个消息，只产生了一次
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

    /**
     * 抽奖方法
     * 优化事物，当任意事物失败，直接结束，注意事物失效的一些情况
     * 增加事物测试，增加数据库乐观锁测试
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
    public ReturnData start(@RequestParam("aid") String aid, @PathVariable("path") String path, HttpServletRequest request) {
        logger.info("luckdraw quest param ,aid:{} ", aid);
        ReturnData ret = ReturnData.newInstance();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        TbPrize luckdraw = luckdrawService.luckdraw(aid, token);
        if (luckdraw == null || luckdraw.getId() == 0) {
            ret.setInfo("未中奖哦，再抽一次吧！");
        } else {
            ret.setInfo(luckdraw.getPrizeName());
        }
        ret.setSuccess();
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
            @ApiImplicitParam(name = "VerificationVo", value = "VerificationVo", required = true)})
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
        if (verificationVo != null && StringUtils.isAnyBlank(verificationVo.getPhone(), verificationVo.getValidCode())) {
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        }
        // TODO 默认校验成功
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
            }
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        } else {
            ret.setFailure(LuckdrawEnum.ERROR_VALIDCODE.getMsg());
            return ret;
        }

    }


}

