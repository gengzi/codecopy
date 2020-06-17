package fun.gengzi.codecopy.business.product.controller;

import fun.gengzi.codecopy.business.product.entity.Product;
import fun.gengzi.codecopy.business.product.service.ProductCacheService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        Product oneProductCacheInfo = productCacheService.getOneProductCacheInfoTest(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(oneProductCacheInfo);
        return ret;
    }

}
