package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;


public interface BussinessTableDao {

    /**
     * 插入数据
     *
     * @param bussinessTable 业务表数据
     * @return
     */
    String insert(final BussinessTable bussinessTable);


}
