

package fun.gengzi.codecopy.business.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TestEntity {

    private List<TestNeerEntity> entity;

    @Data
    @NoArgsConstructor
    public class TestNeerEntity {


        private String parm1;
    }


}
