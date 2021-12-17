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
* 多数据源配置 https://www.cnblogs.com/ll409546297/p/10496346.html


### 一些问题
* https://www.zhihu.com/question/24236515?utm_source=weibo&utm_medium=weibo_share&utm_content=share_question&utm_campaign=share_sidebar
针对一群范围对的最快查找算法设计（不要用数组）？
* [Java反射——如何通过Java中的反射调用对象的方法？](https://blog.csdn.net/ly_xiamu/article/details/82900482)
* [Spring AOP组合使用多个切入点表达式](https://blog.csdn.net/qq_32224047/article/details/107103819)
* [JPA项目多数据源模式整合sharding-jdbc实现数据脱敏](http://www.kailing.pub/article/index/arcid/279.html)
* [Spring boot 注解@Async不生效 无效 不起作用](https://blog.csdn.net/weixin_37760377/article/details/103627676)
* [第二十四章：SpringBoot项目整合JPA多数据源配置](https://www.jianshu.com/p/9f812e651319)
     2 
 10 20 49 69
    33
    
## 新旧数据源配置 
引入sharding 后，一个项目中包含多数据源
旧数据源，和新数据源都需要单独配置
第一个想法：将新旧数据源都放在同一个数据库中，新旧代码都合在一起
问题：发现 原有业务jpa 和 sharding的数据源 都需要手动配置，不能使用 默认配置，还要区分不同的 jpa 实例
比较麻烦
好处：代码都在一个工程下，改造代码简单
后续切换新库作为主库比较简单。





第二个想法：创建新项目，用于sharding 的数据源，开发新接口，用于同步数据。
旧项目中改造代码，触发的sql 流程，再写入新项目

优点：改造旧项目为新工程简单，
需要写一批接口，用于旧项目调用，进行数据更新
后续使用新库代替旧库，可能还存在接口调用。比较麻烦


第三个想法：原有jpa  和 分库分表 jpa 都切换都 sharding jdbc
通过 hint 强制分片策略，来区分old 库和 分库分表库，手动配置执行 不同sql 时，触发不同库



### 第二个问题
在规划区分数据库时，执行每个sql 都要植入一段 hintManager 的代码，会修改原有业务逻辑
考虑，不修改原有业务逻辑，在对jpa 执行sql 时，再植入这段逻辑。

第一个想法：写一个公共植入hintManager service层，根据做什么动作，就参数传递， 使用反射，生成一个jpa 方法调用


第二个想法：这段逻辑既然每个 jpa 方法都要执行，写一个 aop 来实现，环绕通知
好处：无需改造原有业务逻辑

    
    
 
 
 
 

## 推荐阅读
* [李新杰的博客园](https://www.cnblogs.com/lixinjie/tag/Spring/)
* [带有 Spring 框架的 AOP](https://www.tutorialspoint.com/spring/aop_with_spring.htm)
