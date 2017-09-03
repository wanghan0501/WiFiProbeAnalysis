package edu.cs.scu.javautils;

import com.alibaba.fastjson.JSON;
import edu.cs.scu.bean.MacDataBean;

import java.util.List;

public class ParseJson {
    public static List<MacDataBean> jsonToList(String json){
        return JSON.parseArray(json, MacDataBean.class);
    }

    public static MacDataBean jsonToObject(String json){
        return JSON.parseObject(json, MacDataBean.class);
    }
}
