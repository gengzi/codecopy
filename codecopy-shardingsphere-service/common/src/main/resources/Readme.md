[toc]

## 单体服务
各个服务共同使用一个库下的表
屏蔽Sharding jdbc
* 移除shardingjdbc 的jar 依赖
* 放开启动类上，忽略的数据源自动配置


### pay-service 支付微服务

### order-service 订单微服务

### goods-service 商品微服务
1, 初始化测试数据100万，商品数据
2，减库存的业务实现,查商品列表分页，查某个商品信息 
3, 接入sharding jdbc










数据库分库分表
* 在启动类上移除数据源的自动配置
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})

```


分布式事务




### 问题
* [jpa分页查询](https://www.jianshu.com/p/14cd90f32d4d)
* [Spring Data JPA 的时间注解：@CreatedDate 和 @LastModifiedDate](https://www.jianshu.com/p/30aef87f3171)