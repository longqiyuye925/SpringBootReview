# SpringBootAOP

https://mrbird.cc/Spring-Boot-AOP%20log.html

### @Target:注解的作用目标

@Target(ElementType.TYPE)——接口、类、枚举、注解
@Target(ElementType.FIELD)——字段、枚举的常量
@Target(ElementType.METHOD)——方法
@Target(ElementType.PARAMETER)——方法参数
@Target(ElementType.CONSTRUCTOR) ——构造函数
@Target(ElementType.LOCAL_VARIABLE)——局部变量
@Target(ElementType.ANNOTATION_TYPE)——注解
@Target(ElementType.PACKAGE)——包

### @Retention：注解的保留位置

RetentionPolicy.SOURCE:这种类型的Annotations只在源代码级别保留,编译时就会被忽略,在class字节码文件中不包含。
RetentionPolicy.CLASS:这种类型的Annotations编译时被保留,默认的保留策略,在class文件中存在,但JVM将会忽略,运行时无法获得。
RetentionPolicy.RUNTIME:这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用。
@Document：说明该注解将被包含在javadoc中
@Inherited：说明子类可以继承父类中的该注解

### IntelliJ IDEA生成 Serializable 序列化 UID 的快捷键

![image-20210514152915093](C:\Users\wxc\AppData\Roaming\Typora\typora-user-images\image-20210514152915093.png)



Settings - Inspections - 输入Serializable class without 'serialVersionUID' - 在红框处打钩



![image-20210514153053036](C:\Users\wxc\AppData\Roaming\Typora\typora-user-images\image-20210514153053036.png)



移动到实体类上，alt + enter 选择红框处。

### 快捷生成set get:ctrl + o

### 接口中的方法默认都是public abstract

### @Autowired是spring的注解，用来自动装配，把控制反转的类的对象注入到这里



| 注解        | 含义                                         |
| ----------- | -------------------------------------------- |
| @Component  | 最普通的组件，可以被注入到spring容器进行管理 |
| @Repository | 作用于持久层                                 |
| @Service    | 作用于业务逻辑层                             |
| @Controller | 作用于表现层（spring-mvc的注解）             |

# SpringAOP中的JointPoint和ProceedingJoinPoint使用详解

https://blog.csdn.net/kouryoushine/article/details/105299956



### 切面不触发的原因，没有引入下面这个依赖。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```





# Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.

Fri May 14 20:56:18 CST 2021

There was an unexpected error (type=Not Found, status=404).

- controller没有放在启动类的同级或者下级目录里面

- 配合文件里端口号与实际输入值不一样

- 单纯的输出地址

- ```
  设置了server.servlet.context-path ，没有输入它
  ```





