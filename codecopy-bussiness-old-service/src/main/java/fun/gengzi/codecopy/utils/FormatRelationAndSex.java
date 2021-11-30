package fun.gengzi.codecopy.utils;

import java.math.BigDecimal;

/**
 * 处理用户关系和性别工具类,圈子身份，公告类型
 * @author zengming.liu@foriseland.com
 * @since 2018-03-30
 */
public class FormatRelationAndSex {
    /**
     * 好友关系处理
     * @param relation
     * @return
     */
    public static String formatRelation(Integer relation) {
        if (relation == null) {
            return "RED";
        }
        if (relation.intValue() == 1) {
            return "GREEN";
        } else if (relation.intValue() == 2) {
            return "YELLOW";
        } else {
            return "RED";
        }
    }

    /**
     * 性别： 0是男，1是女
     * @param sex
     * @return
     */
    public static String formatSex(String sex) {
        if (sex == null) {
            return "";
        }
        if ("1".equals(sex)) {
            return "male";
        } else if ("2".equals(sex)){
            return "female";
        }
        return "";
    }

    /**
     * 社区用户角色
     * 0:群主
     * 1:联席群主
     * 2:行为会员
     * @param identity
     * @return
     */
    public static String formatCrowdIdentity(Integer identity) {
        if (identity == null) {
            return "none";
        }
        if (identity.intValue() == 0) {
            return "crowdMaster";
        } else if (identity.intValue() == 2) {
            return "behaviorMember";
        } else {
            return "none";
        }
    }

    /**
     * 社群公告发布类型
     * 1-聊天内容模式，2－聊天背景模式，3－气泡模式
     * @Time 2018-05-29
     * @param intType
     * @return
     */
    public static String formatCrowdAnnounceTypeApp(String intType) {
        if (intType == null) {
            return "";
        }
        if ("1".equals(intType)) {
            return "talk";
        } else if ("2".equals(intType)) {
            return "bankGround";
        } else if ("3".equals(intType)) {
            return "bubble";
        } else {
            return "";
        }
    }
    public static String formatCrowdAnnounceTypeEngine(String strType) {
        if (strType == null) {
            return "";
        }
        if ("talk".equals(strType)) {
            return "1";
        } else if ("bankGround".equals(strType)) {
            return "2";
        } else if ("bubble".equals(strType)) {
            return "3";
        } else {
            return "";
        }
    }

    // 5圈主
    // 4群主
    // 3联席群主
    // 2行为会员
    // 1观摩会员

    /**
     * 格式化圈子内人的身份（注意群的别用）
     * @param identity
     * @return
     */
    public static String formatCircleIdentity(Integer identity) {
        if (identity == 5) {
            return "circleMaster";//圈主
        } else if (identity == 4) {
            return "crowdMaster";//群主
        } else if (identity == 3) {
            return "jointMaster";//联席圈主
        } else if (identity == 2) {
            return "behaviorMember";//行为会员
        } else if (identity == 1) {
            return "watchMember";//观摩会员
        } else {
            return "none";//无身份
        }
    }


    public static void main(String[] args){
        Integer integer = getInt(0.0);
        System.out.println(integer);
    }

    /**
     * dubbo转int
     * @param number
     * @return
     */
    public static int getInt(double number){
        BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());
    }

    /**
     * 反向格式化圈子内人的身份（注意群的别用）
     * @param identity
     * @return
     */
    public static Integer revertFormatCircleIdentity(String identity) {
        if ("circleMaster".equals(identity)) {
            return 5;//圈主
        } else if ("crowdMaster".equals(identity)) {
            return 4;//群主
        } else if ("jointMaster".equals(identity)) {
            return 3;//联席圈主
        } else if ("behaviorMember".equals(identity)) {
            return 2;//行为会员
        } else if ("watchMember".equals(identity)) {
            return 1;//观摩会员
        } else {
            return 1;//观摩会员
        }
    }


    /**
     * g格式化圈子的公告显示方式
     * @param typeEngine
     * @return
     */
    public static String formatCircleAnnouncementTypeEngine(String typeEngine) {
        if (null == typeEngine) {
            return "talk";//聊天方式
        } else if ("1".equals(typeEngine)) {
            return "bubble";//气泡模式
        }else if ("2".equals(typeEngine)) {
            return "bankGround";//背景
        }else {
            return "talk";
        }
    }

    public static String formatCircleAnnouncementTypeApp(String typeApp) {
        if (null == typeApp) {
            return "3";//聊天方式
        } else if ("bubble".equals(typeApp)) {
            return "1";//气泡模式
        }else if ("bankGround".equals(typeApp)) {
            return "2";//背景
        }else {
            return "3";
        }
    }

    /**
     * g格式化圈子的公告状态
     * @param status
     * @return
     */
    public static String formatCircleAnnouncementStatusEngine(String status) {
        if (null == status) {
            return "invalid";//无效
        } else if ("1".equals(status)) {
            return "effective ";//有效
        }else if ("2".equals(status)) {
            return "invalid ";
        }else {
            return "invalid";
        }
    }
}
