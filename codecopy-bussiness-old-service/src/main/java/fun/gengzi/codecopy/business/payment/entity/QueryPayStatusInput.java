package fun.gengzi.codecopy.business.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>查询支付状态输入参数类型</h1>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPayStatusInput implements Serializable {
    private static final long serialVersionUID = -7272809031556594105L;
    /**
     * 用户编码
     */
    private String userID;

    /**
     * 支付单号
     */
    private String payFlowID;
}