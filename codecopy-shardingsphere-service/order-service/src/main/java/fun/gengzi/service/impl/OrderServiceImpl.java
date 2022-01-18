package fun.gengzi.service.impl;

import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.dao.PayOrderJPA;
import fun.gengzi.entity.PayOrderEntity;
import fun.gengzi.service.OrderService;
import fun.gengzi.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1> </h1>
 *
 * @author Administrator
 * @date 2022/1/17 15:01
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsJPA goodsJPA;

    @Autowired
    private PayOrderJPA payOrderJPA;
    @Transactional
    @Override
    public void generateOrder(GoodsVo goodsVo) {
        // 预扣库存，生成订单
        goodsJPA.inventoryReduction(goodsVo.getId(),goodsVo.getSales());

        PayOrderEntity payOrderEntity = new PayOrderEntity();

        payOrderJPA.save(payOrderEntity);


    }
}
