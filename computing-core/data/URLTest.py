# coding=utf-8
import json
import random
import urllib2

import time


def random_mac():
    prefix_list = ("f4:5c:89:", "c4:6a:b7:")
    prefix = random.choice(prefix_list)
    macList = []
    for i in range(1, 4):
        randStr = "".join(random.sample("0123456789abcdef", 2))
        macList.append(randStr)
    randMac = prefix + ":".join(macList)
    return randMac


def random_rssi():
    return str(random.randrange(-100, 0))


def random_range():
    return str(round(random.uniform(0, 100), 1))


def random_id():
    return str(random.randrange(1, 1000))


def random_addr():
    return "四川省成都市双流区川大路2段四川大学江安校区计算机学院"


def random_lat():
    return str(random.randrange(-180, 180, _int=float))


def random_lon():
    return str(random.randrange(-180, 180, _int=float))


probeList = []


def random_json():
    probe = {
        "addr": random_addr(),
        "lat": random_lat(),
        "lon": random_lon(),
        "id": '' + random_id(),
        "mmac": random_mac(),
        "rate": "3",
        "wssid": "test",
        "wmac": random_mac(),
        "time": time.strftime(
            '%a %b %e %H:%M:%S %Y', time.localtime(time.time()))}
    mac_DataMul = []
    for i in range(random.randrange(10, 30)):
        mac_DataMul.append({
            "mac": random_mac(),
            "rssi": random_rssi(),
            "range": random_range(),
            "ds": "True",
            "router": "router",
            "tc": "",
            "tmac": "00:00:00:00:00:00"
        })
    probe['data'] = mac_DataMul
    probe = json.dumps(probe)

    print probe

    headers = {'Content-Type': 'application/json'}
    request = urllib2.Request(url='http://120.24.238.195:8080/kafkaWeb/upload.action',
                              headers=headers, data=probe)
    response = urllib2.urlopen(request)


if __name__ == '__main__':
    while True:
        random_json()
        time.sleep(1)

    print "all over %s" % ctime()
