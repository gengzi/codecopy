package fun.gengzi.codecopy.business.subdata.service;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableToDicVo;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableVo;

import java.util.List;
import java.util.Map;

/**
 * <h1>分库分表</h1>
 * <p>
 * <p>
 * 业务：
 * 在当前业务下，单表的数据量递增在1千五百万，大约有10张表的递增数据量都是这样。历史数据有两千万
 * 考虑查询性能和统计的性能，希望通过分库分表来解决问题
 * 系统的并发量不高，只是填报数据字段较多，里面存放了很多 下拉框，树的 code ，关联表的主要是 用户id 用人单位id 审核人id
 * <p>
 * 分库分表带来的问题
 * <p>
 * 查询怎么做，统计怎么做
 *
 * @author gengzi
 * @date 2020年6月19日10:09:52
 */
public interface SubDataService {

    /**
     * 根据id 获取业务表信息
     *
     * @param id 业务表id
     * @return
     */
    BussinessTable getBussinessTbaleInfo(Long id);

    /**
     * 根据id 获取业务表信息,并将字典code 转化为 字典名称
     *
     * @param id 业务表id
     * @return
     */
    List<Map<String,Object>> getBussinessInfoAndDicInfo(Long id);


}
