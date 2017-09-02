package edu.cs.scu.javautils;

import com.alibaba.fastjson.JSON;
import edu.cs.scu.bean.macDataBean;

import java.util.List;

public class ParseJson {
    public static List<macDataBean> jsonToList(String json){
        return JSON.parseArray(json, macDataBean.class);
    }

    public static macDataBean jsonToObject(String json){
        return JSON.parseObject(json, macDataBean.class);
    }
}
