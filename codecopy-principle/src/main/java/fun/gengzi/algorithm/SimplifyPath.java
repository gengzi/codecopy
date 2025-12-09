package fun.gengzi.algorithm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class SimplifyPath {
    // path = "/home/user/Documents/../Pictures"
    public String simplifyPath(String path) {
        if("".equals(path) ||  "/".equals(path)){
            return "";
        }
        LinkedList<Object> objects = new LinkedList<>();
        String[] split = path.split("/");
        for (int i = 0; i < split.length; i++) {
            if (".".equals(split[i]) || "".equals(split[i])) {
                continue;
            }
            if ("..".equals(split[i]) && !objects.isEmpty()) {
                objects.pop();
            }
            if (!"".equals(split[i]) && !".".equals(split[i]) && !"..".equals(split[i])) {
                objects.push("/"+split[i]);
            }
        }
        Collections.reverse(objects);
        return objects.stream().map(Object::toString).collect(Collectors.joining(""));


    }

    public static void main(String[] args) {
        String path = "/home/";
        String s = new SimplifyPath().simplifyPath(path);
        System.out.println(s);
    }

}
