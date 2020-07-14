package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * <h1>hint dao</h1>
 *
 * @author gengzi
 * @date 2020年7月3日09:34:19
 */
@Repository
@Transactional
public interface BussinessDateTableAndUseridDaoExtendsJPA extends JpaRepository<BussinessTable, Long>, JpaSpecificationExecutor<BussinessTable> {



//    @Query(value = "INSERT INTO `dsh0`.`t_bussiness1` (`id`, `name`, `code`, `addresscode`, `diccode`, `createdate`, `updatedate`, `is_del`, `guid`, `data_version`) VALUES ('488352642046078976', 'xx', 'xx', 'xx', 'xx', '2020-06-23 00:00:00', '2020-06-23 00:00:00', '0', 'xx', '1');\n")
//    BussinessTable saveByInsert();


}
