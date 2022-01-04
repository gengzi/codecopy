package fun.gengzi.dao;

import fun.gengzi.aspect.JpaisSharding;
import fun.gengzi.entity.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
public interface GoodsJPA extends JpaRepository<GoodsEntity, Long>, JpaSpecificationExecutor<GoodsEntity> {


    /**
     * 根据商品id 减少存储数
     *
     * @param id  商品id
     * @param num 库存数
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE goods t SET t.sales = t.sales - ?2 WHERE t.id = ?1 AND t.sales > ?2", nativeQuery = true)
    void inventoryReduction(Long id, Integer num);

    List<GoodsEntity> findByIdBetween(Long id, Long id1);


    /**
     * 只查询旧库，不进行新库执行
     *
     * 根据时间范围查询，在此时间修改的数据
     *
     * @param updateTimeStart 开始时间，yyyy-MM-dd 00:00:00
     * @param updateTimeEnd  结束时间, yyyy-MM-dd 23:59:59
     * @return
     */
    @JpaisSharding(value = JpaisSharding.Type.N)
    @Query(value = "SELECT t.id FROM goods t WHERE t.update_time BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Long> queryIdByUpdate(String updateTimeStart, String updateTimeEnd);

}
