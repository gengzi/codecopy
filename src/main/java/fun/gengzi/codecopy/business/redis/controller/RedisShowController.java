package fun.gengzi.codecopy.business.redis.controller;

import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * redis 命令参考文档：https://www.redis.net.cn/tutorial/3510.html
 *
 *
 *
 */
@Api(value = "Redis实战", tags = {"Redis实战"})
@Controller
@RequestMapping("/redisShow")
public class RedisShowController {

    private Logger logger = LoggerFactory.getLogger(RedisShowController.class);


    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "定时变化排行榜", notes = "定时变化排行榜")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/timedChangeLeaderboard")
    @ResponseBody
    public ReturnData timedChangeLeaderboard(@RequestParam("code") String code) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");
        arrayList.add("wangwu");
        boolean b = redisUtil.lSet(code, arrayList, 3000);
        // 获取该 list 所有数据
        List<Object> redisList = redisUtil.lGet(code, 0, -1);

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(redisList);
        return ret;
    }


}
