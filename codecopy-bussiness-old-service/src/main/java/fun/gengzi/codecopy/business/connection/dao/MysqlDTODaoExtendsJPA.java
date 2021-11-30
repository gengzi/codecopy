package fun.gengzi.codecopy.business.connection.dao;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MysqlDTODaoExtendsJPA extends JpaRepository<MysqlDTO, Long>, JpaSpecificationExecutor<MysqlDTO> {


    /**
     * 根据ip 查询
     *
     * @param ip ip
     * @return
     */
    List<MysqlDTO> findByIp(String ip);

}
