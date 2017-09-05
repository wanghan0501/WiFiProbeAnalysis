package edu.cs.scu.hbase.dao;

import edu.cs.scu.hbase.dataObject.GroupData;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDataDao extends BaseHbaseDao<GroupData>{

    public GroupDataDao() throws NoSuchFieldException, SecurityException {
        super(GroupData.class);
    }
}
