package fun.gengzi.codecopy.utils;

//错误状态
public enum ErrorStatusEnum {
    STATUS_603(603);
    private Integer status;
    ErrorStatusEnum(Integer status){
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
