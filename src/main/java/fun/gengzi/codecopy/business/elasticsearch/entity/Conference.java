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

@Data
@Builder
@Document(indexName = "conference-index", type = "geo-class-point-type", shards = 1, replicas = 0,
        refreshInterval = "-1")
public class Conference {

    private @Id
    String id;
    private String name;
    private @Field(type = Date)
    String date;
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