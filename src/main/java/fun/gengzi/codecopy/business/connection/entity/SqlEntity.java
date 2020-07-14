package fun.gengzi.codecopy.business.connection.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SqlEntity {
    // ip
    private String ip;
    // 端口
    private String port;
    // 用户名
    private String username;
    // 密码
    private String password;
}
