# bean-generator

#### 介绍

原生 JDK + FreeMarker 编写的 Bean 生成器，连接 MySQL 数据库后根据表信息和列信息生成本地 bean 文件，简化开发。

依赖如下：

- mysql-connector-java
- freemarker
- lombok（optional）
- fastjson（optional）

#### 配置项

|配置项|说明（括号内是默认值）|
|---|---|
|packageName|指定实体类所在的包名|
|camelCase|是否使用驼峰命名（true）|
|useLombok|是否开启Lombok的注解（true）|
|chainAccess|是否开启链式调用（false）|
|allArgsConstructor|是否生成全参构造器（false）|
|output|输出路径|

#### 使用方法

在`generator.properties`文件中设置 MySQL 连接参数。

```java
GeneratorConfig gc = new GeneratorConfig();
//自定义配置...
Generator gen = new Generator(gc);
gen.generate();
```

通过 gc.ofType() 方法可以自定义类型映射，比如`gc.ofType("TINYINT(1)", "Boolean")`

#### 下一步

- 允许排除不需要的表
- 将配置集中在 GeneratorConfig 类中