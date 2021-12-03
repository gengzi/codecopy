package fun.gengzi.dao;

import fun.gengzi.entity.PayOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PayOrderJPA extends JpaRepository<PayOrderEntity, Long>, JpaSpecificationExecutor<PayOrderEntity> {

}
