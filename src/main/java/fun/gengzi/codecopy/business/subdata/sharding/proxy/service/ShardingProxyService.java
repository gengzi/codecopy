package fun.gengzi.codecopy.business.subdata.sharding.proxy.service;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;

/**
 * <h1>简单模拟操作</h1>
 *
 * @author gengzi
 * @date 2020年6月30日15:03:00
 */

public interface ShardingProxyService {

    /**
     * 插入一条数据
     *
     * @param bussinessTable 业务实体
     * @return {@link BussinessTable}
     */
    BussinessTable insertBussinessData(BussinessTable bussinessTable);
}

