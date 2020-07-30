package fun.gengzi.codecopy.business.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.Builder;

/**
 * 官方演示案例：
 * https://github.com/spring-projects/spring-data-examples/tree/master/elasticsearch/example
 * Conference 会议
 *
 *
 * @Document(indexName="blob3",type="article")：
 * indexName：索引的名称（必填项）
 * type：索引的类型
 * @Id：主键的唯一标识
 * @Field(index=true,analyzer="ik_smart",store=true,searchAnalyzer="ik_smart",type =
 * FieldType.text)
 * index：是否设置分词
 * analyzer：存储时使用的分词器
 * searchAnalyze：搜索时使用的分词器
 * store：是否存储
 * type: 数据类型
 *
 *
 *
 */
@Data
@Builder
// 文档对象  indexName 索引名称（_index） type 文档类型 shards 分片数  replicas 副本数 0   refreshInterval 刷新间隔
@Document(indexName = "conference-index", type = "geo-class-point-type", shards = 1, replicas = 0,
        refreshInterval = "-1")
public class Conference {

    // 文档主键 唯一标识
    @Id
    private String id;
    private String name;
    // @Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(type = Date)
    private String date;
    private GeoPoint location;
    private List<String> keywords;

    // do not remove it
    public Conference() {
    }

    // do not remove it - work around for lombok generated constructor for all params
    public Conference(String id, String name, String date, GeoPoint location, List<String> keywords) {

        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.keywords = keywords;
    }
}