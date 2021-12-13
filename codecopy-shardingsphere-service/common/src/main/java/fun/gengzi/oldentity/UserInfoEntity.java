//package fun.gengzi.oldentity;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//import java.util.Objects;
//
//@Entity
//@Table(name = "user_info", schema = "codecopy_shardingsphere", catalog = "")
//public class UserInfoEntity {
//    private int id;
//    private String name;
//    private String portrait;
//    private String phone;
//    private String password;
//    private String regIp;
//    private Boolean accountNonExpired;
//    private Boolean credentialsNonExpired;
//    private Boolean accountNonLocked;
//    private String status;
//    private boolean isDel;
//    private Timestamp createTime;
//    private Timestamp updateTime;
//
//    @Id
//    @Column(name = "id")
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    @Basic
//    @Column(name = "name")
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Basic
//    @Column(name = "portrait")
//    public String getPortrait() {
//        return portrait;
//    }
//
//    public void setPortrait(String portrait) {
//        this.portrait = portrait;
//    }
//
//    @Basic
//    @Column(name = "phone")
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    @Basic
//    @Column(name = "password")
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Basic
//    @Column(name = "reg_ip")
//    public String getRegIp() {
//        return regIp;
//    }
//
//    public void setRegIp(String regIp) {
//        this.regIp = regIp;
//    }
//
//    @Basic
//    @Column(name = "account_non_expired")
//    public Boolean getAccountNonExpired() {
//        return accountNonExpired;
//    }
//
//    public void setAccountNonExpired(Boolean accountNonExpired) {
//        this.accountNonExpired = accountNonExpired;
//    }
//
//    @Basic
//    @Column(name = "credentials_non_expired")
//    public Boolean getCredentialsNonExpired() {
//        return credentialsNonExpired;
//    }
//
//    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
//        this.credentialsNonExpired = credentialsNonExpired;
//    }
//
//    @Basic
//    @Column(name = "account_non_locked")
//    public Boolean getAccountNonLocked() {
//        return accountNonLocked;
//    }
//
//    public void setAccountNonLocked(Boolean accountNonLocked) {
//        this.accountNonLocked = accountNonLocked;
//    }
//
//    @Basic
//    @Column(name = "status")
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    @Basic
//    @Column(name = "is_del")
//    public boolean isDel() {
//        return isDel;
//    }
//
//    public void setDel(boolean del) {
//        isDel = del;
//    }
//
//    @Basic
//    @Column(name = "create_time")
//    public Timestamp getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Timestamp createTime) {
//        this.createTime = createTime;
//    }
//
//    @Basic
//    @Column(name = "update_time")
//    public Timestamp getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Timestamp updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserInfoEntity that = (UserInfoEntity) o;
//        return id == that.id &&
//                isDel == that.isDel &&
//                Objects.equals(name, that.name) &&
//                Objects.equals(portrait, that.portrait) &&
//                Objects.equals(phone, that.phone) &&
//                Objects.equals(password, that.password) &&
//                Objects.equals(regIp, that.regIp) &&
//                Objects.equals(accountNonExpired, that.accountNonExpired) &&
//                Objects.equals(credentialsNonExpired, that.credentialsNonExpired) &&
//                Objects.equals(accountNonLocked, that.accountNonLocked) &&
//                Objects.equals(status, that.status) &&
//                Objects.equals(createTime, that.createTime) &&
//                Objects.equals(updateTime, that.updateTime);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, portrait, phone, password, regIp, accountNonExpired, credentialsNonExpired, accountNonLocked, status, isDel, createTime, updateTime);
//    }
//}
