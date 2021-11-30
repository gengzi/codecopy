package fun.gengzi.codecopy.business.authentication.entity;

import fun.gengzi.codecopy.utils.datamask.BeanDataMaskUtils;
import fun.gengzi.codecopy.utils.datamask.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowUserInfoVo implements Serializable {
    private static final long serialVersionUID = 7841929261201289164L;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Name)
    private String userName;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Phone, keepChars = {3, 3})
    private String phone;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Email)
    private String email;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.BankCard, keepChars = {4, 2})
    private String bankCard;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.Address, keepChars = {9, 0})
    private String address;
    @Sensitive(type = BeanDataMaskUtils.SensitiveType.IDCard, keepChars = {5, 2})
    private String idCard;
    // 加密字段
    private String idkey;
    // 签名字段
    private String signkey;


}
