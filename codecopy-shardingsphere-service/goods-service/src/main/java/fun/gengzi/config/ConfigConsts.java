package fun.gengzi.config;

/**
 * <h1>Config系统配置常量定义</h1>
 * @author gengzi
 * @date 2020年5月23日19:25:29
 * 全局的系统配置常量定义
 */
public class ConfigConsts {
    // 【推荐】不要使用一个常量类维护所有常量，要按常量功能进行归类，分开维护。
    // 快捷方式 psfs
    // 项目名称
    public static final String APP_NAME_TITLE = "goods-service";
    // 项目说明
    public static final String APP_EXPLANATION = "商品微服务";
    // 项目版本
    public static final String APP_VERSION = "1.0.0";
    // swagger-config-全局controller包名
    public static final String SWAGGER_CONTROLLER_PACKAGE = "fun.gengzi.controller";
    // 额外的包名
    public static final String OTHERR_PACKAGE = "fun.gengzi.job";

}
