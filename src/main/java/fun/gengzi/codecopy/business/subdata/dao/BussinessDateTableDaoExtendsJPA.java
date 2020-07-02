package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface BussinessDateTableDaoExtendsJPA extends JpaRepository<BussinessDateTable, Long>, JpaSpecificationExecutor<BussinessDateTable> {

    @Query(value = "select t from BussinessDateTable t where createdate between ?1 and ?2 ")
    List<BussinessDateTable> getInfobyDate(Date startDate , Date endDate);

}
