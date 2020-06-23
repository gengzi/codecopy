package fun.gengzi.codecopy.business.subdata.dao;

import fun.gengzi.codecopy.business.product.entity.Product;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
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


}
