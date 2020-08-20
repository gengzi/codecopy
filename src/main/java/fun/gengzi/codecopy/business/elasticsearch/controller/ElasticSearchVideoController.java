package fun.gengzi.codecopy.business.elasticsearch.controller;

import com.alibaba.otter.canal.common.utils.JsonUtils;
import fun.gengzi.codecopy.business.elasticsearch.entity.VideoEntity;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



@Api(value = "ES-video信息的增删改查", tags = {"ES-video信息的增删改查"})
@RestController
@RequestMapping("/es/video")
public class ElasticSearchVideoController {
    private Logger logger = LoggerFactory.getLogger(ElasticSearchVideoController.class);


    @ApiOperation(value = "存储video的数据信息", notes = "存储video的数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "VideoEntity", value = "VideoEntity")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t			\"nextStepType\":\"下一步操作\",\n" +
            "\t			\"nextStepData\":\"下一步操作结构体\"\n" +
            "\t		}\n" +
            "\t    \"message\": \"操作成功\",\n" +
            "\t    \"bzcode\": \"\"\n" +
            "\t}\n")})
    @RequestMapping(value = "/saveVideoEntity", method = RequestMethod.POST)
    public ReturnData testServerIsEnable(@RequestBody List<VideoEntity> videoEntity) {
//
//        VideoEntity.VideoEntityBuilder haha = new VideoEntity().builder().videoname("haha").videotime("22").videourl("http://333").imgurl("http://rr");
//        String string = JsonUtils.marshalToString(haha);
//        logger.info(string);


        // 数据库保存
        // es 保存
        // 响应数据
        // 双写 放弃
        // 异步 mq 代码耦合
        //
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }









}
