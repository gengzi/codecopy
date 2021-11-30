package fun.gengzi.codecopy.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {


    public static void main(String[] args) {
        ConcurrentHashMap<String, Map<String, String>> concurrentHashMap = new ConcurrentHashMap<>();


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        Map<String, String> test1 = concurrentHashMap.putIfAbsent("test", map);

        concurrentHashMap.entrySet().forEach(
                (entry) -> {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
        );



        if (test1 == null) {
            System.out.println("----");
            test1 = map;

        }

        test1.put("ssss", "fff");


        concurrentHashMap.entrySet().forEach(
                (entry) -> {
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                }
        );


    }
}
