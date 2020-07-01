package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface BussinessDateTableDaoExtendsJPA extends JpaRepository<BussinessDateTable, Long>, JpaSpecificationExecutor<BussinessDateTable> {


}
