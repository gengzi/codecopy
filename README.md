[TOC]
## 项目说明

目的打造一个适用于复制粘贴的组件化的代码工程，把常见业务场景和工具类都提供实现

系列文章：https://gengzi.github.io/Code-animal/#/


版本号：1.9.1

参考的开源工程代码：
[从0到1构建分布式秒杀系统，脱离案例讲架构都是耍流氓](https://gitee.com/52itstyle/spring-boot-seckill)



## 包含模块
```shell script
### fun.gengzi.codecopy.config 全局配置
ConfigConsts 配置常量类
SwaggerConfig swagger 配置  swagger 可以升级为 kinfe4j
增加了 logback 的配置 logback.xml 
CacheConfig 缓存配置类
ThreadPoolConfig 线程池配置类
ShardingJDBCConfig sharding 分库分表配置

### fun.gengzi.codecopy.utils 工具类
常用工具类
IPUtils ip工具类
SpringContextUtils spring上下文工具类
引入hutool 工具包
引入vjtools 工具包
datamask 基于 vjtools 日志脱敏，实现的对象脱敏工具类
AESUtils RSAUtils 基于hutool 实现的加密解密工具类



### fun.gengzi.codecopy.generator 生成器
mysql 版本的 entity 的生成器
postman:
http://localhost:8089/codeBuildNew?tablename=dic_data

### fun.gengzi.codecopy.constant 全局常量
Constants 全局常量类
RspCodeEnum 响应code枚举类

### fun.gengzi.codecopy.business 业务模块
这里按业务来区分不同的 controller service dao entity
没有把所有的controller 都放在一个包下，不好区分不同业务

增加短链接生成服务   shorturl
增加接口权限校验服务 authentication
增加接口参数的加密解密的服务  authentication  下的  SecurityInterfaceController
增加sharding jdbc 分库分表服务 subdata
增加分片业务实例，检测在指定ip范围的mysql链接是否成功，成功的插入数据库 connection
（针对MySQL数据库的勒索病毒预警 https://www.freebuf.com/articles/system/213975.html ）

增加elatsicsearch 相关的一些操作 elasticsearch
增加了一个对 mysql 链接检测的服务，通过接口，可以得知 某个mysql 服务是否开启，并连接成功 connection
增加演示商品信息缓存实践- 解决缓存的三大问题，缓存穿透，缓存雪崩，缓存击穿 product 
增加工具类的测试服务 utilstest  （html转pdf）


### fun.gengzi.codecopy.vo 全局vo
ReturnData 响应实体

### fun.gengzi.codecopy.aop 切面(aspectj)
LimitAspect 限流 AOP ，通过@ServiceLimit注解修饰于方法，限制ip每秒最多访问五次被 @ServiceLimit 的方法
LockAspect 同步锁 AOP
BusinessAuthentication token 校验aop 用于在修饰的方法，增加 token 校验

### fun.gengzi.codecopy.exception 异常处理
全局异常处理

### fun.gengzi.codecopy.cache 本地缓存
增加本地缓存实现
实现系统初始化后，加载缓存数据到本地内容
redis缓存实现

```
## 注意

* ## 针对lombok的使用

```shell script
对于 pInfo 类似的字段,生成的get 和 set 方法都是 getPInfo setPInfo ,并不是 getpInfo 。
所以在写字段的时候，就不要写  pInfo 类似的字段

对于redis ，当系统中存储多个key，建议将key 分文件夹，分不同的数据库存储。
便于使用工具查看

某些业务，知道存在安全问题，不要有侥幸心理，直接在第一次改掉，避免在业务代码推挤太多后，再修改。


 



```
## 问题
* swagger 问题
[控制台warn提示 Illegal DefaultValue null for parameter type integer java#](https://www.cnblogs.com/michael-xiang/p/12305946.html)
@ApiModelProperty 注解

* spring 配置
[springboot：springboot配置文件（配置文件占位符、Profile、配置文件的加载位置）](https://www.cnblogs.com/Mrchengs/p/10120140.html)