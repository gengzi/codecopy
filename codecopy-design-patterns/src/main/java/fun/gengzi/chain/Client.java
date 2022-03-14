package fun.gengzi.chain;

public class Client {


    public static void main(String[] args) {
        RequestInfo requestInfo = new RequestInfo("C",new String[]{"aaa","bbb"});
        AHandler aHandler = new AHandler();
        BHandler bHandler = new BHandler();
        aHandler.setHandler(bHandler);
        aHandler.execHandler(requestInfo);

    }
}
