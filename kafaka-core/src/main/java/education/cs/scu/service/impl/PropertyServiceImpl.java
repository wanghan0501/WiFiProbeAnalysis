package education.cs.scu.service.impl;

import education.cs.scu.entity.PropertyBean;
import education.cs.scu.mapper.PropertyMapper;
import education.cs.scu.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maicius on 2017/6/28.
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;
    public int addProperty(PropertyBean propertyBean) throws Exception {
        return propertyMapper.addProperty(propertyBean);
    }

    public int setProperty(PropertyBean propertyBean) throws Exception {
        return propertyMapper.setProperty(propertyBean);
    }

    public PropertyBean queryProperty(PropertyBean propertyBean) throws Exception{
        return propertyMapper.queryProperty(propertyBean);
    }
}
