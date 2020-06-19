package fun.gengzi.codecopy.business.subdata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oh_enterprise_info")
public class OhEnterpriseInfo {
    //主键ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("ENTERPRISE_INFO_ID")
    private String enterpriseInfoId;
    //用人单位名称
    @Column("ENTERPRISE_NAME")
    private String enterpriseName;
    //统一社会信用代码
    @Column("CREDIT_CODE")
    private String creditCode;
    //所属地区代码
    @Column("ADDRESS_CODE")
    private String addressCode;
    //经济类型代码
    @Column("ECONOMIC_TYPE_CODE")
    private String economicTypeCode;
    //经济类型代码版本
    @Column("ECONOMIC_TYPE_VISION")
    private String economicTypeVision;
    //行业类别代码
    @Column("INDUSTRY_CATEGORY_CODE")
    private String industryCategoryCode;
    //行业类别代码版本
    @Column("INDUSTRY_CATEGORY_VISION")
    private String industryCategoryVision;
    //企业规模代码
    @Column("BUSINESS_SCALE_CODE")
    private String businessScaleCode;
    //企业规模代码版本
    @Column("BUSINESS_SCALE_VISION")
    private String businessScaleVision;
    //通讯地址
    @Column("ADDRESS_DETAIL")
    private String addressDetail;
    //邮政编码
    @Column("ADDRESS_ZIP_CODE")
    private String addressZipCode;
    //单位联系人
    @Column("ENTERPRISE_CONTACT")
    private String enterpriseContact;
    //单位联系电话
    @Column("CONTACT_TELPHONE")
    private String contactTelphone;
    //创建人账号
    @Column("CREATE_USER_ACCOUNT")
    private String createUserAccount;
    //创建人姓名
    @Column("CREATE_USER_NAME")
    private String createUserName;
    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column("CREATE_DATE")
    private Date createDate;
    //修改人账号
    @Column("UPDATE_USER_ACCOUNT")
    private String updateUserAccount;
    //修改人姓名
    @Column("UPDATE_USER_NAME")
    private String updateUserName;
    //修改日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column("UPDATE_DATE")
    private Date updateDate;
    //是否删除, 0 否 1 是
    @Column("is_del")
    private Integer isDel;
    //日志 ID, 可用作日志的查询
    @Column("guid")
    private String guid;
    //版本号
    @Column("data_version")
    private Integer dataVersion;

    //创建人联系方式
    @Column("USER_PHONE")
    private String userPhone;

}