package education.cs.scu.controller;

import education.cs.scu.entity.ProbeInfo;
import education.cs.scu.entity.ShopInfo;
import education.cs.scu.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maicius on 2017/6/27.
 */
@RestController
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 查询商场信息
     *
     * @param request
     * @param userName
     * @return 商场列表
     * @throws Exception
     */
    @RequestMapping(value = "/queryShopInfos", method = RequestMethod.GET)
    public List<ShopInfo> queryShopInfos(HttpServletRequest request,
                                         @RequestParam("userName") String userName) throws Exception {

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_owner(userName);
        List<ShopInfo> resShopInfos = shopService.queryShopInfos(shopInfo);

        if (resShopInfos.size() > 0) {
            System.out.println("查询成功");
        }
        return resShopInfos;
    }

    /**
     * 增加商场信息
     *
     * @param request
     * @param userName
     * @param shopName
     * @param shopAddr
     * @param shopManager
     * @param shopTelephone
     * @param shopDescribe
     * @return 成功返回"success",失败返回failed
     * @throws Exception
     */
    @RequestMapping(value = "addShopInfo", method = RequestMethod.GET)
    public String addShoInfo(HttpServletRequest request,
                             @RequestParam("userName") String userName,
                             @RequestParam("shopName") String shopName,
                             @RequestParam("shopAddr") String shopAddr,
                             @RequestParam("shopManager") String shopManager,
                             @RequestParam("shopTelephone") String shopTelephone,
                             @RequestParam("shopDescribe") String shopDescribe) throws Exception {

        //localhost:8080/addShopInfo.action?userName=110&shopName=110110&shopAddr=test&shopManager&shopTelephone=110110&shopDescribe=test
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_name(shopName);
        shopInfo.setShop_addr(shopAddr);
        shopInfo.setShop_telephone(shopTelephone);
        shopInfo.setShop_manager(shopManager);
        shopInfo.setShop_describe(shopDescribe);
        shopInfo.setShop_owner(userName);
        int success = shopService.addShopInfo(shopInfo);
        System.out.println(success);
        if (success > 0) {
            return "{\"success\":1}";
        } else {
            return "{\"failed\":0}";
        }
    }


    /**
     * 更新商场信息
     *
     * @param request
     * @param userName
     * @param shopID
     * @param shopName
     * @param shopAddr
     * @param shopManager
     * @param shopTelephone
     * @param shopDescribe
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateShopInfo", method = RequestMethod.GET)
    public String updateShopInfo(HttpServletRequest request,
                                 @RequestParam("userName") String userName,
                                 @RequestParam("shopID") Integer shopID,
                                 @RequestParam("shopName") String shopName,
                                 @RequestParam("shopAddr") String shopAddr,
                                 @RequestParam("shopManager") String shopManager,
                                 @RequestParam("shopTelephone") String shopTelephone,
                                 @RequestParam("shopDescribe") String shopDescribe) throws Exception {
//http://localhost:8080/updateShopInfo.action?userName=110&shopID=1504193463328&shopName=110&shopAddr=testNew&shopManager=testNew&shopTelephone=330&shopDescribe=testnew
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_id(shopID);
        shopInfo.setShop_name(shopName);
        shopInfo.setShop_addr(shopAddr);
        shopInfo.setShop_telephone(shopTelephone);
        shopInfo.setShop_manager(shopManager);
        shopInfo.setShop_describe(shopDescribe);
        shopInfo.setShop_owner(userName);
        int success = shopService.updateShopInfo(shopInfo);
        System.err.println(success);
        if (success > 0) {
            return "{\"success\":1}";
        } else {
            return "{\"failed\":0}";
        }
    }


    /**
     * 增加探针信息
     *
     * @param request
     * @param mmac
     * @param shopName
     * @param lat
     * @param lon
     * @param addr
     * @param state
     * @param totalData
     * @return 成功返回"success",失败返回failed
     * @throws Exception
     */
    @RequestMapping(value = "addProbeInfo", method = RequestMethod.GET)
    public String addProbeInfo(HttpServletRequest request,
                               @RequestParam("userName") String userName,
                               @RequestParam("mmac") String mmac,
                               @RequestParam("shopName") String shopName,
                               @RequestParam("lat") Double lat,
                               @RequestParam("lon") Double lon,
                               @RequestParam("addr") String addr,
                               @RequestParam("state") String state,
                               @RequestParam("totalData") Integer totalData,
                               @RequestParam("rate") Double rate,
                               @RequestParam("shopId") Integer shopId) throws Exception {


        //localhost:8080/addProbeInfo.action?userName=110&mmac=ff.ff.ff.ff.ff.ff&shopName=220&lat=123.45678&lon=321.1234567&addr=test2&state=test2&totalData=10000&rate=2.22&&shopId=1504193463328
        ProbeInfo probeInfo = new ProbeInfo();
        probeInfo.setUser_name(userName);
        probeInfo.setMmac(mmac);
        probeInfo.setShop_name(shopName);
        probeInfo.setLat(lat);
        probeInfo.setLon(lon);
        probeInfo.setAddr(addr);
        probeInfo.setState(state);
        probeInfo.setRate(rate);
        probeInfo.setShop_id(shopId);
        probeInfo.setTotal_data(totalData);
        int success = shopService.addProbeInfo(probeInfo);
        if (success > 0){
            return "{\"success\":1}";
        }
            return "{\"failed\",0}";
    }


    /**
     * 根据用户名查询探针信息
     *
     * @param request
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryProbeInfos", method = RequestMethod.GET)
    public List<ProbeInfo> queryProbeInfos(HttpServletRequest request,
                                           @RequestParam("userName") String userName) throws Exception {
       ProbeInfo probeInfo = new ProbeInfo();
       probeInfo.setUser_name(userName);
       List<ProbeInfo> probeInfos =  shopService.queryProbeInfos(probeInfo);
       if(probeInfos != null && probeInfos.size() > 0){
           return probeInfos;
       }else{
           return new ArrayList<ProbeInfo>();
       }
    }

    /**
     * 根据商场ID查询探针
     *
     * @param request
     * @param shop_id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "queryShopProbeInfos", method = RequestMethod.GET)
    public List<ProbeInfo> queryShopProbeInfos(HttpServletRequest request,
                                               @RequestParam("shopId") Integer shop_id) throws Exception {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_id(shop_id);
        List<ProbeInfo> probeInfoList = shopService.queryshopProbeInfos(shopInfo);
        if (probeInfoList == null || probeInfoList.size()<=0) {
            return null;
        }
        return probeInfoList;
    }
}
