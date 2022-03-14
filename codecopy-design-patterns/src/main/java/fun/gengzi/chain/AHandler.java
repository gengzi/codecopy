package fun.gengzi.chain;

public class AHandler extends AbstractHandler {
    @Override
    public void execHandler(RequestInfo requestInfo) {

        if(requestInfo.getType().equals("A")){
            System.out.println("AAAAA");
            return;
        }
        this.getHandler().execHandler(requestInfo);
    }
}
