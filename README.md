# Wi-Fi Probe Analysis
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
> WIFI探针是一种可以记录附近mac地址的嗅探器，可以根据收集到的mac地址进行数据分析，获得附近的人流量、入店量、驻留时长等信息  
> 本系统以Spark + Hadoop为核心，搭建了基于WIFI探针的大数据分析系统

- 项目演示
[演示视频](http://v.youku.com/v_show/id_XMjg1OTYxNzg1Ng==.html?spm=a2hzp.8244740.0.0)

## Module Introduction

### 系统配置说明

2018年9月19日更新  
[WifiProbeAnalysis 系统安装配置说明](https://github.com/wanghan0501/WiFiProbeAnalysis/blob/master/WifiProbeAnalysis%20%E7%B3%BB%E7%BB%9F%E5%AE%89%E8%A3%85%E9%85%8D%E7%BD%AE%E8%AF%B4%E6%98%8E.md)

### Computing-Core
- 系统核心， 负责实时计算以及离线计算
- 主要技术或API：

名称 | 解释
----| ----|
Spark| 分析程序核心API
Hadoop | 分析程序核心API
Mybatis | 操作Mysql的API
SharedJedis | 分布式Redis的API
Log4j | 日志记录工具
Accumulator | 累加器，相当于Spark中的全局变量
FastJson | Json解析工具
HBase Client | 操作HBase
 

### Kafka-Core
- 利用Kafka缓存数据， 供实时分析程序提取
- 将原始数据存储进HBase，供离线分析程序使  

> 主要技术或API： 
>
 名称  |  解释  |
 ------|--------|
 Kafka |基于内存的消息队列，负责缓冲数据，供实时分析程序提取
 HBase Client | 操作HBase

### Web-UI
- 数据解析和展示
> 主要技术或API：

  名称 | 解释 |
  -----|-----|
  React| 前端框架
  Redux| 负责管理与后端的数据交互
  webSocket | 实时数据获取
  echarts | 绘图
  Ant Design | UI框架

### Web-Server
- 网站服务器端，负责处理前端请求
> 主要技术或API：

 名称 | 解释 |
 -----|-----|
 SSM 架构| Spring MVC + Spring + Mybatis框架
 Spring-Data-Redis | 封装对redis的操作
 FastJson| 处理Json数据
 Alidayu | 阿里大鱼，发送短信
 POI | 导出Excel报表
 
 
### Databases in System

 - MySQL
 
 > 关系型数据库，负责存储一些不会经常读取的数据,比如分析程序的参数配置、商场信息等
 
 - HBase
 > 分布式非关系型数据库，用于永久性存储原始数据，供离线分析程序使用
 
 - Redis
 > 非关系型数据库，适用于存储快速读写的数据，用于存储分析结果，存储格式为json
 
 ## About Us
 
 以下是开发者的GitHub账户，欢迎follow
 
* [wanghan0501](https://github.com/wanghan0501)
* [Maicius](https://github.com/Maicius)
* [Times125](https://github.com/Times125)
 
 如果有关项目的问题可以联系以下邮箱
 
[ hanwang.0501@gmail.com](hanwang.0501@gmail.com)
