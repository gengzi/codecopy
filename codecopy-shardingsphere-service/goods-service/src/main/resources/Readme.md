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








