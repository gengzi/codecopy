package fun.gengzi.codecopy;

public enum PayEnum {
    //
    ZFB("zfb", "支付宝"),
    WXZF("wxzf", "微信支付"),
    YHKZF("yhkzf", "银行卡");
    private String code;
    private String codeName;
 
    private PayEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getCodeName() {
        return codeName;
    }
 
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    // 根据code 获取对应的枚举值
    public static PayEnum getPayEnumByCode(String code){
        for (int i = 0; i < PayEnum.values().length; i++) {
            if(!"".equals(code) && PayEnum.values()[i].getCode().equals(code)){
                return PayEnum.values()[i];
            }
        }
        return null;
    }
 
}