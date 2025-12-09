package fun.gengzi.design.pattern.template;

public class MyTemplate {


    public Object execute(Object param,BussinessExec bussinessExec) {
        Object result = null;
        try {
            // 1. 预处理
            preHandle();
            // 2. 业务处理
            result = bussinessExec.bussinessExec(param);
            // 3. 后置处理
            postHandle();
        } catch (Exception e) {
            // 4. 异常处理
            exceptionHandle(e);
        }
        return result;
    }

    private void exceptionHandle(Exception e) {
        System.out.println("处理异常了");
    }

    private void postHandle() {
        System.out.println("后置处理了");
    }

    private void preHandle() {
        System.out.println("前置处理了");
    }


    public static void main(String[] args) {
        MyTemplate myTemplate = new MyTemplate();
        myTemplate.execute("11",(param)->{
            System.out.println("业务处理了");
            return "业务处理结果";
        });
    }


}
