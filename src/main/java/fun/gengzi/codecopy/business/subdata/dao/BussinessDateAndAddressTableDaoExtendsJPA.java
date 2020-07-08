package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.subdata.entity.BussinessDateAndAddressTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * <h1>复合分片dao</h1>
 *
 * @author gengzi
 * @date 2020年7月3日09:34:19
 */
@Repository
@Transactional
public interface BussinessDateAndAddressTableDaoExtendsJPA extends JpaRepository<BussinessDateAndAddressTable, Long>, JpaSpecificationExecutor<BussinessDateAndAddressTable> {


    /**
     * 查询根据 createDate 和  address ，验证复合分片算法是否可用
     * @param createDate 时间
     * @param address 地区
     * @return {@link  List<BussinessDateAndAddressTable>}
     */
    List<BussinessDateAndAddressTable> findByCreatedateAndAddresscode(Date createDate, String address);


    /**
     * 查询开始时间到结束时间范围内的数据
     *
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return {@link List<BussinessDateAndAddressTable> }
     */
    @Query(value = "select t from BussinessDateAndAddressTable t where createdate between ?1 and ?2 ")
    List<BussinessDateAndAddressTable> getInfobyDate(Date startDate, Date endDate);


}
