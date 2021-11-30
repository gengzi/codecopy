package fun.gengzi.codecopy.business.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>订单信息，发往支付宝测试用</h1>
 *
 * @author gengzi
 * @date 2020年8月4日14:13:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoEntity {
    // 出口交易编号
    private String out_trade_no;
    // 总金额
    private String total_amount;
    // 主题
    private String subject;
    // 买家编号
    private String buyer_id;
    // 商品详情
    private String goods_detail;
    // 卖家编号
    private String seller_id;
}
