package fun.gengzi.codecopy.business.tomcat;


public class DynamicTestServlet extends HttpServlet {
    /**
     * 处理get请求
     *
     * @param request
     * @param respon
     */
    @Override
    public void doGet(Request request, Response respon) {
        String data = ResponseUtils.http200("get 请求响应");
        respon.outPut(data);

    }

    /**
     * 处理post请求
     *
     * @param request
     * @param respon
     */
    @Override
    public void doPost(Request request, Response respon) {
        ResponseUtils.http200("post 请求响应");
    }

    /**
     * 初始化
     */
    @Override
    public void init() {

    }

    /**
     * 销毁方法
     */
    @Override
    public void destory() {

    }
}
