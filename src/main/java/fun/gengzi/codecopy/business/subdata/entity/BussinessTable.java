package fun.gengzi.codecopy.business.subdata.entity;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oh_enterprise_info")
public class BussinessTable {
    //主键ID
    @Id
    @Column(name = "ENTERPRISE_INFO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String enterpriseInfoId;
    @Column(name = "ENTERPRISE_NAME")
    private String enterpriseName;
    @Column(name = "CREDIT_CODE")
    private String creditCode;
    //所属地区代码
    @Column(name = "ADDRESS_CODE")
    private String addressCode;
    //经济类型代码
    @Column(name = "ECONOMIC_TYPE_CODE")
    private String economicTypeCode;
    //经济类型代码版本
    @Column(name = "ECONOMIC_TYPE_VISION")
    private String economicTypeVision;
    //行业类别代码
    @Column(name = "INDUSTRY_CATEGORY_CODE")
    private String industryCategoryCode;
    //行业类别代码版本
    @Column(name = "INDUSTRY_CATEGORY_VISION")
    private String industryCategoryVision;
    //企业规模代码
    @Column(name = "BUSINESS_SCALE_CODE")
    private String businessScaleCode;
    //企业规模代码版本
    @Column(name = "BUSINESS_SCALE_VISION")
    private String businessScaleVision;
    //通讯地址
    @Column(name = "ADDRESS_DETAIL")
    private String addressDetail;
    //邮政编码
    @Column(name = "ADDRESS_ZIP_CODE")
    private String addressZipCode;
    //单位联系人
    @Column(name = "ENTERPRISE_CONTACT")
    private String enterpriseContact;
    //单位联系电话
    @Column(name = "CONTACT_TELPHONE")
    private String contactTelphone;
    //创建人账号
    @Column(name = "CREATE_USER_ACCOUNT")
    private String createUserAccount;
    //创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;
    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;
    //修改人账号
    @Column(name = "UPDATE_USER_ACCOUNT")
    private String updateUserAccount;
    //修改人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;
    //修改日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_DATE")
    private Date updateDate;
    //是否删除, 0 否 1 是
    @Column(name = "is_del")
    private Integer isDel;
    //日志 ID, 可用作日志的查询
    @Column(name = "guid")
    private String guid;
    //版本号
    @Column(name = "data_version")
    private Integer dataVersion;

    //创建人联系方式
    @Column(name = "USER_PHONE")
    private String userPhone;

}