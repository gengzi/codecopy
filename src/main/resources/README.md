[TOC]
## 项目说明

目的打造一个适用于复制粘贴的组件化的代码工程，把常见业务场景和工具类都提供实现

版本号：1.0.3

参考的开源工程代码：
[从0到1构建分布式秒杀系统，脱离案例讲架构都是耍流氓](https://gitee.com/52itstyle/spring-boot-seckill)



## 包含模块
```shell script
### fun.gengzi.codecopy.config 全局配置
ConfigConsts 配置常量类
SwaggerConfig swagger 配置
增加了 logback 的配置 logback.xml 

### fun.gengzi.codecopy.utils 工具类
常用工具类
IPUtils ip工具类
SpringContextUtils spring上下文工具类




### fun.gengzi.codecopy.generator 生成器
mysql 版本的 entity 的生成器

### fun.gengzi.codecopy.constant 全局常量
Constants 全局常量类
RspCodeEnum 响应code枚举类

### fun.gengzi.codecopy.business 业务模块
这里按业务来区分不同的 controller service dao entity
没有把所有的controller 都放在一个包下，不好区分不同业务


### fun.gengzi.codecopy.vo 全局vo
ReturnData 响应实体

### fun.gengzi.codecopy.aop 切面(aspectj)
LimitAspect 限流 AOP ，通过@ServiceLimit注解修饰于方法，限制ip每秒最多访问五次被 @ServiceLimit 的方法
LockAspect 同步锁 AOP

### fun.gengzi.codecopy.exception 异常处理



```
## 注意

* ## 针对lombok的使用

```shell script
对于 pInfo 类似的字段,生成的get 和 set 方法都是 getPInfo setPInfo ,并不是 getpInfo 。
所以在写字段的时候，就不要写  pInfo 类似的字段
 



```
## 问题
* swagger 问题
[控制台warn提示 Illegal DefaultValue null for parameter type integer java#](https://www.cnblogs.com/michael-xiang/p/12305946.html)
@ApiModelProperty 注解

* spring 配置
[springboot：springboot配置文件（配置文件占位符、Profile、配置文件的加载位置）](https://www.cnblogs.com/Mrchengs/p/10120140.html)