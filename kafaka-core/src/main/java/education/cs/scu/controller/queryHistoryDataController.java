package education.cs.scu.controller;

import education.cs.scu.entity.HistoryData;
import education.cs.scu.entity.entityData.Day;
import education.cs.scu.entity.entityData.Hour;
import education.cs.scu.entity.entityData.Month;
import education.cs.scu.entity.entityData.Year;
import education.cs.scu.service.QueryHistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by maicius on 2017/6/28.
 */
@CrossOrigin
@RestController
public class queryHistoryDataController {
    @Autowired
    private QueryHistoryDataService queryHistoryDataService;

    /**
     * 添加历史模拟数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addActivityData", method = RequestMethod.GET)
    public void addActivityData(HttpServletRequest request) throws Exception {
//        HistoryData historyData = new HistoryData();
//        historyData.setYear(activityMonth);
//        historyData.setUserName(userName);
        queryHistoryDataService.addActivityData();
    }

    /**
     * 根据月度查询活跃数据
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryActivityMonth", method = RequestMethod.GET)
    public List<Month> queryActivityMonth(HttpServletRequest request) throws Exception {
//        HistoryData historyData = new HistoryData();
//        historyData.setYear(activityMonth);
//        historyData.setUserName(userName);
        return queryHistoryDataService.queryActivityMonth();
    }

    /**
     * 根据年度查询活跃信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryActivityYear", method = RequestMethod.GET)
    public List<Year> queryActivityYear(HttpServletRequest request) throws Exception {
//        HistoryData historyData = new HistoryData();
//        historyData.setYear(activityYear);
//        historyData.setUserName(userName);
        //queryHistoryDataService.addActivityData();
        return queryHistoryDataService.queryActivityYear();
    }

    /**
     * 根据日期查询一天中每个小时的活跃信息
     *
     * @param request
     * @param activityDay
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryActivityDay", method = RequestMethod.GET)
    public List<Hour> queryActivityDay(HttpServletRequest request,
                                       @RequestParam("activityDay") String activityDay) throws Exception {
//        HistoryData historyData = new HistoryData();
//        historyData.setUserName(userName);
//        historyData.setDay(activityDay);
        //queryHistoryDataService.addActivityData();
        return queryHistoryDataService.queryActivityDay(activityDay);
    }

}
