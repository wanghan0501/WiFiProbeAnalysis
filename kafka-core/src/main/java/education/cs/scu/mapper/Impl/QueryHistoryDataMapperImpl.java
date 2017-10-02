package education.cs.scu.mapper.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import education.cs.scu.entity.HistoryData;
import education.cs.scu.entity.entityData.Day;
import education.cs.scu.entity.entityData.Hour;
import education.cs.scu.entity.entityData.Month;
import education.cs.scu.entity.entityData.Year;
import education.cs.scu.mapper.QueryHistoryDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QueryHistoryDataMapperImpl
 *
 * @Author lch
 * @Create on 2017/08/25 14:57
 **/
public class QueryHistoryDataMapperImpl implements QueryHistoryDataMapper {

    @Autowired
    private RedisTemplate<String, HistoryData> redisTemplate;

    @Autowired
    private RedisTemplate<String, Month> redisTemplateMonth;

    @Autowired
    private RedisTemplate<String, Year> redisTemplateYear;

    @Autowired
    private RedisTemplate<String, Day> redisTemplateDay;


    // 一年365天的数据
    //12月数据
    //一天12个小时
    public int addActivityData() throws Exception {

        List<Month> monthList = new ArrayList<Month>();
        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        //随机生成month数据
        for (int k = 0; k < 12; k++) {
            Month month = new Month();
            month.setMonth(k + 1);
            int temp1 = ((new Random()).nextInt(10) + k + 1) * 825;
            month.setNumber(temp1);
            month.setCheckInnum((int) (temp1 * Math.random()));
            monthList.add(month);
            if (k < 9)
                redisTemplateMonth.opsForHash().put("MONTH", "2017-0" + String.valueOf(k + 1)
                        , JSON.toJSONString(month));
            else
                redisTemplateMonth.opsForHash().put("MONTH", "2017-" + String.valueOf(k + 1)
                        , JSON.toJSONString(month));

        }
        Year year = new Year();
        year.setYear(2017);
        int temp = 0;
        for (int i = 0; i < monthList.size(); i++) {
            temp += monthList.get(i).getNumber();
        }
        year.setNumber(temp);
        year.setCheckInnum((int) (temp * Math.random()));
        redisTemplateYear.opsForHash().put("YEAR", "2017", JSON.toJSONString(year));

        //System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqq");

        //随机生成day数据
        String initDay = "2016-01-01";
        Long initDayStamp = dateToStamp(initDay);
        Long dayStamp = initDayStamp;
        Map<String, String> map = new HashMap<String, String>();
        int num = temp / (365 * 12);
        for (int i = 0; i < 366; i++) {
//                Day day = new Day();
//            day.setDay(dayStamp);
            List<Hour> hourList = new ArrayList<Hour>();
            for (int j = 0; j < 24; j++) {
                Hour hour = new Hour();
                hour.setHour(j + 1);
                if (j >= 8 && j <= 20) {
                    int temp1 = (int) (num * (Math.random() + 1.0));
                    int temp2 = (int) (temp1 * (Math.random()));
                    hour.setNumber((temp1));
                    hour.setCheckInnum((temp2));
                } else {
                    int temp1 = (int) (num * (Math.random()));
                    int temp2 = (int) (temp1 * (Math.random()));
                    hour.setNumber(temp1);
                    hour.setCheckInnum(temp2);
                }
                hourList.add(hour);
            }
            map.put(stampToDate(dayStamp), JSON.toJSONString(hourList));

//            day.setHour(hourList);
            //redisTemplateDay.opsForHash().put("DAY", stampToDate(dayStamp), JSON.toJSONString(hourList));
            dayStamp += 24 * 3600 * 1000;
        }
        redisTemplateDay.opsForHash().putAll("DAY", map);
        endTime = System.currentTimeMillis();
        System.out.println("生成模拟数据一共耗时 = " + (endTime-startTime)/1000 + "秒");
        return 1;

    }

    public List<Year> queryActivityYear() throws Exception {
        List<Year> yearList = new ArrayList<Year>();
        String tmp = (String) redisTemplateYear.opsForHash().get("YEAR", "2017");
        yearList.add(JSON.parseObject(tmp, Year.class));

        return yearList;
    }

    public List<Month> queryActivityMonth() throws Exception {
        List<Month> monthList = new ArrayList<Month>();
        Map<Object, Object> map = redisTemplateMonth.opsForHash().entries("MONTH");
        Set set = map.keySet();
        for (Object key : map.keySet()) {
            //System.out.println("--------" + key.toString());

            monthList.add(JSON.parseObject((String) map.get(key.toString()), Month.class));
        }
        return monthList;
    }

    public List<Hour> queryActivityDay(String date) throws Exception {

        List<Day> dayList = new ArrayList<Day>();
        return JSONArray.parseArray((String) redisTemplateDay.opsForHash().get("DAY", date), Hour.class);


        // dayList;
    }

    /**
     * 将时间转换为时间戳
     */
    public static Long dateToStamp(String s) throws ParseException {
        //String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        //res = String.valueOf(ts);
        return ts;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 历史数据存入的时候，分别以historyData.getYear()，historyData.getMongth()，historyData.getDay()
     * 作为KEY
     */


}
