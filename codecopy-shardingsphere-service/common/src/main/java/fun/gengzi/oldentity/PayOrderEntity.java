//package fun.gengzi.oldentity;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Objects;
//
//@Entity
//@Table(name = "pay_order", schema = "codecopy_shardingsphere", catalog = "")
//public class PayOrderEntity {
//    private long id;
//    private String orderNo;
//    private int userId;
//    private Integer productId;
//    private String productName;
//    private BigDecimal amount;
//    private Integer count;
//    private String channel;
//    private byte status;
//    private String clientIp;
//    private String buyId;
//    private String tradeNo;
//    private Timestamp createdTime;
//    private Timestamp updatedTime;
//    private Timestamp payTime;
//    private String extra;
//    private String goodsOrderNo;
//
//    @Id
//    @Column(name = "id")
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
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
//    @Column(name = "user_id")
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    @Basic
//    @Column(name = "product_id")
//    public Integer getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Integer productId) {
//        this.productId = productId;
//    }
//
//    @Basic
//    @Column(name = "product_name")
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    @Basic
//    @Column(name = "amount")
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    @Basic
//    @Column(name = "count")
//    public Integer getCount() {
//        return count;
//    }
//
//    public void setCount(Integer count) {
//        this.count = count;
//    }
//
//    @Basic
//    @Column(name = "channel")
//    public String getChannel() {
//        return channel;
//    }
//
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }
//
//    @Basic
//    @Column(name = "status")
//    public byte getStatus() {
//        return status;
//    }
//
//    public void setStatus(byte status) {
//        this.status = status;
//    }
//
//    @Basic
//    @Column(name = "client_ip")
//    public String getClientIp() {
//        return clientIp;
//    }
//
//    public void setClientIp(String clientIp) {
//        this.clientIp = clientIp;
//    }
//
//    @Basic
//    @Column(name = "buy_id")
//    public String getBuyId() {
//        return buyId;
//    }
//
//    public void setBuyId(String buyId) {
//        this.buyId = buyId;
//    }
//
//    @Basic
//    @Column(name = "trade_no")
//    public String getTradeNo() {
//        return tradeNo;
//    }
//
//    public void setTradeNo(String tradeNo) {
//        this.tradeNo = tradeNo;
//    }
//
//    @Basic
//    @Column(name = "created_time")
//    public Timestamp getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(Timestamp createdTime) {
//        this.createdTime = createdTime;
//    }
//
//    @Basic
//    @Column(name = "updated_time")
//    public Timestamp getUpdatedTime() {
//        return updatedTime;
//    }
//
//    public void setUpdatedTime(Timestamp updatedTime) {
//        this.updatedTime = updatedTime;
//    }
//
//    @Basic
//    @Column(name = "pay_time")
//    public Timestamp getPayTime() {
//        return payTime;
//    }
//
//    public void setPayTime(Timestamp payTime) {
//        this.payTime = payTime;
//    }
//
//    @Basic
//    @Column(name = "extra")
//    public String getExtra() {
//        return extra;
//    }
//
//    public void setExtra(String extra) {
//        this.extra = extra;
//    }
//
//    @Basic
//    @Column(name = "goods_order_no")
//    public String getGoodsOrderNo() {
//        return goodsOrderNo;
//    }
//
//    public void setGoodsOrderNo(String goodsOrderNo) {
//        this.goodsOrderNo = goodsOrderNo;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PayOrderEntity that = (PayOrderEntity) o;
//        return id == that.id &&
//                userId == that.userId &&
//                status == that.status &&
//                Objects.equals(orderNo, that.orderNo) &&
//                Objects.equals(productId, that.productId) &&
//                Objects.equals(productName, that.productName) &&
//                Objects.equals(amount, that.amount) &&
//                Objects.equals(count, that.count) &&
//                Objects.equals(channel, that.channel) &&
//                Objects.equals(clientIp, that.clientIp) &&
//                Objects.equals(buyId, that.buyId) &&
//                Objects.equals(tradeNo, that.tradeNo) &&
//                Objects.equals(createdTime, that.createdTime) &&
//                Objects.equals(updatedTime, that.updatedTime) &&
//                Objects.equals(payTime, that.payTime) &&
//                Objects.equals(extra, that.extra) &&
//                Objects.equals(goodsOrderNo, that.goodsOrderNo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, orderNo, userId, productId, productName, amount, count, channel, status, clientIp, buyId, tradeNo, createdTime, updatedTime, payTime, extra, goodsOrderNo);
//    }
//}
