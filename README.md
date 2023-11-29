# -Online_video_learning_system-

前排提示：该项目是基于SpringBoot的在线视频学习系统，仅作学习交流使用，**请勿商用**!

# 项目基本信息:

**SpringBoot版本：2.7.5**

**MySQL版本：8.0.31**

**IDEA版本：2022.2.3**

## 报错：

1、在**调用端的启动类（service_edu）**上添加注解：`@EnableFeignClients`时报错：No Feign Client for loadBalancing defined. Did you forget to include spring-cloud-starter-loadbalancer?

解决办法：

```xml
 其实解决原因他已经告诉我们了，就是说忘记加上 spring-cloud-starter-loadbalancer
 这是因为由于SpringCloud Feign在Hoxton.M2 RELEASED版本之后不再使用Ribbon而是使用spring-cloud-loadbalancer，所以不引入spring-cloud-loadbalancer会报错。
 需要在nacos注册发现依赖中把Ribbon 进行客户端负载均衡去掉就可以了
 
 <!--        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
            <version>2.2.10.RELEASE</version>
        </dependency>-->

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
            <version>3.1.5</version>
        </dependency>
        
```


# 网关
报错：网关出现503错误是因为全局过滤器没有加载（ReactiveLoadBalancerClientFilter），只需要将含有这个过滤器的依赖进行导入就行了
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-loadbalancer</artifactId>
<version>3.1.5</version>
</dependency>



**最后一次更新时间：2023年02月05日18点10分**





