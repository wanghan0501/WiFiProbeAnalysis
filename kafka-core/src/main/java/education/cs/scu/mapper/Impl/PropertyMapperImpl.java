package education.cs.scu.mapper.Impl;

import com.alibaba.fastjson.JSON;
import education.cs.scu.entity.PropertyBean;
import education.cs.scu.mapper.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * PropertyMapper
 *
 * @Author lch
 * @Create on 2017/08/25 12:22
 **/
public class PropertyMapperImpl {

    @Autowired
    private RedisTemplate<String, PropertyBean> redisTemplate;
    private static final String PROPERTY_BEAN_KEY = "PROPERTY_BEAN";

    /**
     * 更新Property以mmac作为field的记录，Value 为propertyBean对象
     * 由于一个店铺有多个探针，而redis是以K-V的方式，如果以shopId作为字段，
     * 就会让一个shopId有且仅有一个探针mmac，如果一个店铺里面有2个及其以上的mmac，
     * 就不能方便记录，所以用一个mmac对应一个店铺的方式来记录，比较方便。
     * */
    public int setProperty(PropertyBean propertyBean)  {

        try {
            redisTemplate.opsForHash().put(PROPERTY_BEAN_KEY,propertyBean.getMmac(),propertyBean);
        }catch (RedisConnectionFailureException e){
            return 0;
        }
        return 1;
    }

    /**
     * 添加Property以mmac作为field的记录，Value 为propertyBean对象
     * */
    public int addProperty(PropertyBean propertyBean){
        try {
            //redisTemplate.opsForHash().put(PROPERTY_BEAN_KEY,propertyBean.getMmac(),propertyBean);
            redisTemplate.opsForHash().put(PROPERTY_BEAN_KEY,propertyBean.getMmac(), JSON.toJSONString(propertyBean));
        }catch (RedisConnectionFailureException e){
            return 0;
        }
        return 1;
    }

    /**
     * 查询field为 mmac的记录
     * */
    public PropertyBean queryProperty(PropertyBean propertyBean)  {
        PropertyBean resultPropertyBean;
        try {
            resultPropertyBean = (PropertyBean) redisTemplate.opsForHash().get(PROPERTY_BEAN_KEY,propertyBean.getMmac());
        }catch (RedisConnectionFailureException e){
            return null;
        }
        return resultPropertyBean;
    }
}
