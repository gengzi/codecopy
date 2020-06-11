package fun.gengzi.codecopy.sys.entity;


import javax.persistence.*;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


@Entity
@Table(name = "sys_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 4095915507924264952L;
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //
    @Column("ID")
    private Integer id;
    //
    @Column("PID")
    private Integer pid;
    //
    @Column("NAME")
    private String name;
    //
    @Column("TYPE")
    private String type;
    //
    @Column("SORT")
    private Integer sort;
    //
    @Column("URL")
    private String url;
    //
    @Column("PERM_CODE")
    private String permCode;
    //
    @Column("ICON")
    private String icon;
    //
    @Column("STATE")
    private String state;
    //
    @Column("DESCRIPTION")
    private String description;
    //
    @Column("FROM_APP_IDENTIFY_ID")
    private String fromAppIdentifyId;
}