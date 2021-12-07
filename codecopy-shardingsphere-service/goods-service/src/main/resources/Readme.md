[toc]
## 商品微服务
业务场景： 现有商品表存储1千万数据，每日新增商品数10万。数据库表性能达到瓶颈。
优化：商品表分库分表

### 思路
分库分表可以拆分四种情况：
* 垂直分库，将商品表，订单表，支付记录表 拆分到为三个库
* 垂直分表，将商品表，拆分为俩张表，基础表和额外数据表
* 水平分库，将商品表，拆分表数据，到不同库
* 水平分表，将商品表，拆分表数据，到不同表

分片算法：
商品表，对单个商品的查询，收藏，下单都针对唯一的商品编码，都是针对数据库的操作
对于商品的范围查询，应该交由es搜索引擎来处理。

可以使用 商品id 的 hash 分片

一致性hash 算法

#### 测试环境
现有商品表100万数据，为了满足俩年业务需求，预计每日新增1-2万数据。为保证业务性能，
需保证单表数据量最大100万。

计算公式： 以一万数据，俩年730万数据，现有100万，总共 865万数据，以单表100万限制，需求9张表
以俩万数据，俩年




0 - 50000
50000 -10000





### 分库分表

选择某一个字段，准备水平分表  id 范围分片
增加sharding jdbc 逻辑，实现原有功能
增加双写实现，新增商品，商品数目更改以旧库为主，使用job 追加历史数据，使用job 追平数据
实现双写，以新库为主，追平差异
放弃旧库，都使用新库


### 配置变化
* 显示sql 
```java
5.x版本以前
spring.shardingsphere.props.sql.show=true

5.x版本以后，sql.show参数调整为sql-show
spring.shardingsphere.props.sql-show=true
```



### 问题
* （1） [Java 范围比较的推荐姿势](https://blog.csdn.net/w605283073/article/details/121297313)
使用guava的 range 比较器
* （2） StandardShardingStrategy 为什么要求精确分片 和 范围分片都必须存在
PreciseShardingAlgorithm是必选的，用于处理=和IN的分片
RangeShardingAlgorithm是可选的，用于处理BETWEEN AND分片，如果不配置RangeShardingAlgorithm，SQL中的BETWEEN AND将按照全库路由处理



### 一些问题
https://www.zhihu.com/question/24236515?utm_source=weibo&utm_medium=weibo_share&utm_content=share_question&utm_campaign=share_sidebar
针对一群范围对的最快查找算法设计（不要用数组）？


     2 
 10 20 49 69
    33
    
    
    
 
 
 
 

