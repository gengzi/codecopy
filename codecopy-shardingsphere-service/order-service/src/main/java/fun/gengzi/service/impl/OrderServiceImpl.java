package fun.gengzi.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.inject.internal.cglib.core.$ProcessArrayCallback;
import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.dao.PayOrderJPA;
import fun.gengzi.entity.PayOrderEntity;
import fun.gengzi.service.OrderService;
import fun.gengzi.vo.GoodsVo;
import fun.gengzi.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <h1> </h1>
 *
 * @author Administrator
 * @date 2022/1/17 15:01
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsJPA goodsJPA;

    @Autowired
    private PayOrderJPA payOrderJPA;


    @Transactional
    @Override
    public void generateOrder(OrderVo orderVo) {
        // 预扣库存，生成订单
        goodsJPA.inventoryReduction(orderVo.getId(), orderVo.getSales());
        PayOrderEntity payOrderEntity = new PayOrderEntity();
        payOrderEntity.setUserId(orderVo.getUserid());
        payOrderEntity.setProductId(Long.toString(orderVo.getId()));
        //TODO  处理小数，特别注意，不要使用 bigdecimal(double) 处理，会出现精度丢失问题。如果需要处理小数，请使用 String 的入参
        BigDecimal price = new BigDecimal(orderVo.getPrice());
        BigDecimal sales = new BigDecimal(orderVo.getSales());
        BigDecimal totalMoney = price.multiply(sales);
        payOrderEntity.setAmount(totalMoney);
        payOrderEntity.setProductName(orderVo.getGoodsName());
        payOrderEntity.setOrderNo(orderId(orderVo.getUserid()));
        payOrderJPA.saveAndFlush(payOrderEntity);
    }


    private String orderId(String userid) {
        long currentTimeMillis = System.currentTimeMillis();
        String id = Long.toString(currentTimeMillis) +
                userid.substring(userid.length() - 5, userid.length()) +
                RandomUtil.randomNumbers(3);
        log.info("生成的orderid：{}", id);
        return id;
    }
}
