package fun.gengzi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 新旧数据库枚举
 */
@Getter
@AllArgsConstructor
public enum ShardingDataSourceType {
    TYPE_OLD("TYPE_OLD", "old", "旧数据库"),
    TYPE_NEW("TYPE_NEW", "new", "新分库分表数据库");
    private String type;
    private String prefix;
    private String typeName;
}