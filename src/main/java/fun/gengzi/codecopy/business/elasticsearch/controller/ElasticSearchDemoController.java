package fun.gengzi.codecopy.business.elasticsearch.controller;

import fun.gengzi.codecopy.business.elasticsearch.entity.EsSysEntiry;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;


/**
 * <h1>ElasticSearch RestFul api 接口演示</h1>
 * <p>
 * 测试先，启动es 实例
 * 默认端口：9200
 * <p>
 * <p>
 * 未来： 简单的es 服务实例监控，查看服务实例信息，控制开启服务实例，关闭实例等等
 *
 * @author gengzi
 * @date 2020年7月22日22:47:32
 */
@Api(value = "ElasticSearch RestFul api 接口演示", tags = {"ElasticSearch RestFul api 接口演示"})
@RestController
@RequestMapping("/es")
public class ElasticSearchDemoController {
    private Logger logger = LoggerFactory.getLogger(ElasticSearchDemoController.class);

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 这里考虑使用 HttpClient okhttp 还有 RestTemplate 来发起http 请求 或者使用其他工具封装好的工具包
     *
     * @param ipAndport ip 和 端口
     * @return
     */
    @ApiOperation(value = "测试ElasticSearch实例是否启动", notes = "测试ElasticSearch实例是否启动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ipAndport", value = "localhost:9200")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t			\"nextStepType\":\"下一步操作\",\n" +
            "\t			\"nextStepData\":\"下一步操作结构体\"\n" +
            "\t		}\n" +
            "\t    \"message\": \"操作成功\",\n" +
            "\t    \"bzcode\": \"\"\n" +
            "\t}\n")})
    @RequestMapping(value = "/testServerIsEnable", method = RequestMethod.POST)
    public ReturnData testServerIsEnable(@RequestParam(value = "ipAndport", required = false) String ipAndport) {
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        logger.info("qryParams port : {} ", ipAndport);
        if (StringUtils.isBlank(ipAndport)) {
            ipAndport = "http://localhost:9200";
        }
        ResponseEntity<String> forEntity = restTemplate.getForEntity(ipAndport, String.class);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            ret.setMessage(forEntity.getBody());
        }
        return ret;
    }


    /**
     * 这里考虑使用 HttpClient okhttp 还有 RestTemplate 来发起http 请求 或者使用其他工具封装好的工具包
     *
     * @param ipAndport ip 和 端口
     * @return
     */
    @ApiOperation(value = "创建index创建type创建一个document", notes = "创建index创建type创建一个document")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ipAndport", value = "localhost:9200"),
            @ApiImplicitParam(name = "EsSysEntiry", value = "EsSysEntiry")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t			\"nextStepType\":\"下一步操作\",\n" +
            "\t			\"nextStepData\":\"下一步操作结构体\"\n" +
            "\t		}\n" +
            "\t    \"message\": \"操作成功\",\n" +
            "\t    \"bzcode\": \"\"\n" +
            "\t}\n")})
    @RequestMapping(value = "/svaeOneDocument", method = RequestMethod.POST)
    public ReturnData svaeOneDocument(@RequestParam(value = "ipAndport", required = false) String ipAndport, @RequestBody EsSysEntiry esSysEntiry) {

        String documentJson = esSysEntiry.getDocumentJson();
        // 判断是否为 json 字符串，校验格式 是
        // 转换为实体，写入指定的位置，并将其 java 文件，编译为 class ，动态加载到 spring 容器中
        // 再使用json 串，为动态加载的类赋值
        if (StringUtils.isBlank(ipAndport)) {
            ipAndport = "http://localhost:9200";
        }
        String index = esSysEntiry.getIndex();

        String type = esSysEntiry.getType();

        String id = esSysEntiry.getId();

        restTemplate.put(ipAndport + "/" + index + "/" + type + "/" + id, documentJson);

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


}
