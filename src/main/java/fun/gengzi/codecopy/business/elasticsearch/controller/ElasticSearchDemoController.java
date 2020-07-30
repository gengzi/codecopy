package fun.gengzi.codecopy.business.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import fun.gengzi.codecopy.business.elasticsearch.dao.ConferenceRepository;
import fun.gengzi.codecopy.business.elasticsearch.entity.Conference;
import fun.gengzi.codecopy.business.elasticsearch.entity.EsSysEntiry;
import fun.gengzi.codecopy.business.elasticsearch.entity.UserEntity;
import fun.gengzi.codecopy.business.elasticsearch.service.ElasticSearchSpringDataShowService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


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

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private ElasticSearchSpringDataShowService elasticSearchSpringDataShowService;


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
     * <p>
     * <p>
     * <p>
     * {
     * "documentObj": {
     * "userName": "name"
     * <p>
     * },
     * "id": "1",
     * "index": "people",
     * "type": "info"
     * }
     * <p>
     * 响应信息：
     * {
     * "status": 200,
     * "info": {},
     * "message": "{\"_index\":\"people\",\"_type\":\"info\",\"_id\":\"1\",\"_version\":1,\"result\":\"created\",\"_shards\":{\"total\":2,\"successful\":1,\"failed\":0},\"_seq_no\":0,\"_primary_term\":1}"
     * }
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
    public ReturnData svaeOneDocument(@RequestParam(value = "ipAndport", required = false) String ipAndport, @RequestBody EsSysEntiry<UserEntity> esSysEntiry) {
        // 判断是否为 json 字符串，校验格式 是
        // 转换为实体，写入指定的位置，并将其 java 文件，编译为 class ，动态加载到 spring 容器中
        // 再使用json 串，为动态加载的类赋值
        final String documentJson = esSysEntiry.getDocumentJson();
        final UserEntity userEntity = esSysEntiry.getDocumentObj();
        if (StringUtils.isBlank(ipAndport)) {
            ipAndport = "http://localhost:9200";
        }
        final String index = esSysEntiry.getIndex();
        final String type = esSysEntiry.getType();
        final String id = esSysEntiry.getId();
        final String url = ipAndport + "/" + index + "/" + type + "/" + id;

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        if (StringUtils.isNotBlank(documentJson)) {
            requestEntity = new HttpEntity<Object>(documentJson, httpHeaders);
        } else {
            final String userinfo = JSONObject.toJSONString(userEntity);
            requestEntity = new HttpEntity<Object>(userinfo, httpHeaders);
        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        final String body = responseEntity.getBody();
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(body);
        return ret;
    }

    @ApiOperation(value = "测试使用spring-data-elasticsearch保存数据到es 中", notes = "测试使用spring-data-elasticsearch保存数据到es 中")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t			\"nextStepType\":\"下一步操作\",\n" +
            "\t			\"nextStepData\":\"下一步操作结构体\"\n" +
            "\t		}\n" +
            "\t    \"message\": \"操作成功\",\n" +
            "\t    \"bzcode\": \"\"\n" +
            "\t}\n")})
    @RequestMapping(value = "/svaeOneInfo", method = RequestMethod.POST)
    public ReturnData svaeOneInfo() {
        // 存储一个文档到 es
        // 使用 builder 方法来构造一个 文档对象
        Conference conference = conferenceRepository.save(Conference.builder().date("2014-11-06").name("Spring eXchange 2014 - London")
                .keywords(Arrays.asList("java", "spring")).location(new GeoPoint(51.500152D, -0.126236D)).build());
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(conference);
        return ret;
    }


    /**
     * 测试数据
     * <p>
     * {
     * "age": 10,
     * "birthday": "2020-07-30 10:11:33",
     * "imgs": [
     * {
     * "imgUrl": "http://localhost:8089/swagger-ui.html#/ElasticSearch%20RestFul%20api%20%E6%8E%A5%E5%8F%A3%E6%BC%94%E7%A4%BA/saveUserInfoUsingPOST",
     * "imgid": 2
     * }
     * ],
     * "pinYinName": "lisi",
     * "uid": 2,
     * "userName": "李四"
     * }
     *
     * @param userEntity
     * @return
     */
    @ApiOperation(value = "UserEntity保存数据到es中", notes = "UserEntity保存数据到es中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserEntity", value = "UserEntity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
    public ReturnData saveUserInfo(@RequestBody UserEntity userEntity) {
        UserEntity userInfo = elasticSearchSpringDataShowService.saveUserInfo(userEntity);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(userInfo);
        return ret;
    }

    @ApiOperation(value = "根据用户名称查询匹配用户信息", notes = "根据用户名称查询匹配用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @RequestMapping(value = "/searchUserInfoByUserName", method = RequestMethod.POST)
    public ReturnData searchUserInfoByUserName(@RequestParam("userName") String userName) {
        List<UserEntity> userEntities = elasticSearchSpringDataShowService.searchUserInfo(userName);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(userEntities);
        return ret;
    }


}
