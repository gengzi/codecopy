package fun.gengzi.dao;

import fun.gengzi.aspect.JpaHintSharding;
import fun.gengzi.aspect.JpaisSharding;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.enums.ShardingDataSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface GoodsShardingJPA extends JpaRepository<GoodsEntity, Long>, JpaSpecificationExecutor<GoodsEntity> {


    @Modifying
    @Transactional
    @Query(value = "UPDATE goods t SET t.sales = t.sales - ?2 WHERE t.id = ?1 AND t.sales > ?2", nativeQuery = true)
    void inventoryReduction(Long id, Integer num);

    List<GoodsEntity> findByIdBetween(Long id, Long id1);


    /**
     * 只查询旧库，不进行新库执行
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     */
    @JpaisSharding(value = JpaisSharding.Type.N)
    @Query(value = "SELECT t.id FROM goods t WHERE t.update_time BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Long> queryIdByUpdate(String updateTimeStart, String updateTimeEnd);


}
