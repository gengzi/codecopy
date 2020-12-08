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
 * <h1>Redis不同业务场景下的实践</h1>
 * <p>
 * <p>
 * redis 命令参考文档：https://www.redis.net.cn/tutorial/3510.html
 *
 * @author gengzi
 * @date 2020年12月8日16:06:56
 */
@Api(value = "Redis实战", tags = {"Redis实战"})
@Controller
@RequestMapping("/redisShow")
public class RedisShowController {

    private Logger logger = LoggerFactory.getLogger(RedisShowController.class);


    @Autowired
    private RedisUtil redisUtil;

    /**
     * 数组，查询快，增删慢  可以根据下标查询，增删，都可能会涉及 扩容和数据移动
     * 链表：增删快，查询慢  增删，只需要把前一个指向和后一个指向改变
     * <p>
     * List 类型
     * 底层 linkedList zipList
     * <p>
     * 定义了
     * 头结点
     * 尾节点
     * 长度
     * 这样，在进行 pop/push 或者获取 lien 长度，复杂度为 O(1)
     * 由于是链表 lindex 获取某个位置的元素值，复杂度为O(N)
     * <p>
     * <p>
     * <p>
     * <p>
     * 点赞列表 lpush  lrange
     * 评论列表
     * <p>
     * <p>
     * 分页获取的排行榜
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "List-定时变化排行榜（获取全部）", notes = "List-定时变化排行榜（获取全部）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/timedChangeLeaderboard")
    @ResponseBody
    public ReturnData timedChangeLeaderboard(@RequestParam("code") String code) {
        // 模拟排行榜假数据
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");
        arrayList.add("wangwu");
        boolean b = redisUtil.lSet(code, arrayList, 3000);
        // 获取该 list 所有数据
        List<Object> redisList = redisUtil.lGet(code, 0, -1);

        // 获取该list 的分页数据
        // 0,2
        // 2,4


        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(redisList);
        return ret;
    }

    @ApiOperation(value = "List-定时变化排行榜（分页）", notes = "List-定时变化排行榜（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true),
            @ApiImplicitParam(name = "page", value = "page", required = true),
            @ApiImplicitParam(name = "size", value = "size", required = true)})
    @PostMapping("/timedChangeLeaderboard")
    @ResponseBody
    public ReturnData timedChangeLeaderboardPagination(@RequestParam("code") String code, @RequestParam("page") String page, @RequestParam("size") String size) {
        // 模拟排行榜假数据
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");
        arrayList.add("wangwu");
        boolean b = redisUtil.lSet(code, arrayList, 3000);
        // 获取分页数据
        long length = redisUtil.lGetListSize(code);

//        int startIndex = ;


//        redisUtil.lGet(code,);

        // 获取该list 的分页数据
        // 0,2
        // 2,4


        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
//        ret.setMessage(redisList);
        return ret;
    }


}
