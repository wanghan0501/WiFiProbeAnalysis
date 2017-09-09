package education.cs.scu.service.impl;

import education.cs.scu.entity.HistoryData;
import education.cs.scu.entity.entityData.Day;
import education.cs.scu.entity.entityData.Hour;
import education.cs.scu.entity.entityData.Month;
import education.cs.scu.entity.entityData.Year;
import education.cs.scu.mapper.QueryHistoryDataMapper;
import education.cs.scu.service.QueryHistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maicius on 2017/6/28.
 */
@Service
public class QueryHistoryDataServiceImpl implements QueryHistoryDataService{
    @Autowired
    QueryHistoryDataMapper queryHistoryData;

    public int addActivityData() throws Exception {
        return queryHistoryData.addActivityData();
    }

    public List<Year> queryActivityYear() throws Exception {
        return queryHistoryData.queryActivityYear();
    }

    public List<Month> queryActivityMonth() throws Exception {
        return queryHistoryData.queryActivityMonth();
    }

    public List<Hour> queryActivityDay(String date) throws Exception {
        return queryHistoryData.queryActivityDay(date);
    }

}
