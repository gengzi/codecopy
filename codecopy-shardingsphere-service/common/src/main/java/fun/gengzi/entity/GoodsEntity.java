package fun.gengzi.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "goods")
public class GoodsEntity {
    private Long id;
    private String goodsName;
    private Integer price;
    private String goodsDescriptionMarkDown;
    private String goodsDescription;
    private String goodsType;
    private String goodsImgUrl;
    private Timestamp autoOnlineTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte isDel;
    private Integer sales;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "goods_name")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Basic
    @Column(name = "price")
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "goods_description_mark_down")
    public String getGoodsDescriptionMarkDown() {
        return goodsDescriptionMarkDown;
    }

    public void setGoodsDescriptionMarkDown(String goodsDescriptionMarkDown) {
        this.goodsDescriptionMarkDown = goodsDescriptionMarkDown;
    }

    @Basic
    @Column(name = "goods_description")
    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    @Basic
    @Column(name = "goods_type")
    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Basic
    @Column(name = "goods_img_url")
    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    @Basic
    @Column(name = "auto_online_time")
    public Timestamp getAutoOnlineTime() {
        return autoOnlineTime;
    }

    public void setAutoOnlineTime(Timestamp autoOnlineTime) {
        this.autoOnlineTime = autoOnlineTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "is_del")
    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "sales")
    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsEntity that = (GoodsEntity) o;
        return id == that.id &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(price, that.price) &&
                Objects.equals(goodsDescriptionMarkDown, that.goodsDescriptionMarkDown) &&
                Objects.equals(goodsDescription, that.goodsDescription) &&
                Objects.equals(goodsType, that.goodsType) &&
                Objects.equals(goodsImgUrl, that.goodsImgUrl) &&
                Objects.equals(autoOnlineTime, that.autoOnlineTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDel, that.isDel) &&
                Objects.equals(sales, that.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, goodsName, price, goodsDescriptionMarkDown, goodsDescription, goodsType, goodsImgUrl, autoOnlineTime, createTime, updateTime, isDel, sales);
    }
}
