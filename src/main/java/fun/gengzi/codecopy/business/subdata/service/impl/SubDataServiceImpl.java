package fun.gengzi.codecopy.business.subdata.service.impl;

import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.service.SubDataService;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableToDicVo;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据id 获取业务表信息,并将字典code 转化为 字典名称
     *
     * @param id 业务表id
     * @return
     */
    @Override
    public List<Map<String,Object>> getBussinessInfoAndDicInfo(Long id) {
        return daoExtendsJPA.qryBussinessTableToDic(id);
    }

    /**
     * 根据id 获取业务表信息,并将字典code 转化为 字典名称
     * <p>
     * 原生sql 查询
     *
     * @param id 业务表id
     * @return
     */
    @Override
    public List<Map<String, Object>> getBussinessInfoAndDicInfoBySql(Long id) {
        return daoExtendsJPA.qryBussinessTableToDicBySql(id);
    }
}
