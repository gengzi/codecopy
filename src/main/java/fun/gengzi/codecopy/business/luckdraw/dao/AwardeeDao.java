package fun.gengzi.codecopy.business.luckdraw.dao;

import fun.gengzi.codecopy.business.luckdraw.entity.TbAwardee;
import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * <h1>发奖记录dao层</h1>
 *
 * @author gengzi
 * @date 2020年9月11日13:36:51
 */
@Repository
public interface AwardeeDao extends JpaRepository<TbAwardee, Integer> {


}
