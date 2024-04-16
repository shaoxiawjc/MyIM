# 简易IM聊天系统
## 项目简介
项目GitHub地址：https://github.com/shaoxiawjc/MyIM.git

本项目是基于Java语言开发的一个简单的IM即时聊天项目的后端部分，采用Http和WebSokcet协议
具体技术栈如下：
- Spring SpringBoot 开发框架
- Netty实现WebSocket
- MyBatis/MyBatis-plus持久层框架
- MySQL关系型数据库
- Redis非关系型数据库
- Redisson 实现分布式锁

项目实现
数据库表设计
数据库表大致可分为俩个模块 消息聊天模块 和 用户模块
具体的库表对应可以访问https://drawsql.app/teams/-1124/diagrams/myim
DDL语句见项目docs文件夹


## 项目架构图
```
├─docs
│  └─sql
└─server
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─shaoxia
│  │  │          └─server
│  │  │              ├─common                           
│  │  │              │  ├─constant            -- 常量包
│  │  │              │  ├─exception           -- 异常包
│  │  │              │  └─utils               -- 工具包
│  │  │              ├─config                 -- 配置包
│  │  │              ├─job                    -- 任务包
│  │  │              ├─user                   -- 用户模块
│  │  │              │  ├─controller          -- 控制层
│  │  │              │  ├─dao                 -- 对mapper进一步封装的持久化层
│  │  │              │  ├─mapper              -- 数据库操作层
│  │  │              │  ├─model               -- 实体类层
│  │  │              │  │  ├─domain           -- 数据库表实体类
│  │  │              │  │  └─vo               -- 前后端交互实体类
│  │  │              │  │      ├─apply
│  │  │              │  │      ├─friend
│  │  │              │  │      ├─group
│  │  │              │  │      ├─message
│  │  │              │  │      └─user
│  │  │              │  └─service             -- 用户业务层
│  │  │              │      └─impl
│  │  │              └─websocket              -- websocket消息发送层
│  │  │                  ├─dao                -- 消息持久化层
│  │  │                  ├─handler            -- 服务器处理层
│  │  │                  ├─mapper             -- 数据库持久化层
│  │  │                  ├─model              -- 实体类层
│  │  │                  │  └─domain
│  │  │                  ├─service            -- 业务层
│  │  │                  │  └─impl
│  │  │                  └─vo                 -- 前后端数据交互层
│  │  │                      ├─request
│  │  │                      └─response
│  │  └─resources
│  │      └─mapper
```




