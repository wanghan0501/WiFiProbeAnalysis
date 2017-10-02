package com.cs.scu.hbase.dataObject;

import com.cs.scu.hbase.HbaseColumn;
import com.cs.scu.hbase.HbaseTable;

@HbaseTable(name = "GroupData", rowKey = "time_id")
public class GroupData implements java.io.Serializable{
    public String time_id;

    @HbaseColumn(family = "probeInfo", qualifier = "probe_id")
    public String probe_id;

    @HbaseColumn(family = "probeInfo", qualifier = "mmac")
    public String mmac;

    @HbaseColumn(family = "probeInfo", qualifier = "rate")
    public String rate;

    @HbaseColumn(family = "probeInfo", qualifier = "wssid")
    public String wssid;
    @HbaseColumn(family = "probeInfo", qualifier = "wmac")
    public String wmac;
    @HbaseColumn(family = "probeInfo", qualifier = "record_time")
    public String record_time;

    //下面三个字段如果没有需要补全
    @HbaseColumn(family = "Address", qualifier = "lat")
    public String lat;
    @HbaseColumn(family = "Address", qualifier = "lon")
    public String lon;
    @HbaseColumn(family = "Address", qualifier = "addr")
    public String addr;

    @HbaseColumn(family = "data", qualifier = "dataList")
    public String dataList;

    public GroupData(){

    }

    public String getMmac() {
        return mmac;
    }

    public void setMmac(String mmac) {
        this.mmac = mmac;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getWssid() {
        return wssid;
    }

    public void setWssid(String wssid) {
        this.wssid = wssid;
    }

    public String getWmac() {
        return wmac;
    }

    public void setWmac(String wmac) {
        this.wmac = wmac;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }

    public String getProbe_id() {
        return probe_id;
    }

    public void setProbe_id(String probe_id) {
        this.probe_id = probe_id;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }
}
