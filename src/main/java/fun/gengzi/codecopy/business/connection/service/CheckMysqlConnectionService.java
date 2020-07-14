package fun.gengzi.codecopy.business.connection.service;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;

public interface CheckMysqlConnectionService {

    /**
     * 检测mysql链接是否有效
     *
     * @param mysqlDTO
     * @return
     */
    MysqlDTO checkMysqlConnectionIsEnable(MysqlDTO mysqlDTO);

}
