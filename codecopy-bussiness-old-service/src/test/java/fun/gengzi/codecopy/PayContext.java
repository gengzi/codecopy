package fun.gengzi.codecopy;

public class PayContext {
    // 引用策略类
    private PayMethodService payMethodService;
 
    public PayContext(PayMethodService payMethodService) {
        this.payMethodService = payMethodService;
    }
 
    public String toPay(PayBaseEntity payBaseEntity) {
        return payMethodService.pay(payBaseEntity);
    }
}