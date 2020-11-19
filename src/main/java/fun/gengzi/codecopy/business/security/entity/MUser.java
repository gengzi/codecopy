

//package fun.gengzi.codecopy.business.security.entity;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//@Data
//public class MUser implements UserDetails {
//
//    private Long id;
//    private String username;
//    private String realName;
//    private String headUrl;
//    private Integer gender;
//    private String email;
//    private String mobile;
//    private Long deptId;
//    private String password;
//    private Integer status;
//    private Integer superAdmin;
//    /**
//     * 部门数据权限
//     */
//    private List<Long> deptIdList;
//    /**
//     * 帐户是否过期
//     */
//    private boolean isAccountNonExpired = true;
//    /**
//     * 帐户是否被锁定
//     */
//    private boolean isAccountNonLocked = true;
//    /**
//     * 密码是否过期
//     */
//    private boolean isCredentialsNonExpired = true;
//    /**
//     * 帐户是否可用
//     */
//    private boolean isEnabled = true;
//    /**
//     * 拥有权限集合
//     */
//    private Set<GrantedAuthority> authorities;
//
//
//    /**
//     * 重写 equals 和 hashcode 方法
//     *
//     * @param obj
//     * @return
//     */
//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof MUser ? this.getUsername().equals(((MUser) obj).getUsername()) : false;
//    }
//
//    @Override
//    public int hashCode() {
//        return this.getUsername().hashCode();
//    }
//
//    @Override
////    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }
//
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return this.isAccountNonExpired;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.isAccountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return this.isCredentialsNonExpired;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.isEnabled;
//    }
//}
