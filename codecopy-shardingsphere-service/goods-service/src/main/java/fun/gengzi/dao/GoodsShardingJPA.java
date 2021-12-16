package fun.gengzi.dao;

import fun.gengzi.aspect.JpaHintSharding;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.enums.ShardingDataSourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository("goodsShardingJPA")
@Transactional
public interface GoodsShardingJPA extends JpaRepository<GoodsEntity, Long>, JpaSpecificationExecutor<GoodsEntity> {


    @Query(value = "UPDATE goods t SET t.sales = t.sales - ?2 WHERE t.id = ?1 AND t.sales > ?2;", nativeQuery = true)
    GoodsEntity inventoryReduction(Long id,Integer num);

    List<GoodsEntity> findByIdBetween(Long id, Long id1);

}
