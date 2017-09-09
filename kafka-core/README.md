# spring_mvc_mybatis_maven-
基于maven的java web项目框架模板，使用spring_mvc + mybatis框架
可用于在IDEA上快速构建web项目

更多 spring_mvc教程demo:
https://github.com/Maicius/Spring_course

#测试方法：
将test.sql中sql语句复制到mysql中，
建立数据库，并修改db.properties里数据库相关属性（账户名和密码）
build -> run,正常显示网页
输入数据库中到账户和密码，
登陆跳转后显示用户名
测试完成

#一些可能到错误（针对mac）：
###1.pom.xml里某些包出现无法找到对应版本
解决方法：update maven
###2.maven成功引入所有的包，但报 class not found之类但错误 
原因：包损坏（可能是下载过程中突然断网引起的）
解决方法：进入 .m2 目录删除repository，重新下载所有的包
###3.maven更新和下载速度过慢
解决方法：将中心仓库的镜像替换为阿里云镜像,打开 .m2 目录下setting.xml，
将<mirror></mirror>替换为下面内容：

    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
