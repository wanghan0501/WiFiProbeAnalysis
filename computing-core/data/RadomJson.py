# coding=utf-8
import random
from time import ctime, sleep
import json
import time
import os


def random_mac():
    macList = []
    for i in range(1, 7):
        randStr = "".join(random.sample("0123456789abcdef", 2))
        macList.append(randStr)
    randMac = ":".join(macList)
    return randMac


def random_rssi():
    return str(random.randrange(-100, 0))


def random_range():
    return str(round(random.uniform(0, 100), 1))


def random_id():
    return str(random.randrange(1, 1000))


probeList = []


def random_json(item):
    probe = {"id": '' + random_id(), "mmac": random_mac(), "rate": "3", "wssid": "test", "wmac": random_mac(),
             "time": time.strftime('%a %b %e %H:%M:%S %Y', time.localtime(time.time()))}
    mac_DataMul = []
    for i in range(random.randrange(10, 30)):
        mac_DataMul.append({"mac": random_mac(), "rssi": random_rssi(), "range": random_range()})
    probe['data'] = mac_DataMul

    probe = json.dumps(probe)

    fileName = '/Users/mac/Workspace/Java/WIFIProbeAnalysis_Backstage/wifiProbe/source/A' + str(item)
    file = open(fileName, 'w')
    file.write(probe)
    file.close()
    os.system('hdfs dfs -put ' + fileName + ' /source')
    os.system('rm ' + fileName)
    sleep(1)


if __name__ == '__main__':
    threads = []
    probeList = []
    index = 0
    for i in range(2):
        random_json(i)

    print "all over %s" % ctime()
