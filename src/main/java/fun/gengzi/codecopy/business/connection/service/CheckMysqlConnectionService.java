package fun.gengzi.codecopy.business.connection.service;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;

public interface CheckMysqlConnectionService {

    /**
     * 检测mysql链接是否有效
     *
     * @param startIndex 开始索引
     * @param endIndex 结束索引
     *
     * @return
     */
    MysqlDTO checkMysqlConnectionIsEnable(Long startIndex,Long endIndex);

}
