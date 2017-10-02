package education.cs.scu.mapper.Impl;

import education.cs.scu.entity.ProbeInfo;
import education.cs.scu.entity.ShopInfo;
import education.cs.scu.entity.User;
import education.cs.scu.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ShopMapperImpl
 *
 * @Author lch
 * @Create on 2017/08/25 15:09
 **/
public class ShopMapperImpl  {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SHOP_INFO_KEY = "SHOP_INFO";
    private static final String PROBE_INFO_KEY = "PROBE_INFO";
    private static final String UNIQUE_SHOP_ID_KEY = "UNIQUE_SHOP_ID";

    /**
     * 批量查询多个商店的信息
     */
    public List<ShopInfo> queryShopInfos(List<ShopInfo> shopInfos) {

        List<ShopInfo> results = new ArrayList<ShopInfo>();
        List<ShopInfo> temps = new ArrayList<ShopInfo>();
        Map<Object, Object> map = redisTemplate.opsForHash().entries(SHOP_INFO_KEY);
        for (ShopInfo si : shopInfos) {

            if (map.containsKey(si.getShop_owner())) {
                temps = (List<ShopInfo>) map.get(si.getShop_owner());
                results.addAll(temps);
                System.out.println(map.get(si.getShop_owner()));
            }
            //results.add((ShopInfo) redisTemplate.opsForHash().get(SHOP_INFO_KEY, si.getShop_owner()));
        }
        return results;
    }

    /**
     * 查询一个商店的信息
     */
    public List<ShopInfo> queryShopInfos(ShopInfo shopInfo) {

        List<ShopInfo> results = new ArrayList<ShopInfo>();
        Map<Object, Object> map = redisTemplate.opsForHash().entries(SHOP_INFO_KEY);

        if (map.containsKey(shopInfo.getShop_owner())) {
            results = (List<ShopInfo>) map.get(shopInfo.getShop_owner());
            //System.out.println(map.get(shopInfo.getShop_owner()));
        }
        return results;
    }

    /**
     * 以username作为唯一key，username是用户注册时候的手机号，value是一个list对象，里面存放了一个用户的多个商店信息
     */
    public int addShopInfo(ShopInfo shopInfo) {
        try {
            List<ShopInfo> shopInfoList = queryShopInfos(shopInfo);
            shopInfoList.add(shopInfo);
            redisTemplate.opsForHash().put(SHOP_INFO_KEY, shopInfo.getShop_owner(), shopInfoList);
        } catch (RedisConnectionFailureException e) {
            return 0;
        } catch (ClassCastException e) {
            return 0;
        }
        return 1;
    }

    /**
     * 单条修改
     * 更新商店信息，先读出list，然后根据shopId进行修改
     */
    public int updateShopInfo(ShopInfo shopInfo) {
        List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();
        shopInfoList = queryShopInfos(shopInfo);
        for (int i = 0; i < shopInfoList.size(); i++) {
            if (shopInfo.getShop_id() == shopInfoList.get(i).getShop_id()) {
                shopInfoList.set(i, shopInfo);
            }
        }

        try {
            redisTemplate.opsForHash().put(SHOP_INFO_KEY, shopInfo.getShop_owner(), shopInfoList);
        } catch (RedisConnectionFailureException e) {
            return 0;
        }
        return 1;
    }

    /**
     * 用于生成唯一的SHOP_id
     **/
    public long getUniqueShopId() {
        long res = System.currentTimeMillis();
        boolean b = redisTemplate.opsForSet().isMember(UNIQUE_SHOP_ID_KEY, String.valueOf(res));
        if (b) {
            res = getUniqueShopId();
        }
        redisTemplate.opsForSet().add(UNIQUE_SHOP_ID_KEY, String.valueOf(res));
        return res;

    }

    /**
     * 查询用户的所有探针信息
     *
     * */
    public List<ProbeInfo> queryProbeInfos(ProbeInfo probeInfo) {
        List<ProbeInfo> results = new ArrayList<ProbeInfo>();
        try {
            Map<Object, Object> map = redisTemplate.opsForHash().entries(PROBE_INFO_KEY);
            if (map.containsKey(probeInfo.getUser_name())) {
                results = (List<ProbeInfo>) map.get(probeInfo.getUser_name());
            }
        } catch (RedisConnectionFailureException e) {
            return null;
        }
        return results;
    }

    /**
     * 查询店铺的探针信息
     * */
    public List<ProbeInfo> queryShopProbeInfo(ShopInfo shopInfo) {
        List<ProbeInfo> results = new ArrayList<ProbeInfo>();
        try {
            Map<Object, Object> map = redisTemplate.opsForHash().entries(PROBE_INFO_KEY);
            if (map.containsKey(shopInfo.getShop_owner())) {
                results = (List<ProbeInfo>) map.get(shopInfo.getShop_owner());
            }
            for (ProbeInfo pi:results) {
                if (pi.getShop_id() != shopInfo.getShop_id()) {
                    results.remove(pi);
                }
            }
        } catch (RedisConnectionFailureException e) {
            return null;
        }
        return results;
    }

    /**
     * 给指定用户添加探针信息
     */
    public int addProbeInfo(ProbeInfo probeInfo) {
        List<ProbeInfo> probeInfoList = new ArrayList<ProbeInfo>();
        try {
            probeInfoList = queryProbeInfos(probeInfo);
            probeInfoList.add(probeInfo);
            redisTemplate.opsForHash().put(PROBE_INFO_KEY, probeInfo.getUser_name(), probeInfoList);
        } catch (RedisConnectionFailureException e) {
            return 0;
        }
        return 1;
    }
}
