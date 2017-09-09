package education.cs.scu.controller;

import education.cs.scu.entity.PropertyBean;
import education.cs.scu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by maicius on 2017/6/28.
 */
@RestController
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    /**
     * 更新属性
     *
     * @param request
     * @param shop_id
     * @param mmac
     * @param visitCycle
     * @param visitRange
     * @param visitRssi
     * @param activityDegree
     * @param visitTimeSplit
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setProperty", method = RequestMethod.GET)
    public String setProperty(HttpServletRequest request,
                              @RequestParam("property_id") Integer property_id,
                              @RequestParam("shop_id") Integer shop_id,
                              @RequestParam("mmac") String mmac,
                              @RequestParam("visitCycle") String visitCycle,
                              @RequestParam("visitRange") Double visitRange,
                              @RequestParam("visitRssi") Integer visitRssi,
                              @RequestParam("activityDegree") String activityDegree,
                              @RequestParam("visitTimeSplit") String visitTimeSplit) throws Exception {

        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setPropertyId(property_id);
        propertyBean.setShopId(shop_id);
        propertyBean.setMmac(mmac);
        propertyBean.setVisitCycle(visitCycle);
        propertyBean.setVisitRange(visitRange);
        propertyBean.setVisitRSSI(visitRssi);
        propertyBean.setActivityDegree(activityDegree);
        propertyBean.setVisitTimeSplit(visitTimeSplit);
        propertyBean.setPropertyType(false);

        int res = propertyService.setProperty(propertyBean);
        if (res > 0) {
            return "{\"success\":1}";
        } else {
            return "{}";
        }
    }

    /**
     * 新增属性
     *
     * @param request
     * @param shop_id
     * @param mmac
     * @param visitCycle
     * @param visitRange
     * @param visitRssi
     * @param activityDegree
     * @param visitTimeSplit
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addProperty", method = RequestMethod.GET)
    public String addProperty(HttpServletRequest request,
                              @RequestParam("shop_id") Integer shop_id,
                              @RequestParam("mmac") String mmac,
                              @RequestParam("visitCycle") String visitCycle,
                              @RequestParam("visitRange") Double visitRange,
                              @RequestParam("visitRssi") Integer visitRssi,
                              @RequestParam("activityDegree") String activityDegree,
                              @RequestParam("visitTimeSplit") String visitTimeSplit) throws Exception {


        //localhost:8080/addProperty.action?shop_id=1504193496555&mmac=ff.ff.ff.ff.ff.ff&visitCycle=test&visitRange=2&visitRssi=1&activityDegree=test&visitTimeSplit=test
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setShopId(shop_id);
        propertyBean.setMmac(mmac);
        propertyBean.setVisitCycle(visitCycle);
        propertyBean.setVisitRange(visitRange);
        propertyBean.setVisitRSSI(visitRssi);
        propertyBean.setActivityDegree(activityDegree);
        propertyBean.setVisitTimeSplit(visitTimeSplit);
        propertyBean.setPropertyType(false);

        int res = propertyService.addProperty(propertyBean);
        if (res > 0) {
            return "{\"success\":1}";
        } else {
            return "{\"failed\":0}";
        }
    }

    /**
     * 查询属性
     *
     * @param request
     * @param shop_id
     * @param mmac
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryProperty", method = RequestMethod.GET)
    public PropertyBean queryProperty(HttpServletRequest request,
                                      @RequestParam("shop_id") Integer shop_id,
                                      @RequestParam("mmac") String mmac) throws Exception {
        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setShopId(shop_id);
        propertyBean.setMmac(mmac);
        PropertyBean propertyBean1 = propertyService.queryProperty(propertyBean);
        if (propertyBean1 == null){
            propertyBean1 = new PropertyBean();
            propertyBean1.setMmac("");
            return propertyBean1;
        }
        return propertyService.queryProperty(propertyBean);
    }
}
