# 企业级微服务架构项目-尚上优选-响应式编程版本【技术无好坏，适合的才是最好的，本项目主要目的为学习技术】

## 一 项目概述

### 1 项目介绍

项目来自于尚硅谷出版《尚上优选》项目，视频地址：https://www.bilibili.com/video/BV19M4y1q7Lt?p=9&vd_source=9984a630d4e421030d2db88868f6ca31
本仓库将对尚上优选后端服务进行升级，主要包含以下2部分升级：
1.框架版本升级
2.将项目改为响应式版本

## 关键字：响应式，webflux，netty，springboot3.0+

## 二 项目框架版本升级

### 1 升级内容

|         框架         |     升级前版本     |     升级后版本      |
|:------------------:|:-------------:|:--------------:|
|        java        |       8       |      17　　      |
|      Java EE       |       8       | Jakarta EE 9　　 |
|     springboot     | 2.3.6.RELEASE |    3.0.1　　     |
|    springCloud     |  Hoxton.SR8   |   2022.0.0　    |
| springCloudAlibaba | 2.2.2.RELEASE | 2022.0.0.0-RC1 |
|    mybatis-plus    |     3.4.1     |    3.5.3.1     |
|     httpclient     |     4.5.1     |     4.5.13     |
|      knife4j       | 2.3.6.RELEASE |     4.1.0      |

### 2 遇到的问题及解决方案

1.springboot3.0+所有与servlet相关依赖包名改变，需要引入Jakarta EE 9相关依赖并修改包引用路径
2.springboot3.0+使用mybatis-plus的版本必须是3.5.3.1，参考如下文章：https://github.com/baomidou/mybatis-plus/issues/5187
3.nacos最新版支持grpc通信，所以之前的docker脚本需要升级为以下：

``` 
docker run --env MODE=standalone --name nacos --restart=always -d -p 8848:8848  -p 9848:9848 -p 9849:9849  nacos/nacos-server
```

4.elasticsearch升级之后，docker服务端脚本也需要安装8.5.3+左右的版本，本地测试8.5.1也可以

5.待更新......

## 三 项目升级成响应式版本

### 1 升级计划

| 升级响应式之前技术          |   升级响应式之前技术是否支持响应式    | 升级响应式之后技术     | 升级响应式之后技术是否支持响应式 | 编码是否完成 |
|--------------------|:---------------------:|---------------|:----------------:|--------|
| tomcat             | 原生不支持响应式,也能做，但不如netty | netty         |        是         | 已完成    |
| openFeign          |           否           | webclient     |        是         | 已完成   |
| springmvc          |           否           | springwebflux |        是         | 已完成    |
| jwt                |           否           | sa-token      |        是         | 已完成    |
| CompletableFuture  |           否           | mono或flux     |        是         | 正在开发   |
| ThreadPoolExecutor |           否           | Schedulers    |        是         | 正在开发   |
| nacos              |       支持，需要替换依赖       | nacos         |        是         | 正在开发   |
| redis              |       支持，需要替换依赖       | redis         |        是         | 正在开发   |
| knife4j            |       支持，需要替换依赖       | knife4j       |        是         | 正在开发   |
| rabbit             |       支持，需要替换依赖       | rabbit        |        是         | 正在开发   |
| 待更新                |        ......         | ......        |      ......      | ...... |
