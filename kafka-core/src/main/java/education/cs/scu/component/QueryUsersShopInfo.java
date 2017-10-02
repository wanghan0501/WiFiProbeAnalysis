package education.cs.scu.component;

import education.cs.scu.entity.ShopInfo;
import education.cs.scu.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类，用来获取给定userName下的shop_id
 *
 * @Author lch
 * @Create on 2017/08/25 00:56
 **/
@Component("queryUsersShopInfo")
public class QueryUsersShopInfo {

    @Autowired
    private ShopService shopService;

    /**
     *
     * 根据指定userName 查询 其名下的shopID
     *
     *  */
    public List<Integer> getShopId(String userName) throws Exception {

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_owner(userName);

        List<Integer> shopIdlist = new ArrayList<Integer>();
        List<ShopInfo> shopInfoList = shopService.queryShopInfos(shopInfo);
        for (ShopInfo si : shopInfoList) {
            shopIdlist.add(si.getShop_id());
            System.out.println("shop_id = " + si.getShop_id());
        }
        return shopIdlist;
    }

    public String getShopName(Integer shopId) throws Exception {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShop_id(shopId);
        String shopName = "";
        List<ShopInfo> shopInfoList = shopService.queryShopNameById(shopInfo);
        for (ShopInfo si : shopInfoList) {
           shopName = si.getShop_name();
        }
        return shopName;

    }

}
