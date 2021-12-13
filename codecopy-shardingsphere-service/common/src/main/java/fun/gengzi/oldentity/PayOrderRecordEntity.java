//package fun.gengzi.oldentity;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//import java.util.Objects;
//
//@Entity
//@Table(name = "pay_order_record", schema = "codecopy_shardingsphere", catalog = "")
//public class PayOrderRecordEntity {
//    private int id;
//    private String orderNo;
//    private String userNo;
//    private String type;
//    private String fromStatus;
//    private Integer paidAmount;
//    private String remark;
//    private String createdBy;
//    private Timestamp createdAt;
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
//    @Column(name = "order_no")
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    @Basic
//    @Column(name = "user_no")
//    public String getUserNo() {
//        return userNo;
//    }
//
//    public void setUserNo(String userNo) {
//        this.userNo = userNo;
//    }
//
//    @Basic
//    @Column(name = "type")
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    @Basic
//    @Column(name = "from_status")
//    public String getFromStatus() {
//        return fromStatus;
//    }
//
//    public void setFromStatus(String fromStatus) {
//        this.fromStatus = fromStatus;
//    }
//
//    @Basic
//    @Column(name = "paid_amount")
//    public Integer getPaidAmount() {
//        return paidAmount;
//    }
//
//    public void setPaidAmount(Integer paidAmount) {
//        this.paidAmount = paidAmount;
//    }
//
//    @Basic
//    @Column(name = "remark")
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    @Basic
//    @Column(name = "created_by")
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    @Basic
//    @Column(name = "created_at")
//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PayOrderRecordEntity that = (PayOrderRecordEntity) o;
//        return id == that.id &&
//                Objects.equals(orderNo, that.orderNo) &&
//                Objects.equals(userNo, that.userNo) &&
//                Objects.equals(type, that.type) &&
//                Objects.equals(fromStatus, that.fromStatus) &&
//                Objects.equals(paidAmount, that.paidAmount) &&
//                Objects.equals(remark, that.remark) &&
//                Objects.equals(createdBy, that.createdBy) &&
//                Objects.equals(createdAt, that.createdAt);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, orderNo, userNo, type, fromStatus, paidAmount, remark, createdBy, createdAt);
//    }
//}
