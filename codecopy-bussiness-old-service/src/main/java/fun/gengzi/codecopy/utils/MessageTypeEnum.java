package fun.gengzi.codecopy.utils;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    COMMETN(1, "comment"),//评论
    REPLY(2, "reply"),//回复
    LIKE(3, "like"),//喜欢
    FORWARD(4, "forward");//转发

    private Integer messageType;
    private String messageTypeStr;

    private MessageTypeEnum(Integer messageType, String messageTypeStr) {
        this.messageType = messageType;
        this.messageTypeStr = messageTypeStr;
    }

    public static String getMessageTypeStr(Integer arg) {
        switch (arg) {
            case 1:
                return COMMETN.getMessageTypeStr();
            case 2:
                return REPLY.getMessageTypeStr();
            case 3:
                return LIKE.getMessageTypeStr();
            case 4:
                return FORWARD.getMessageTypeStr();
            default:
                return "";
        }
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageTypeStr() {
        return messageTypeStr;
    }

    public void setMessageTypeStr(String messageTypeStr) {
        this.messageTypeStr = messageTypeStr;
    }
}
