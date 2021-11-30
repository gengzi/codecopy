package fun.gengzi.codecopy.business.tomcat;

public interface Servlert {

    /**
     * 初始化
     */
    void init();

    /**
     * 服务处理方法
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    void service(Request request, Response response);

    /**
     * 销毁方法
     */
    void destory();


}
