package com.cs.scu.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cs.scu.entity.DataGroup;
import com.cs.scu.hbase.dao.GroupDataDao;
import com.cs.scu.hbase.dataObject.GroupData;
import com.cs.scu.service.DataUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lch on 2017/5/3.
 */
@Service
public class DataUploadServiceImpl implements DataUploadService {

    @Autowired
    GroupDataDao groupDataDao;

    public String PrintJson() throws Exception {

        return "";
    }

    public void saveObject(DataGroup dataGroup) throws Exception {
        GroupData groupData = new GroupData();
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String time_id = new SimpleDateFormat("yyyyMMDDHHmmssSSS").format(date) + "-" + dataGroup.getId();
        System.out.println(time_id);
        groupData.setTime_id(time_id);
        groupData.setRecord_time(dataGroup.getTime());
        groupData.setAddr(dataGroup.getAddr());
        JSONArray ja = JSONArray.parseArray(JSON.toJSONString(dataGroup.getData()));
        groupData.setDataList(ja.toJSONString());
        groupData.setProbe_id(dataGroup.getId());
        groupData.setLat(dataGroup.getLat());
        groupData.setLon(dataGroup.getLon());
        groupData.setMmac(dataGroup.getMmac());
        groupData.setWmac(dataGroup.getWmac());
        groupData.setWssid(dataGroup.getWssid());
        groupData.setRate(dataGroup.getRate());
        try {
            groupDataDao.save(groupData);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Insert Into Hbase Success");
    }

}
