package fun.gengzi.codecopy.business.connection.service;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;

import java.util.List;

public interface CheckMysqlConnectionService {

    /**
     * 检测mysql链接是否有效
     *
     * @param startIndex 开始索引
     * @param endIndex   结束索引
     * @return
     */
    MysqlDTO checkMysqlConnectionIsEnable(Long startIndex, Long endIndex);

    /**
     * 根据ip查询 mysql链接信息
     *
     * @param ip ipv4 地址
     * @return {@link MysqlDTO}
     */
    List<MysqlDTO> getMysqlDTOInfo(String ip);

}
