package fun.gengzi.codecopy.business.subdata.entity;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dic_data")
public class DicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //
    @Column(name = "ID")
    private Integer id;
    //
    @Column(name = "DIC_LIST_ID")
    private Integer dicListId;
    //
    @Column(name = "PID")
    private Integer pid;
    //
    @Column(name = "CODE")
    private String code;
    //
    @Column(name = "NAME")
    private String name;
    //
    @Column(name = "SORT")
    private Integer sort;
    //
    @Column(name = "IS_USE")
    private Integer isUse;
    //
    @Column(name = "NOTES")
    private String notes;
    //
    @Column(name = "OTHERWHERE")
    private String otherwhere;
    //
    @Column(name = "OTHERHAVING")
    private String otherhaving;
    //
    @Column(name = "BEFCLEAN_NAME")
    private String befcleanName;
    //
    @Column(name = "AFTCLEAN_NAME")
    private String aftcleanName;
}