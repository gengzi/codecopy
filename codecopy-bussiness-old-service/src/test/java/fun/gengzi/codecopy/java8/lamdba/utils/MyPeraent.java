package fun.gengzi.codecopy.java8.lamdba.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPeraent {

    private MySon mySon;


    @Data
    static class MySon{
        String a;

        List<Myson2> a1;
    }

    @Data
    static class Myson2{

        String a1;
    }
}
