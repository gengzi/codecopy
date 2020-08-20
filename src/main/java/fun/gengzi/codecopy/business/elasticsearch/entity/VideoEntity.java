package fun.gengzi.codecopy.business.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <h1>视频实体类</h1>
 * @author gengzi
 * @date 2020年8月14日14:54:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "video_mv")
public class VideoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //主键
    @Column("vid")
    private Integer vid;
    //视频名称
    @Column("videoName")
    private String videoname;
    //时间时长
    @Column("videoTime")
    private String videotime;
    //视频作者
    @Column("videoAuther")
    private String videoauther;
    //视频展示图片
    @Column("imgUrl")
    private String imgurl;
    //视频地址
    @Column("videoUrl")
    private String videourl;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column("createtime")
    private Date createtime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    @Column("updatetime")
    private Date updatetime;
    //版本
    @Column("version")
    private Integer version;


}
