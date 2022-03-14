package fun.gengzi.chain;

public class RequestInfo {
    private String type;
    private String[] params;

    public RequestInfo(String type, String[] params) {
        this.type = type;
        this.params = params;
    }

    public String getType() {
        return type;
    }

    public String[] getParams() {
        return params;
    }
}
