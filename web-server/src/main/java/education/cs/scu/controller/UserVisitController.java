package education.cs.scu.controller;

import education.cs.scu.component.QueryUsersShopInfo;
import education.cs.scu.entity.UserBean;
import education.cs.scu.service.ShopService;
import education.cs.scu.service.UserVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lch
 * @Create on 2017/09/02 20:40
 **/
@RestController
public class UserVisitController {
    @Autowired
    private UserVisitService userVisitService;

    @Autowired
    private ShopService shopService;

    @Autowired
    QueryUsersShopInfo queryUsersShopInfo;

    /**
     * 根据userName 查询其名下的商店
     * <p>
     * 查询redis中的table_user表
     */

    @RequestMapping(value = "queryUserShop", method = RequestMethod.GET)
    public List<UserBean> queryUserShop(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam("userName") String userName) throws Exception {
        List<Integer> shopIdlist = queryUsersShopInfo.getShopId(userName);
        List<String> shopNameList = new ArrayList<String>();//名字列表
        if (shopIdlist.size() > 0) {

            for (int i = 0; i < shopIdlist.size(); i++) {
                shopNameList.add(queryUsersShopInfo.getShopName(shopIdlist.get(i)));
            }
            List<UserBean> res = userVisitService.queryUserShop(shopIdlist);
            if (res == null) {
                return null;
            }
            for (int k = 0; k < res.size(); k++) {
                for (int i = 0; i < shopIdlist.size(); i++) {
                    if (res.get(k).getShopId() == shopIdlist.get(i)) {
                        res.get(k).setShopName(shopNameList.get(i));
                        System.out.println(res.get(k).getShopName());
                    }
                }
            }
            return res;
        } else {
            return null;
        }
    }


    /**
     * 查询redis中的table_user_visit表
     * 通过webSocket 推送到 前端
     */

//    @RequestMapping(value = "queryUserVisit", method = RequestMethod.GET)
//    public UserVisitBean queryUserVisit(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        @RequestParam("userName") String userName) throws Exception {
//
//
//        UserVisitBean userVisitBean = new UserVisitBean();
//        userVisitBean.setTotalFlow(0);
//        userVisitBean.setTime(0l);
//        userVisitBean.setShopId(0);
//        userVisitBean.setShallowVisitRate(0d);
//        userVisitBean.setDeepVisitRate(0d);
//        userVisitBean.setCheckInRate(0d);
//        userVisitBean.setCheckInFlow(0);
//        userVisitBean.setMmac("0");
//
//        List<Integer> shopIdlist = queryUsersShopInfo.getShopId(userName);
//        if (shopIdlist == null) {
//            return null;
//        }
//        if (shopIdlist.size() > 0) {
//            List<UserVisitBean> res = new ArrayList<UserVisitBean>();
//            res = userVisitService.queryUserVisit(shopIdlist);
//            if (res == null) {
//                return userVisitBean;
//            }
//            return res.get(0);
//        } else {
//
//            return userVisitBean;
//        }
//
//    }
//
//}
}
