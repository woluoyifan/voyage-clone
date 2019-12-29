### 项目简介
该项目是基于`cgi`页游年代的游戏&nbsp;&nbsp;`网络大航海时代`&nbsp;&nbsp;重构而来<br/><br/>
网上流传原版源码使用`perl`写成,本项目使用`Java`进行了重构<br/><br/>
`cgi`游戏作为最早的页游,其界面朴素,玩法逻辑简单,但在那个拨号上网都难得而昂贵的时代,是一代人的历史回忆<br/><br/>

### 项目状态
完成度`90%`,基本还原了原作以下各模块
- 船只
- 商品交易
- 战斗
- 城镇
- 冒险和发现物

当然,也包括原作的bug(笑)<br/><br/>

待完成的部分
- 网上流传源代码的冒险为日文,需转换后添加至项目中
- 后台管理优化
- 原作的bug修复

### 目录结构
项目为标准的Java web mvc结构

### 项目依赖
- spring boot
- mysql
- bootstrap
- jquery

### 使用说明
使用`mysql`5.7版本,新建数据库`voyage`<br/><br/>
连接用户和密码设置在application.yml中<br/><br/>
使用voyage.sql初始化表和数据<br/><br/>
maven打包构建后使用spring boot典型方式启动<br/><br/>
启动类为`com.luoyifan.voyage.Application`<br/><br/>
相关配置可在`resources/application.yml`中的`voyage`项查看<br/><br/>
在浏览器中打开http://localhost:18888访问(默认)<br/>

### 一些想法
最初只是打算重构后给自己构建一个世界来回忆这个游戏<br/><br/>
所以只是用了最简单便捷的jpa+mysql进行开发,作为游戏服务来说性能其实是非常低下的,当然,后台模板引擎渲染也是一个问题<br/><br/>
如果有空时,可能会考虑进行内存化\前后端分离化的改造<br/><br/>
最后,就像是魔兽也推出了怀旧服一样,希望大家在怀旧中能找回当年的那一份乐趣
