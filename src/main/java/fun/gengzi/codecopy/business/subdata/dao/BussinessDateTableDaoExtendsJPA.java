package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * <h1>范围分片dao</h1>
 *
 * @author gengzi
 * @date 2020年7月3日09:34:19
 */
@Repository
@Transactional
public interface BussinessDateTableDaoExtendsJPA extends JpaRepository<BussinessDateTable, Long>, JpaSpecificationExecutor<BussinessDateTable> {


    /**
     * 查询开始时间到结束时间范围内的数据
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return {@link List<BussinessDateTable> }
     */
    @Query(value = "select t from BussinessDateTable t where createdate between ?1 and ?2 ")
    List<BussinessDateTable> getInfobyDate(Date startDate, Date endDate);

    /**
     * 查询当前时间之前的数据
     *
     * @param endDate   时间
     * @return {@link List<BussinessDateTable> }
     */
    @Query(value = "select t from BussinessDateTable t where createdate <= ?1 ")
    List<BussinessDateTable> getInfobyDateList(Date endDate);

}
