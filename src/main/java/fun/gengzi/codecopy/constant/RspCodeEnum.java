package fun.gengzi.codecopy.constant;

/**
 * <h2>响应返回码枚举定义</h2>
 *
 * @author gengzi
 * @date 2020年5月23日18:43:45
 */
public enum RspCodeEnum {
    // 【参考】枚举类名带上 Enum 后缀，枚举成员名称需要全大写，单词间用下划线隔开
    //【推荐】如果变量值仅在一个固定范围内变化用 enum 类型来定义。
    SUCCESS(200, "success"),
    ERROR(0, "error"),
    FAILURE(1000,"failure");

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 描述
     */
    private String desc;

    RspCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }


    /**
     * 根据code返回RspCodeEnum
     * @param code 返回码
     * @return
     */
    public static RspCodeEnum getRspCodeEnumBycode(int code) {
        for (RspCodeEnum rspCodeEnum : RspCodeEnum.values()) {
            if (rspCodeEnum.getCode() == code) {
                return rspCodeEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举-RspCodeEnum");
    }
}
