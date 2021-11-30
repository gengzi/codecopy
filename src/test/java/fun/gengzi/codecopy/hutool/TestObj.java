package fun.gengzi.codecopy.hutool;


import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import fun.gengzi.codecopy.utils.datamask.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestObj {
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Phone, keepChars = {3, 3})
    private String phone;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Email)
    private String email;
    private String test;

    private List<TestObj> testObjs;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Address, keepChars = {9, 0})
    private String[] addressinfo;
}
