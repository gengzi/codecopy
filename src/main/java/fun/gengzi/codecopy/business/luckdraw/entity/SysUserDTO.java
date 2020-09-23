package fun.gengzi.codecopy.business.luckdraw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDTO extends SysUser implements Serializable {

    private static final long serialVersionUID = -343978074020579921L;
    private String token;

    @Override
    public String toString() {
        return "SysUserDTO{" +
                "token='" + token + '\'' +
                "} " + super.toString();
    }
}