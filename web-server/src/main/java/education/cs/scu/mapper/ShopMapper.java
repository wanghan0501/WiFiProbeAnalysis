package education.cs.scu.mapper;

import education.cs.scu.entity.ProbeInfo;
import education.cs.scu.entity.ShopInfo;
import education.cs.scu.entity.User;

import java.util.List;

/**
 * Created by maicius on 2017/6/27.
 */
public interface ShopMapper {
    List<ShopInfo> queryShopInfos(ShopInfo shopInfo);
    int addShopInfo(ShopInfo shopInfo);
    int updateShopInfo(ShopInfo shopInfo);
    long getUniqueShopId();
    List<ProbeInfo> queryProbeInfos(ProbeInfo probeInfo);
    List<ProbeInfo> queryShopProbeInfo(ShopInfo shopInfo);
    int addProbeInfo(ProbeInfo probeInfo);
    List<ShopInfo> queryShopNameById(ShopInfo shopInfo);
}
