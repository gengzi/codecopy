package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.product.entity.Product;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableToDicVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BussinessTableDaoExtendsJPA extends JpaRepository<BussinessTable, Long>, JpaSpecificationExecutor<BussinessTable> {


    /**
     * 测试不查询 分片建，会不会 出现多条sql 语句
     *
     * @param name 名称
     * @return
     */
    List<BussinessTable> findByName(String name);


    /**
     * 根据业务表id，查询业务表数据（将字典code转为字典名称）
     *
     * @param id 业务表id
     * @return
     */
    @Query(value = "SELECT t1.*, t2.`name` AS codename FROM t_bussiness0 t1 LEFT JOIN ( SELECT CODE, NAME FROM dic_data WHERE DIC_LIST_ID = 31 ) t2 ON t1.`code` = t2.`code` WHERE t1.id = :id", nativeQuery = true)
    BussinessTableToDicVo qryBussinessTableToDic(Long id);


}
