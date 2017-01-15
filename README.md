# SmartStockServer
智能选股服务端
#概述
这个服务端是利用socket通讯的,至少我认为socket通讯是比http通讯是要安全的,另外这个项目最终是可以打成jar包直接运行的,这是我比较喜欢的一种方式.
#技术
socket:mina
缓存:redis
数据库:spring+jdbcTemplate
#通讯类
https://github.com/momo145/TransportModule
#客户端
https://github.com/momo145/SmartStock
#IDE
ide我是使用 IntelliJ IDEA