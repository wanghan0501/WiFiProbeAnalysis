package education.cs.scu.service.impl;

import education.cs.scu.entity.ProbeInfo;
import education.cs.scu.entity.ShopInfo;
import education.cs.scu.entity.User;
import education.cs.scu.mapper.ShopMapper;
import education.cs.scu.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maicius on 2017/6/27.
 */
@Service
public class ShopServiceImpl implements  ShopService{

    @Autowired
    ShopMapper shopMapper;


    public List<ShopInfo> queryShopInfos(ShopInfo shopInfo) throws Exception {
        return shopMapper.queryShopInfos(shopInfo);
    }

    public int addShopInfo(ShopInfo shopInfo) throws Exception{
        return shopMapper.addShopInfo(shopInfo);
    }

    public int updateShopInfo(ShopInfo shopInfo) throws Exception{
        return shopMapper.updateShopInfo(shopInfo);
    }

    public long getUniqueShopId() throws Exception {
        return shopMapper.getUniqueShopId();
    }

    public List<ProbeInfo> queryProbeInfos(ProbeInfo probeInfo) throws Exception{
        return shopMapper.queryProbeInfos(probeInfo);
    }

    public List<ProbeInfo> queryshopProbeInfos(ShopInfo shopInfo) throws Exception {
        return shopMapper.queryShopProbeInfo(shopInfo);
    }

    public int addProbeInfo(ProbeInfo probeInfo) {
        return shopMapper.addProbeInfo(probeInfo);
    }

    public List<ShopInfo> queryShopNameById(ShopInfo shopInfo) {
        return shopMapper.queryShopNameById(shopInfo);
    }
}
