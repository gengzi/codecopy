package fun.gengzi.codecopy.business.product.controller;

import cn.hutool.core.util.RandomUtil;
import fun.gengzi.codecopy.business.product.entity.Product;
import fun.gengzi.codecopy.business.product.service.ProductCacheService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <h1>产品缓存服务</h1>
 */
@Api(value = "ProductCache测试接口", tags = {"ProductCache测试接口"})
@Controller
public class ProductCacheController {

    private Logger logger = LoggerFactory.getLogger(ProductCacheController.class);

    @Autowired
    private ProductCacheService productCacheService;


//    @ApiOperation(value = "缓存雪崩", notes = "缓存雪崩")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "type", value = "类型", required = true)})
//    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
//            "\t    \"status\": 200,\n" +
//            "\t    \"info\": {\n" +
//            "\t		}\n"	+
//            "\t    \"message\": \"信息\",\n" +
//            "\t}\n")})
//    @PostMapping("/findCacheByType")
//    @ResponseBody
//    public ReturnData findCacheByType(@RequestParam("type") String type) {
//        logger.info("findCacheByType start {} ");
//        List<Product> productCacheInfo = productCacheService.getProductCacheInfo(type);
//        ReturnData ret = ReturnData.newInstance();
//        ret.setSuccess();
//        ret.setMessage(productCacheInfo);
//        return ret;
//    }

    @ApiOperation(value = "缓存雪崩", notes = "缓存雪崩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheById")
    @ResponseBody
    public ReturnData findCacheById(@RequestParam("id") Integer id) {
        long start = System.currentTimeMillis();
        logger.info("findCacheById start {} ");
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfo(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        long end = System.currentTimeMillis();
        logger.info("findCacheById end {} ", (end - start));
        return ret;
    }

    @ApiOperation(value = "缓存穿透-缓存null数据", notes = "缓存穿透-缓存null数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheByErrorId")
    @ResponseBody
    public ReturnData findCacheByErrorId(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfo(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }


    @ApiOperation(value = "缓存穿透-使用布隆过滤器（内存）", notes = "缓存穿透-使用布隆过滤器（内存）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheUseBloomByErrorId")
    @ResponseBody
    public ReturnData findCacheUseBloomByErrorId(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfoBloom(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }


    @ApiOperation(value = "将key存入布隆过滤器", notes = "将key存入布隆过滤器")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/putBloomKey")
    @ResponseBody
    public ReturnData putBloomKey() {
        productCacheService.putBloomKey();
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    @ApiOperation(value = "缓存穿透-使用布隆过滤器（Redis）", notes = "缓存穿透-使用布隆过滤器（Redis）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheUseRedisBloomByErrorId")
    @ResponseBody
    public ReturnData findCacheUseRedisBloomByErrorId(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfoRedisBloom(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }

    @ApiOperation(value = "将key存入Redis布隆过滤器", notes = "将key存入Redis布隆过滤器")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/putRedisBloomKey")
    @ResponseBody
    public ReturnData putRedisBloomKey() {
        productCacheService.putRedisBloomKey();
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    @ApiOperation(value = "缓存雪崩-使用不同的redis过期时间来存储", notes = "缓存穿透-使用不同的redis过期时间来存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheUseRedisTtl")
    @ResponseBody
    public ReturnData findCacheUseRedisTtl(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = getOneProductCacheInfoTest(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }


    @ApiOperation(value = "缓存雪崩-使用多级缓存（一级，内存缓存，二级，redis缓存）一级缓存只缓存比较热点的数据", notes = "缓存雪崩-使用多级缓存（一级，内存缓存，二级，redis缓存）一级缓存只缓存比较热点的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheUseRedisAndLocalCache")
    @ResponseBody
    public ReturnData findCacheUseRedisAndLocalCache(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfoTest2(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }

    @ApiOperation(value = "缓存击穿-使用互斥锁", notes = "缓存击穿-使用互斥锁,当缓存失效，限定只有第一个线程查询数据库并更新缓存，其他线程阻塞，等待缓存更新，再从缓存中拿数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/findCacheByMutex")
    @ResponseBody
    public ReturnData findCacheByMutex(@RequestParam("id") Integer id) {
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfoByMutex(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }


    /**
     * 使用反射，根据随机参数调用 service 的方法，将数据存入不同的过期时间内
     * 不能在service 层调用，会将 @cacheable 注解忽略执行方法
     *
     * 目的 解决缓存雪崩问题，但是这种做法，应该是不好的，代码冗余而且繁杂
     *
     * @param id
     * @return
     */
    @SneakyThrows
    public Product getOneProductCacheInfoTest(Integer id) {
        int i = RandomUtil.randomInt(1, 4);
        String methodName = "cacheAvalanche"+i;
        // https://blog.csdn.net/morendap/article/details/80746996
        Method method = productCacheService.getClass().getMethod(methodName, Integer.class);
        return (Product) method.invoke(productCacheService,id);
    }


    @ApiOperation(value = "缓存更新- Cache-Aside Pattern", notes = "缓存穿透- Cache-Aside Pattern")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Product", value = "请求参数实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/updateProductInfo")
    @ResponseBody
    public ReturnData updateProductInfo(@RequestBody Product product) {
        productCacheService.updateProductInfo(product);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

}
