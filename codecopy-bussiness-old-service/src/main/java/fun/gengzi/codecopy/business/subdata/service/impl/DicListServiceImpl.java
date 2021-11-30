package fun.gengzi.codecopy.business.subdata.service.impl;

import fun.gengzi.codecopy.business.subdata.entity.DicList;
import fun.gengzi.codecopy.business.subdata.service.DicListService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class DicListServiceImpl implements DicListService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 插入一条数据
     *
     * @param dicList
     * @return
     */
    @Override
    public DicList insertDicList(final DicList dicList) {
        entityManager.persist(dicList);
        return dicList;

    }
}
