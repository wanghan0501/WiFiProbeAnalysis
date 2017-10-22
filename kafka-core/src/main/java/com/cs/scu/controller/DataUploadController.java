package com.cs.scu.controller;

import com.alibaba.fastjson.JSON;
import com.cs.scu.entity.Data;
import com.cs.scu.entity.DataGroup;
import com.cs.scu.kafka.consumer.KafkaConsumerForHive;
import com.cs.scu.kafka.consumer.KafkaConsumers;
import com.cs.scu.kafka.producer.KafkaProducerForHive;
import com.cs.scu.kafka.producer.KafkaProducers;
import com.cs.scu.service.DataUploadService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lch on 2017/5/3.
 */
@Controller
public class DataUploadController {
    @Autowired
    private DataUploadService dataUploadService;

    @Resource(name = "kafkaProducers")
    KafkaProducers producers;
    @Resource(name = "kafkaConsumers")
    KafkaConsumers consumers;


    @Resource(name = "kafkaProducerForHive")
    KafkaProducerForHive producerForHive;
    @Resource(name = "kafkaConsumerForHive")
    KafkaConsumerForHive consumerForHive;


    /**
     * @Name upload 探针上传数据接口
     * @param  json 接受的数据类型
     * @describe 接受探针上传的数据，然后将数据进行补全便于后期分析，
     * 同时补全后的数据分发的kafka中
     *
     * */
    @RequestMapping(value = "/upload", method = RequestMethod.POST,produces ="application/json;charset=utf-8")
    @ResponseBody
    public Object upload(@RequestBody String json) {
        System.out.println("-----------------<>");
        List<Data> dataList = new ArrayList<Data>();
        DataGroup group;
        String ujson = "";
        String res = "";

        try {
            ujson = new String(json.getBytes("ISO-8859-1"), "utf-8");
            res = URLDecoder.decode(ujson, "UTF-8");

            try{
                group = JSON.parseObject(res,DataGroup.class);
                dataList = group.getData();

                if (group.getAddr() == null) {
                    group.setLat("30.55836");
                    group.setLon("104.00285");
                    group.setAddr("四川省成都市双流区川大路2段四川大学江安校区计算机学院");
                }

                //System.err.println("resdata ---> " + JSON.toJSONString(group));

                for (Data data1:dataList){

                    if (data1.getRouter() == null) {
                        data1.setRouter("");
                    }
                    if (data1.getTc() == null) {
                        data1.setTc("");
                    }
                    if (data1.getDs() == null) {
                        data1.setDs("");
                    }
                    if (data1.getTs() == null) {
                        data1.setTc("");
                    }
                    if (data1.getTmc() == null) {
                        data1.setTmc("");
                    }
                }
                dataUploadService.saveObject(group);
                String resJson = JSON.toJSONString(group);

                System.err.println("resJson ---> " + StringEscapeUtils.escapeJava(resJson));

                producers.sendMessage(resJson);//发送数据到kafka topic : test,进行数据分析
                //producerForHive.sendMessage(resJson);//发送数据到topic : hiveData,存放到hive中

            }catch (Exception e){
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
    public ModelAndView sendMessage() {
        System.out.println("--------sendmessage--------");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(date);

        ModelAndView mv = new ModelAndView();
        mv.addObject("time", now);
        mv.setViewName("kafka_send");
        return mv;
    }


    @RequestMapping(value = "/receive",method = RequestMethod.GET)
    public ModelAndView receive() throws  Exception {
        System.err.println("--------------> receive <----------------");
        List<String> msg = new ArrayList<String>();
        msg = consumers.receive();
        String msg1 = consumerForHive.receive();
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg",msg);
        mv.setViewName("kafka_receive");
        return  mv;
    }

}
