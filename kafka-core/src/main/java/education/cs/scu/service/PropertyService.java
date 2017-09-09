package education.cs.scu.service;

import education.cs.scu.entity.PropertyBean;

/**
 * Created by maicius on 2017/6/28.
 */
public interface PropertyService {
    int addProperty(PropertyBean propertyBean) throws Exception;
    int setProperty(PropertyBean propertyBean) throws Exception;
    PropertyBean queryProperty(PropertyBean propertyBean) throws Exception;
}
