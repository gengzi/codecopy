[toc]

## 单体服务
各个服务共同使用一个库下的表
屏蔽Sharding jdbc
* 移除shardingjdbc 的jar 依赖
* 放开启动类上，忽略的数据源自动配置






数据库分库分表
* 在启动类上移除数据源的自动配置
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})

```


分布式事务

