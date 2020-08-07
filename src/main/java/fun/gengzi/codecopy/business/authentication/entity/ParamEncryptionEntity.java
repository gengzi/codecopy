package fun.gengzi.codecopy.business.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamEncryptionEntity {
    private String su;
    private String sp;
    private String serviceTime;
    private String nonce;

}
