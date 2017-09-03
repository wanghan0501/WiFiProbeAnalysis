package edu.cs.scu.common.constants;

/**
 * Created by Wang Han on 2017/8/25 11:15.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. All Rights Reserved.
 *
 * @author Wang Han
 */
public interface AnalysisConstants {
    String FIELD_DATA = "data";
    String FIELD_ID = "id";
    String FIELD_MMAC = "mmac";
    String FIELD_RATE = "rate";
    String FIELD_TIME = "time";
    String FIELD_WMAC = "wmac";
    String FIELD_WSSID = "wssid";
    String FIELD_MAC = "mac";
    String FIELD_RANGE = "range";
    String FIELD_RSSI = "rssi";
    String FIELD_DS = "ds";
    String FIELD_ROUTER = "router";
    String FIELD_TC = "tc";
    String FIELD_TMAC = "tmac";
    String FIELD_ARRD = "addr";
    String FIELD_LAT = "lat";
    String FIELD_LON = "lon";

    long DEFAULT_FIRST_VISIT_TIME = 0L;
    Integer THREADS_NUM = 2;

    //HBase 常量
    byte[] ADDRESS_CF = "Address".getBytes();
    byte[] PROBEINFO_CF = "probeInfo".getBytes();
    byte[] DATA_CF = "data".getBytes();

    byte[] lon = "lon".getBytes();
    byte[]  lat = "lat".getBytes();
    byte[]  mmac = "mmac".getBytes();
    byte[]  probe_id = "probe_id".getBytes();
    byte[]  rate = "rate".getBytes();
    byte[]  wmac = "wmac".getBytes();
    byte[]  dataList = "dataList".getBytes();
    byte[]  wssid = "wssid".getBytes();
    byte[] record_time = "record_time".getBytes();
    byte[]  addr = "addr".getBytes();
}
