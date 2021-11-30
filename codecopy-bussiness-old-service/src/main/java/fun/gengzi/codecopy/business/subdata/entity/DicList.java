package fun.gengzi.codecopy.business.subdata.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


/**
 * 字典实体类
 * @author gengzi
 * @date 2020年6月29日10:24:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dic_list")
public class DicList {

    //TODO 会报错 ：HibernateException: The database returned no natively generated identity value
    // @GeneratedValue(strategy = GenerationType.IDENTITY)  提示 数据库没有返回生成的标识值

    // increment 的策略是，返回主键的最大值，+1 作为主键
    @Id
    // generator 主键生成器的名称
    @GeneratedValue(generator="increment")
    // 自定义主键生成策略
    @GenericGenerator(name = "increment", strategy = "increment")
    // 主键
    @Column(name = "ID")
    private Integer id;
    // 数据字典中文名称
    @Column(name = "NAME")
    private String name;
    // 数据字典key
    @Column(name = "KEYWORDS")
    private String keywords;
    // 数组字典含义
    @Column(name = "NOTES")
    private String notes;
}