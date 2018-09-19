# 一些可能到错误（针对mac）：

### 1.pom.xml里某些包出现无法找到对应版本

解决方法：update maven

### 2.maven成功引入所有的包，但报 class not found之类但错误 

原因：包损坏（可能是下载过程中突然断网引起的）
解决方法：进入 .m2 目录删除repository，重新下载所有的包

### 3.maven更新和下载速度过慢

解决方法：将中心仓库的镜像替换为阿里云镜像,打开 .m2 目录下setting.xml，
将<mirror></mirror>替换为下面内容：

    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
