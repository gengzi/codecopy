package fun.gengzi.codecopy;

public class PayFactory {
    private PayContext payContext;
    public PayFactory(String payEnum) {
        // 根据code 获取枚举对象
        PayEnum pay = PayEnum.getPayEnumByCode(payEnum);
        if (pay != null) {
            switch (pay) {   
                case WXZF:
                 payContext = new PayContext(new WxPay());
                    break;
                case ZFB:
                    payContext = new PayContext(new ZFBPay());
                    break;
                default:
                    System.out.println("不支持支付类型");
            }
        }
    }
 
    public String toPay(PayBaseEntity entity) {
        return payContext.toPay(entity);
    }
 
}