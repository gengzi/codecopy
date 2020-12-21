package fun.gengzi.codecopy.business.redis.controller;

import fun.gengzi.codecopy.business.redis.config.RedisManager;
import fun.gengzi.codecopy.business.redis.config.RedisRegister;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.redisson.misc.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


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
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisScript<Boolean> script;

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
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");
        arrayList.add("wangwu");
        boolean b = redisUtil.lSetAll(code, arrayList, 18000);
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
    @PostMapping("/timedChangeLeaderboardPagination")
    @ResponseBody
    public ReturnData timedChangeLeaderboardPagination(@RequestParam("code") String code, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        // 模拟排行榜假数据
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add("zhangsan"); // 0
        arrayList.add("lisi");  // 1
        arrayList.add("wangwu");  // 2
        boolean b = redisUtil.lSetAll(code, arrayList, 18000);
        // 获取分页数据
        long length = redisUtil.lGetListSize(code);
        int startIndex = (page * size) + 1;
        int endIndex = startIndex + size;
        List<Object> redisList = redisUtil.lGet(code, startIndex, endIndex);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(redisList);
        return ret;
    }


    /**
     * redis map
     * <p>
     * 经常变化的数据 可以使用 map
     * <p>
     * 用户id  商品id 数量
     * key    110  1
     * 112  2
     * 113  3
     * <p>
     * <p>
     * 不是经常变化的数据
     * <p>
     * 用户id  商品id  标签信息
     * key   110      json
     * 111      json
     * <p>
     * <p>
     * 如果存储的字段不多，而且长度都很短。 使用hash 更为有效
     * <p>
     * 在大多数的场景下，大多数都是获取数据，并不是修改数据。对于redis 中数据的修改，通常默认先执行 数据库，再更新redis 的数据
     * 在不清楚数据规模的情况下，可以优先考虑 String json 这种方式。能构造更加复杂的数据对象
     * <p>
     * <p>
     * 两种存储方法： hashtable  或者 ziplist
     * <p>
     * hashtable 与 hashMap 的底层类似
     * <p>
     * 扩容缩容依赖于  负载因子，来控制 数据的扩容和缩小
     * <p>
     * <p>
     * https://stor.51cto.com/art/201907/600416.htm
     * 可以看下，对于 hash  和 String 的选择
     */
    @ApiOperation(value = "Map-存储对象", notes = "存储对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/storageObject")
    @ResponseBody
    public ReturnData storageObject(@RequestParam("code") String code) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("111", "22");
        stringObjectHashMap.put("112", "23");
        stringObjectHashMap.put("113", "221");
        stringObjectHashMap.put("114", "222");
        boolean hmset = redisUtil.hmset(code, stringObjectHashMap);
        Map<Object, Object> hmget = redisUtil.hmget(code);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(hmget);
        return ret;
    }


    /**
     * set 无序列表
     * <p>
     * 底层 hashtable  intset
     * 如果是 hashtable ， value 为 null
     * <p>
     * <p>
     * 如果是存储数字， -127 - 128 是一个字节存储
     * 如果是大一点的数字，就需要 2 两个字节存储
     * <p>
     * 共同关注
     * <p>
     * 粉丝列表，转 好友列表
     * <p>
     * userid  fenid
     * 我的关注
     * userid  wid
     * 几百个，或者几万个。
     * 1    {5,4,2}
     * 2    {4,1}
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "set-热门数据", notes = "热门数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/hotdata")
    @ResponseBody
    public ReturnData hotdata(@RequestParam("code") String code) {
        code = "set:" + code;
        String arr[] = {"热门事件1", "热门事件2", "热门事件3", "热门事件4", "热门事件5", "热门事件1"};
        long l = redisUtil.sSet(code, arr);
        // 测试存储同样的value，会不会去重
        Set<Object> objects1 = redisUtil.sGet(code);
        logger.info("当前set集合的内容：{}", objects1);
        List<Object> objects = redisUtil.sRandomGet(code, 4);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(objects);
        return ret;
    }


    /**
     * shorted set  排序set
     * <p>
     * <p>
     * skkiplist 实现
     * <p>
     * 根据分数排序的链表
     * 由于底层数据结构是 链表，不能使用二分查找的方式，来进行快速搜索
     * 所以提出 跳跃表的思想
     * 时间复杂度 最坏情况下 O(N)
     * 正常情况 O(logN)
     * <p>
     * cnblogs.com/chenziyu/p/9225233.html
     * <p>
     * <p>
     * <p>
     * 动态排行榜实现
     * <p>
     * redis 事物实现
     * <p>
     * redis 的脚本模式
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "sorted set 动态排行榜", notes = "dynamicLeaderboard")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/dynamicLeaderboard")
    @ResponseBody
    public ReturnData dynamicLeaderboard(@RequestParam("code") String code) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        DefaultTypedTuple typedTuple = new DefaultTypedTuple("zhangsan", 88D);
        DefaultTypedTuple typedTuple1 = new DefaultTypedTuple("zhangsan", 77D);
        DefaultTypedTuple typedTuple2 = new DefaultTypedTuple("lisi", 68D);
        DefaultTypedTuple typedTuple3 = new DefaultTypedTuple("wangwu", 120D);
        tuples.add(typedTuple);
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        redisUtil.zsSetAndTime(code, tuples);
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zsGetReverseWithScores(code, 0, -1);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(set);
        return ret;
    }


    /**
     * 多路复用
     * <p>
     * 多个网络连接，复用同一个线程，进行同时的网络I/O的操作
     * <p>
     * 多路复用技术依靠于操作系统的支持，通过监听回调的形式，当客户发来网络数据，就直接返回给redis。
     * <p>
     * 监听这个线程上的，i/o操作。有那个客户发来数据，就接收处理。
     * <p>
     * <p>
     * https://blog.csdn.net/diweikang/article/details/90346020
     * <p>
     * <p>
     * 选择 redis 数据库命令
     * select 数据库的数字
     */

    @ApiOperation(value = "redis 动态选择数据库", notes = "redis 动态选择数据库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true),
            @ApiImplicitParam(name = "dbindex", value = "dbindex", required = true)})
    @PostMapping("/dynamicDatabaseSelection")
    @ResponseBody
    public ReturnData dynamicDatabaseSelection(@RequestParam("code") String code, @RequestParam("dbindex") Integer dbindex) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        DefaultTypedTuple typedTuple = new DefaultTypedTuple("zhangsan", 88D);
        DefaultTypedTuple typedTuple1 = new DefaultTypedTuple("zhangsan", 77D);
        DefaultTypedTuple typedTuple2 = new DefaultTypedTuple("lisi", 68D);
        DefaultTypedTuple typedTuple3 = new DefaultTypedTuple("wangwu", 120D);
        tuples.add(typedTuple);
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        redisUtil.switchDatabase(dbindex);
//        try {
//            logger.info("当前业务执行业务开始");
//            Thread.sleep(30000);
//            logger.info("耗时结束");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        redisUtil.zsSetAndTime(code, tuples);
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zsGetReverseWithScores(code, 0, -1);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(set);
        return ret;
    }

    @ApiOperation(value = "redis 动态选择数据库 新方式", notes = "redis 动态选择数据库 新方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true),
            @ApiImplicitParam(name = "dbindex", value = "dbindex", required = true)})
    @PostMapping("/dynamicDatabaseSelectionNew")
    @ResponseBody
    public ReturnData dynamicDatabaseSelectionNew(@RequestParam("code") String code, @RequestParam("dbindex") Integer dbindex) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        DefaultTypedTuple typedTuple = new DefaultTypedTuple("zhangsan", 88D);
        DefaultTypedTuple typedTuple1 = new DefaultTypedTuple("zhangsan", 77D);
        DefaultTypedTuple typedTuple2 = new DefaultTypedTuple("lisi", 68D);
        DefaultTypedTuple typedTuple3 = new DefaultTypedTuple("wangwu", 120D);
        tuples.add(typedTuple);
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        redisUtil.zsSetAndTime(code, tuples, dbindex);
        Set<ZSetOperations.TypedTuple<Object>> set = redisUtil.zsGetReverseWithScores(code, 0, -1, dbindex);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(set);
        return ret;
    }





    @ApiOperation(value = "redis lua scriptTest", notes = "redis lua 脚本测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "code", required = true)})
    @PostMapping("/scriptTest")
    @ResponseBody
    public ReturnData scriptTest(@RequestParam("code") String code) {
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
        DefaultTypedTuple typedTuple = new DefaultTypedTuple("zhangsan", 88D);
        DefaultTypedTuple typedTuple1 = new DefaultTypedTuple("zhangsan", 77D);
        DefaultTypedTuple typedTuple2 = new DefaultTypedTuple("lisi", 68D);
        DefaultTypedTuple typedTuple3 = new DefaultTypedTuple("wangwu", 120D);
        tuples.add(typedTuple);
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);


        redisTemplate.execute(script, Collections.singletonList(code),tuples.toArray());
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
        return ret;
    }



//    public boolean checkAndSet(String expectedValue, String newValue) {
//        return (boolean) redisTemplate.execute(script, Collections.singletonList("key"), Arrays.asList(expectedValue, newValue));
//    }


}
