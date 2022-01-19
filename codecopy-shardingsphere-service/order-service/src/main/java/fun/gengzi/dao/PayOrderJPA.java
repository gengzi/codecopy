package fun.gengzi.dao;

import fun.gengzi.entity.PayOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * <h1>订单表dao 操作</h1>
 *
 * @author gengzi
 * @date 2022年1月17日14:53:04
 */
@Repository
public interface PayOrderJPA extends JpaRepository<PayOrderEntity, Long>, JpaSpecificationExecutor<PayOrderEntity> {

}
