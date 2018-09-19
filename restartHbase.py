#coding=utf-8
import os
from time import sleep

# ls /hbase/table
# rmr
# create "LogData", "prop"
# create "LogAna", "IP", "BYTES", "REQ", "METHOD_STATE", "URL"
os.system("cd /usr/local/Cellar/hbase/1.2.6/bin")
os.system("stop-hbase.sh")
os.system("cd /usr/local/Cellar/zookeeper/3.4.10/bin/")
os.system("zkServer stop")
print "关闭成功"
sleep(5)
os.system("zkServer start")
os.system("cd /usr/local/Cellar/hbase/1.2.6/bin")
os.system("start-hbase.sh")
sleep(5)
os.system("jps")
print "重启完成"
