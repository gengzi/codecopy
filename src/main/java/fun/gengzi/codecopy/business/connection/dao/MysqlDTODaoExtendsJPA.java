package fun.gengzi.codecopy.business.connection.dao;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MysqlDTODaoExtendsJPA extends JpaRepository<MysqlDTO, Long>, JpaSpecificationExecutor<MysqlDTO> {

}
