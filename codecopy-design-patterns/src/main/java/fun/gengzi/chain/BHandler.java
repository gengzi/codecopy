package fun.gengzi.chain;

public class BHandler extends AbstractHandler {
    @Override
    public void execHandler(RequestInfo requestInfo) {

        if(requestInfo.getType().equals("B")){
            System.out.println("BBBBBB");
            return;
        }
        this.getHandler().execHandler(requestInfo);
    }
}
