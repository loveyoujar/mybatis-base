**Mybatis模板引擎生成工具**

1.把依赖jar包导入到项目,jar包都存放在lib包下
2.打开src-> buildCodeConfig.xml 文件,配置参数
3.点击根目录启动类
4.生成的代码在generator-output 目录
5.所依赖的几个核心类存在在otherutil包下.包涵分页工具类, 基础增删改查类

注意: 模板代码都在template_app.${basepackage}目录下,根据实际情况可修改里面的模板代码