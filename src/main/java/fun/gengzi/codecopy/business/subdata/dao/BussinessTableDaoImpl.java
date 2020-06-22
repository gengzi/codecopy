package fun.gengzi.codecopy.business.subdata.dao;


import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 业务表dao 层实现
 */
@Repository
@Transactional
public class BussinessTableDaoImpl implements BussinessTableDao {


    /**
     * 首先@PersistenceContext是jpa专有的注解，而@Autowired是spring自带的注释
     * 上方图片的意思就是EntityManager不是线程安全的，当多个请求进来的时候，spring会创建多个线程，
     * 而@PersistenceContext就是用来为每个线程创建一个EntityManager的，而@Autowired就只创建了一个，为所有线程共用，有可能报错
     *
     * https://www.pianshen.com/article/30321050885/
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 插入数据
     *
     * @param bussinessTable 业务表数据
     * @return
     */
    @Override
    public String insert(final BussinessTable bussinessTable) {
        entityManager.persist(bussinessTable);
        return bussinessTable.getEnterpriseInfoId();
    }
}
