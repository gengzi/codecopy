package fun.gengzi.codecopy.business.subdata.service.impl;

import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.service.SubDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubDataServiceImpl implements SubDataService {


    @Autowired
    private BussinessTableDaoExtendsJPA daoExtendsJPA;


    /**
     * 根据id 获取业务表信息
     *
     * @param id
     * @return
     */
    @Override
    public BussinessTable getBussinessTbaleInfo(Long id) {
        return daoExtendsJPA.findById(id).orElse(null);
    }
}
