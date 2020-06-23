package fun.gengzi.codecopy.business.subdata.service;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;

/**
 * <h1>分库分表</h1>
 *
 *
 * 业务：
 * 在当前业务下，单表的数据量递增在1千五百万，大约有10张表的递增数据量都是这样。历史数据有两千万
 * 考虑查询性能和统计的性能，希望通过分库分表来解决问题
 * 系统的并发量不高，只是填报数据字段较多，里面存放了很多 下拉框，树的 code ，关联表的主要是 用户id 用人单位id 审核人id
 *
 * 分库分表带来的问题
 *
 * 查询怎么做，统计怎么做
 *
 *
 *
 * @author gengzi
 * @date 2020年6月19日10:09:52
 *
 */
public interface SubDataService {

    /**
     * 根据id 获取业务表信息
     * @param id
     * @return
     */
    BussinessTable getBussinessTbaleInfo(Long id);



}
