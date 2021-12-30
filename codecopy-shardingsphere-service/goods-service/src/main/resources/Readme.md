[toc]
## 商品微服务
业务场景： 现有商品表存储1千万数据，每日新增商品数10万。数据库表性能达到瓶颈。
优化：商品表分库分表

本篇模拟实现，指导性文章：
[分库分表常见概念解读+Sharding-JDBC实战](https://jishuin.proginn.com/p/763bfbd302e8)
[大众点评订单系统分库分表实践](https://tech.meituan.com/2016/11/18/dianping-order-db-sharding.html)
[进来抄作业：一次完美的分库分表实践！](https://database.51cto.com/art/202012/637727.htm)

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
* [EntityManager常用方法简介](https://www.cnblogs.com/powerwu/articles/10733838.html)
* [Spring 多线程事务管理](https://dzone.com/articles/spring-transaction-management-over-multiple-thread-1)
* [Spring Data JPA手动管理事务](https://blog.csdn.net/loushangdeanshi/article/details/106322450)
* [Spring boot 注解@Async不生效 无效 不起作用](https://blog.csdn.net/weixin_37760377/article/details/103627676)


    
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



## 双写实践

使用jmeter 模拟新增产品，商品个数减少的模拟
（1）编写新库（分库分表）写入逻辑，可能涉及旧服务业务改造，修改原有旧库写入逻辑。代码编写好后，测试，准备上线
     代码编写注意：新库写入逻辑，可以异步处理，不影响原有旧库逻辑
     新库写入逻辑允许失败，失败后不抛出异常，不回滚旧库写入数据，将失败日志记录，用于排查补偿数据
         
（2）编写迁移旧数据脚本，编写校验数据脚本，用于对比新库旧库数据是否一致
    
 （3）部署新旧库写入逻辑，双写开始。双写10分钟后，开始迁移现有旧库数据。防止提前迁入，导致新写入旧库一部分数据没有
 迁移到新库
 
 
 
 
 数据迁移，可能出现的问题
 双写过程中，
 对于新增的数据，旧库不存在，新库不存在，写入
 对于修改的数据，旧库存在，新库不存在，旧库修改成功，新库报错（从旧库拉数据，填充新库）
 对于修改的数据，旧库存在，新库也存在，旧库新库同时修改。
 
 新库迁移旧库数据，在此期间： 旧数据迁移过程中，出现旧数据更改
对于新增的数据，迁移的旧库数据不存在，新库不存在，双写会写入，不影响
对于修改的数据，迁移的旧库数据存在，新库如果存在，双写会写入，在迁移旧库到新库时，判断新库是否存在该数据，不再写入
对于修改的数据，迁移的旧库数据存在，新库如果不存在，将旧数据写入新库，后续双写会同步修改


注意：在执行的sql 如果存在变量的情况或者执行顺序不一致，数据还是可能会出现不一致  或者执行sql 就是出问题了

线程1，执行根据id 商品减1
线程2：执行根据id 商品减1

线程1： 假设商品是 5，先处理旧库，再处理新库，发现新库没有值，先存入数据， 存入4
线程2: 假设商品是 5 ，先处理旧库，再处理新库，此时线程1 旧库处理，线程2 接着处理新库旧库变为 3 ，
然后线程1 接着执行 刷新了新库为 4 。就会出现数据问题


如何限制和解决
。。。



根据更新时间，筛选今天产生数据更新的行。从旧库中查询，再从新库中查询，对比两个数据是否一致
一致，忽略，不一致，标记并将新表数据更新为旧表数据
将不一致的数据，进行日志排查，排查为什么产生数据不一致，
通过不一致排查，基本能确定问题，解决问题
未产生数据修改的行，因为这部分数据都是旧库导入，抽测检测是否一致


经过一段时间数据对比，不会再产生什么问题
开始切库，以新库为主，旧库为副
依据上述方法，继续对比数据

经过一段时间数据对比，基本没什么问题
废弃旧库配置，只使用新库



 

### 接入sharding jdbc 对jpa 原有影响
jpa 自动追加创建时间 和 更新时间，好像不生效了。需要手动设置创建时间和更新时间了
    
 time_zone	否	Asia/Shanghai	
 当前连接的mysql 必须断开连接，执行的sql才能生效
 

## 推荐阅读
* [李新杰的博客园](https://www.cnblogs.com/lixinjie/tag/Spring/)
* [带有 Spring 框架的 AOP](https://www.tutorialspoint.com/spring/aop_with_spring.htm)

### idea 技巧
* [如何看源码系列-idea中配置jdk源码和中文注释](https://blog.csdn.net/l773772189/article/details/105067297/)
* [源码如何添加注释](https://blog.csdn.net/qq_40495860/article/details/121011875)
* [IDEA类和方法注释模板设置（非常详细）](https://blog.csdn.net/xiaoliulang0324/article/details/79030752)



## 思考
当一个问题出现，不符合预期后。对于第三方源码实现，应该直接debug排查不符合预期的逻辑代码，不应该直接去设想是那部分代码的影响。
依靠经验和猜测很容易偏离解决问题的方向，并且可能会尝试一些操作来实现功能。到头来，发现真正答案就在面前。
不要被各种组件之间相容，扰乱解决问题的思路。


看源码，先看主线代码，忽略分支。不纠结单个方法实现，有时间再看每个方法实现。




