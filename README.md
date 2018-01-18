# ssm-test-demo

> 这是一个参考网上各路大神（详见参考）做的自用参考 demo, 欢迎大家多指教。

## 项目结构

项目结构是参考 [lihengming](https://github.com/lihengming) 大神做的。
其中 mybatis 三件套改用 spring-boot-starter 实现自动配置，
一些常量的命名风格改为固定前缀式，尤雨溪大神说这样 IDE 提示更有效率。

## 代码生成器

也是参考 lihengming 大神做的。
MBG 加了配置参数，例如不自动生成注解，布尔类的列去掉 "is_" 前缀，
自定义了类型转换，TIMESTAMP 转 Instant，TINYINT 转 Boolean，
都是参照马云爸爸的 Java 规范改的。

## 参考链接

> [spring-boot-api-project-seed](https://github.com/lihengming/spring-boot-api-project-seed) - [lihengming](https://github.com/lihengming)

> [MyBatis-Spring-Boot](https://github.com/abel533/MyBatis-Spring-Boot) - [abel533](https://github.com/abel533)

> [阿里巴巴Java开发手册（纪念版）](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E7%BA%AA%E5%BF%B5%E7%89%88%EF%BC%89.pdf)
