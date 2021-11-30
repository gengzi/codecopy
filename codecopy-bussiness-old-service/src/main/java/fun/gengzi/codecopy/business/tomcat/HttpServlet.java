package fun.gengzi.codecopy.business.tomcat;

import io.swagger.models.HttpMethod;

public abstract class HttpServlet implements Servlert {

    /**
     * 处理get请求
     * @param request
     * @param respon
     */
    public abstract void doGet(Request request, Response respon);

    /**
     * 处理post请求
     * @param request
     * @param respon
     */
    public abstract void doPost(Request request, Response respon);

    /**
     * 服务处理方法
     *
     * @param request  请求对象
     * @param response 响应对象
     */
    @Override
    public void service(Request request, Response response) {
        if (HttpMethod.GET.name().equalsIgnoreCase(request.getHttpType())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
