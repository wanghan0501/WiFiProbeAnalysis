package com.cs.scu.hbase.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HbaseDaoFactory {
	@Autowired
	private GroupDataDao groupDataDao;

	public BaseHbaseDao getDao(Class<?> daoType){
		if(daoType.equals(GroupDataDao.class)){
			return groupDataDao;
		}
		return null;
	}

}
