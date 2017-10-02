package education.cs.scu.service;

/**
 * Created by maicius on 2017/6/27.
 */

import education.cs.scu.entity.ProbeInfo;
import education.cs.scu.entity.ShopInfo;
import education.cs.scu.entity.User;

import java.util.List;

public interface ShopService {

    List<ShopInfo> queryShopInfos(ShopInfo shopInfo) throws Exception;
    int addShopInfo(ShopInfo shopInfo) throws Exception;
    int updateShopInfo(ShopInfo shopInfo) throws Exception;
    long getUniqueShopId() throws  Exception;
    List<ProbeInfo> queryProbeInfos(ProbeInfo probeInfo) throws Exception;
    List<ProbeInfo> queryshopProbeInfos(ShopInfo shopInfo) throws Exception;
    int addProbeInfo(ProbeInfo probeInfo);
    List<ShopInfo> queryShopNameById(ShopInfo shopInfo);
}
