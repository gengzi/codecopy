package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;


public interface BussinessTableDao {

    /**
     * 插入数据
     *
     * @param bussinessTable 业务表数据
     * @return
     */
    Long insert(final BussinessTable bussinessTable);

    /**
     * 根据id 获取数据
     *
     * @param id 主键
     * @return
     */
    Object getBussinessTableById(final Long id);


}
