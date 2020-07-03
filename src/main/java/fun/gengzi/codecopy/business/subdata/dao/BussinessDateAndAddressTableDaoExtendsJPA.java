package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateAndAddressTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



/**
 * <h1>复合分片dao</h1>
 *
 * @author gengzi
 * @date 2020年7月3日09:34:19
 */
@Repository
@Transactional
public interface BussinessDateAndAddressTableDaoExtendsJPA extends JpaRepository<BussinessDateAndAddressTable, Long>, JpaSpecificationExecutor<BussinessDateAndAddressTable> {


}
