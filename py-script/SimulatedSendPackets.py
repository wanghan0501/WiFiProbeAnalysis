#coding=utf-8
import random
import threading
from time import ctime,sleep
import json
import requests
import time
def random_mac():
    macList = []
    for i in range(1, 7):
        randStr = "".join(random.sample("0123456789abcdef",2))
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

# 模拟探针发包
def send_random_json():
    headers = {'Content-Type': 'application/json'}
    probe = {"id": ''+random_id(), "mmac": random_mac(), "rate": "3", "wssid": "test", "wmac": random_mac(), "time": time.strftime('%a %b %e %H:%M:%S %Y', time.localtime(time.time()))}
    mac_DataMul = []
    for i in range(random.randrange(1, 5)):
        mac_DataMul.append({"mac": random_mac(), "rssi": random_rssi(), "range": random_range()})
    probe['data'] = mac_DataMul
    probe = json.dumps(probe)
    print(probe)
    request = requests.post(url='http://localhost:8000/upload.action', headers=headers, data=probe)
    print("response code:", request.status_code)

# 模拟多线程发包
def send_rand_json_with_multi_thread():
    for i in range(10):
        probe = {"id": i, "mmac": random_mac(), "rate": 3, "wssid": "test", "wmac": random_mac()}
        probes = json.dumps(probe)
        probeList.append(probes)

    for i in range(10):
        t = threading.Thread(target=send_random_json)
        threads.append(t)

    for i in range(10):
        threads[i].setDaemon(True)
        threads[i].start()

if __name__ == '__main__':
    threads = []
    probeList = []
    index=0
    for i in range(1000):
        send_random_json()
        sleep(1)
    print("all over %s" %ctime())