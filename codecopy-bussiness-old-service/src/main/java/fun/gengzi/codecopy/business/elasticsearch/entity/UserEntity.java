//package fun.gengzi.codecopy.business.elasticsearch.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.*;
//import org.joda.time.DateTime;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
///**
// * * @Document(indexName="blob3",type="article")：
// * * indexName：索引的名称（必填项）
// * * type：索引的类型
// * * @Id：主键的唯一标识
// * * @Field(index=true,analyzer="ik_smart",store=true,searchAnalyzer="ik_smart",type =
// * * FieldType.text)
// * * index：是否设置分词
// * * analyzer：存储时使用的分词器
// * * searchAnalyze：搜索时使用的分词器
// * * store：是否存储
// * * type: 数据类型
// */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Document(indexName = "user", type = "baseinfo")
//public class UserEntity implements Serializable {
//    @Id
//    private Long uid;
//    @Field(index = true, searchAnalyzer = "ik_smart", type = FieldType.Text)
//    private String userName;
//    private String pinYinName;
//    @Field
//    private Integer age;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Temporal(TemporalType.DATE)
//    private Date birthday;
//    private List<Img> imgs;
//
//    @Getter
//    @Setter
//    static class Img {
//        private Long imgid;
//        private String imgUrl;
//    }
//
//}
