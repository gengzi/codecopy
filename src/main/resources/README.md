[toc]
## 项目说明
2020年5月23日19:46:33

适用于复制粘贴的组件化的代码工程

版本号：1.0.0

## 包含模块
```shell script
### fun.gengzi.codecopy.config 全局配置
swagger 配置

### fun.gengzi.codecopy.utils 工具类

### fun.gengzi.codecopy.generator 生成器
mysql 版本的 entity 的生成器

### fun.gengzi.codecopy.constant 全局常量

### fun.gengzi.codecopy.business 业务模块

### fun.gengzi.codecopy.vo 全局vo

```
## 注意
```shell script
## lombok 
对于 pInfo 类似的字段,生成的get 和 set 方法都是 getPInfo setPInfo ,并不是 getpInfo 。
所以在写字段的时候，就不要写  pInfo 类似的字段
 



```
## 问题
* swagger 问题
[控制台warn提示 Illegal DefaultValue null for parameter type integer java#](https://www.cnblogs.com/michael-xiang/p/12305946.html)
@ApiModelProperty 注解

* spring 配置
[springboot：springboot配置文件（配置文件占位符、Profile、配置文件的加载位置）](https://www.cnblogs.com/Mrchengs/p/10120140.html)