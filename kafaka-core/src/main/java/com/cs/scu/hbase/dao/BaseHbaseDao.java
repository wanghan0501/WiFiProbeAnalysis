package com.cs.scu.hbase.dao;

import com.cs.scu.hbase.HbaseColumn;
import com.cs.scu.hbase.HbaseOneToMany;
import com.cs.scu.hbase.HbaseOneToOne;
import com.cs.scu.hbase.HbaseTable;
import com.cs.scu.service.HBaseService;
import com.cs.scu.service.model.*;
import com.cs.scu.util.StringUtil;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

public class BaseHbaseDao<T> {
	protected final Class<?> type;
	protected final String tableName;
	protected final Field rowKeyField;
	protected Map<HbaseColCouple,Field> couple_field_mapping;
	protected Map<String,HbaseColCouple> fieldName_couple_mapping;
	protected Map<String,Field> fieldName_field_mapping;	//包括HbaseColumn注释和lazy为false的列
	protected List<Field> positiveField;	//包括lazy为false的列
	protected Map<String,Field> lazy_fieldName_field_mapping;	//包括lazy为true的列

	@Autowired
	private HbaseDaoFactory hbaseDaoFactory;

	@Autowired
	private HBaseService hbaseService;
	
	public BaseHbaseDao(Class<?> type) throws NoSuchFieldException, SecurityException{
		this.type = type;
		HbaseTable table = type.getAnnotation(HbaseTable.class);
		tableName = table.name();
		rowKeyField = type.getDeclaredField(table.rowKey());
		//初始化
		couple_field_mapping = new HashMap<HbaseColCouple,Field>();
		fieldName_couple_mapping = new HashMap<String,HbaseColCouple>();
		fieldName_field_mapping = new HashMap<String,Field>();
		positiveField = new ArrayList<Field>();
		lazy_fieldName_field_mapping = new HashMap<String,Field>();
		
		Field[] fields = type.getDeclaredFields();
		for(Field field:fields){
			//maping条件
			//1：不是主键
			if(!field.equals(rowKeyField)){
				//2.1：有HbaseColumn注释
				HbaseColumn annotation = field.getAnnotation(HbaseColumn.class);
				if(annotation!=null){
					HbaseColCouple col = new HbaseColCouple(annotation.family(),
							annotation.qualifier());
					couple_field_mapping.put(col, field);
					fieldName_couple_mapping.put(field.getName(), col);
					fieldName_field_mapping.put(field.getName(), field);
				}
				//2.2:有HbaseOneToMany注释
				HbaseOneToMany oneToMany = field.getAnnotation(HbaseOneToMany.class);
				if(oneToMany!=null){
					if(oneToMany.lazy()){	//不主动加载
						lazy_fieldName_field_mapping.put(field.getName(),field);
					}else{	//主动加载
						fieldName_field_mapping.put(field.getName(),field);
						positiveField.add(field);
					}
				}
				//2.3:有HbaseOneToOne注释
				HbaseOneToOne oneToOne = field.getAnnotation(HbaseOneToOne.class);
				if(oneToOne!=null){
					if(oneToOne.lazy()){	//不主动加载
						lazy_fieldName_field_mapping.put(field.getName(),field);
					}else{	//主动加载
						fieldName_field_mapping.put(field.getName(),field);
						positiveField.add(field);
					}
				}
			}
		}
	}
	
	/**
	 * 根据行键获取单行
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T findById(String id) throws Exception{
		return findById(id,null,null);
	}
	
	/**
	 * 根据行键获取单行，只获取某一列族，类似select xxx，yyy，zzz
	 * @param id
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @return
	 * @throws Exception
	 */
	public T findByIdRestrictFamily(String id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findById(id,tmpCouple.getFamily(),null);
		}else{
			return findById(id,null,null);
		}
	}
	/**
	 * 根据行键获取单行，只获取某一列，类似select xxx
	 * @param id
	 * @param fieldName DO那列对应的field名
	 * @return
	 * @throws Exception
	 */
	public T findByIdRestrictCol(String id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findById(id,tmpCouple.getFamily(),tmpCouple.getQualifier());
		}else{
			return findById(id,null,null);
		}
	}
	
	private T findById(String id,String family,String qualifier) throws Exception{
		if(id!=null){
			HbaseRow row = hbaseService.findById(tableName, id, family, qualifier);  
			return parseToEntity(row);
		}
		return null;
	}
	
	/**
	 * 根据行键list获取list
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List findByIds(Collection<String> ids) throws Exception{
		return findByIds(ids,null,null);
	}
	/**
	 * 根据行键list获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param ids
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdsRestrictFamily(Collection<String> ids,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIds(ids,tmpCouple.getFamily(),null);
		}else{
			return findByIds(ids,null,null);
		}
	}
	/**
	 * 根据行键list获取list，只获取某一列，类似select xxx
	 * @param ids
	 * @param fieldName DO那列对应的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdsRestrictCol(Collection<String> ids,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIds(ids,tmpCouple.getFamily(),tmpCouple.getQualifier());
		}else{
			return findByIds(ids,null,null);
		}
	}
	
	private List findByIds(Collection<String> ids,String family,String qualifier) throws Exception{
		List<T> entities = new ArrayList<T>();
		if(ids!=null){
			List<HbaseRow> rows = hbaseService.findByIds(tableName, ids, family, qualifier);
			for(HbaseRow row:rows){
				entities.add(parseToEntity(row));
			}
		}
		return entities;
	}
	
	/**
	 * 根据行键正则获取list
	 * @param idExp
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexp(String idExp) throws Exception{
		return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,null,Operator.MUST_PASS_ONE);
	}
	/**
	 * 根据行键正则获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param idExp
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpRestrictFamily(String idExp,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(getListFromString(idExp),tmpCouple.getFamily(),null,null,Operator.MUST_PASS_ONE);
		}else{
			return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,null,Operator.MUST_PASS_ONE);
		}
	}
	/**
	 * 根据行键正则获取list，只获取某一列，类似select xxx
	 * @param idExp
	 * @param fieldName DO那列对应的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpRestrictCol(String idExp,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(getListFromString(idExp),tmpCouple.getFamily(),tmpCouple.getQualifier(),null,Operator.MUST_PASS_ONE);
		}else{
			return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,null,Operator.MUST_PASS_ONE);
		}
	}
	
	/**
	 * 根据行键正则list获取list
	 * @param idExp
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexps(Collection<String> idExp) throws Exception{
		return findByIdRegexpsAndCondition(idExp,null,null,null,Operator.MUST_PASS_ONE);
	}
	/**
	 * 根据行键正则list获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param idExp
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpsRestrictFamily(Collection<String> idExp,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(idExp,tmpCouple.getFamily(),null,null,Operator.MUST_PASS_ONE);
		}else{
			return findByIdRegexpsAndCondition(idExp,null,null,null,Operator.MUST_PASS_ONE);
		}
	}
	/**
	 * 根据行键正则list获取list，只获取某一列，类似select xxx
	 * @param idExp
	 * @param fieldName DO那列对应的field名
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpsRestrictCol(Collection<String> idExp,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(idExp,tmpCouple.getFamily(),tmpCouple.getQualifier(),null,Operator.MUST_PASS_ONE);
		}else{
			return findByIdRegexpsAndCondition(idExp,null,null,null,Operator.MUST_PASS_ONE);
		}
	}

	/**
	 * 根据行键正则和具体列值条件获取list 
	 * @param idExp
	 * @param conditions 列值条件列表
	 * @param op	条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpAndCondition(String idExp,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,conditions,op);
	}
	/**
	 * 根据行键正则和具体列值条件获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param idExp 
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @param conditions	列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpAndConditionRestrictFamily(String idExp,String fieldName,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(getListFromString(idExp),tmpCouple.getFamily(),null,conditions,op);
		}else{
			return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,conditions,op);
		}
	}
	/**
	 * 根据行键正则和具体列值条件获取list，只获取某一列，类似select xxx
	 * @param idExp
	 * @param fieldName DO那列对应的field名
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpAndConditionRestrictCol(String idExp, String fieldName, Collection<HbaseConditonModel> conditions, Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(getListFromString(idExp),tmpCouple.getFamily(),tmpCouple.getQualifier(),conditions,op);
		}else{
			return findByIdRegexpsAndCondition(getListFromString(idExp),null,null,conditions,op);
		}
	}
	
	/**
	 * 根据行键正则list和具体列值条件获取list 
	 * @param idExp
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpsAndCondition(Collection<String> idExp,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		return findByIdRegexpsAndCondition(idExp,null,null,conditions,op);
	}
	/**
	 * 根据行键正则list和具体列值条件获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param idExp
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpsAndConditionRestrictFamily(Collection<String> idExp,String fieldName,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(idExp,tmpCouple.getFamily(),null,conditions,op);
		}else{
			return findByIdRegexpsAndCondition(idExp,null,null,conditions,op);
		}
	}
	/**
	 * 根据行键正则list和具体列值条件获取list，只获取某一列，类似select xxx
	 * @param idExp
	 * @param fieldName DO那列对应的field名
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByIdRegexpsAndConditionRestrictCol(Collection<String> idExp,String fieldName,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(idExp,tmpCouple.getFamily(),tmpCouple.getQualifier(),conditions,op);
		}else{
			return findByIdRegexpsAndCondition(idExp,null,null,conditions,op);
		}
	}
	
	/**
	 * 根据具体列值条件获取list
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByCondition(Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		return findByIdRegexpsAndCondition(null,null,null,conditions,op);
	}
	/**
	 * 根据具体列值条件获取list，只获取某一列族，类似select xxx，yyy，zzz
	 * @param fieldName  列族里的随便一个列，DO的field名
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByConditionRestrictFamily(String fieldName,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(null,tmpCouple.getFamily(),null,conditions,op);
		}else{
			return findByIdRegexpsAndCondition(null,null,null,conditions,op);
		}
	}
	/**
	 * 根据具体列值条件获取list，只获取某一列，类似select xxx
	 * @param fieldName DO那列对应的field名
	 * @param conditions 列值条件列表
	 * @param op 条件间关系 是全部通过才行（and），还是一个通过就行（or）
	 * @return
	 * @throws Exception
	 */
	public List findByConditionRestrictCol(String fieldName,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			return findByIdRegexpsAndCondition(null,tmpCouple.getFamily(),tmpCouple.getQualifier(),conditions,op);
		}else{
			return findByIdRegexpsAndCondition(null,null,null,conditions,op);
		}
	}
	
	private List findByIdRegexpsAndCondition(Collection<String> idExp,String family,String qualifier,Collection<HbaseConditonModel> conditions,Operator op) throws Exception{
		Collection<HbaseServiceConditonModel> hbaseCondition = null;
		if(conditions!=null){
			hbaseCondition = parseCondition(conditions);
		}
		List<T> entities = new ArrayList<T>();
		List<HbaseRow> rows = hbaseService.findByIdRegexpAndCondition(tableName, idExp, family, qualifier, hbaseCondition, op);
		for(HbaseRow row:rows){
			entities.add(parseToEntity(row));
		}
		return entities;
	}
	
	/**
	 * 保存
	 * @param entity
	 * @throws Exception
	 */
	public void save(T entity) throws Exception{
		if(entity!=null){
			hbaseService.saveOrUpdate(tableName, parseToHbaseRow(entity));
		}
	}
	/**
	 * 批量保存
	 * @param entities
	 * @throws Exception
	 */
	public void saveBatch(Collection<T> entities) throws Exception{
		if(entities!=null&&entities.size()>0){
			List<HbaseRow> rows = new ArrayList<HbaseRow>();
			for(T entity:entities){
				rows.add(parseToHbaseRow(entity));
			}
			hbaseService.saveOrUpdates(tableName, rows);
		}
	}
	
	/**
	 * 根据行键删除整行 
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(String id) throws Exception{
		deleteById(id,null,null);
	}
	/**
	 * 根据行键删除某一列族的所有值
	 * @param id
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @throws Exception
	 */
	public void deleteByIdRestrictFamily(String id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			deleteById(id,tmpCouple.getFamily(),null);
		}else{
			deleteById(id,null,null);
		}
	}
	/**
	 * 根据行键删除某一列的值 
	 * @param id
	 * @param fieldName DO那列对应的field名
	 * @throws Exception
	 */
	public void deleteByIdRestrictCol(String id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			deleteById(id,tmpCouple.getFamily(),tmpCouple.getQualifier());
		}else{
			deleteById(id,null,null);
		}
	}
	
	private void deleteById(String id,String family,String qualifier) throws Exception{
		if(id!=null){
			hbaseService.delete(tableName, id, family, qualifier);  
		}
	}
	
	/**
	 * 根据行键列表删除整行
	 * @param id
	 * @throws Exception
	 */
	public void deleteByIds(Collection<String> id) throws Exception{
		deleteByIds(id,null,null);
	}
	/**
	 * 根据行键列表删除某一列族的所有值
	 * @param id
	 * @param fieldName 列族里的随便一个列，DO的field名
	 * @throws Exception
	 */
	public void deleteByIdsRestrictFamily(Collection<String> id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			deleteByIds(id,tmpCouple.getFamily(),null);
		}else{
			deleteByIds(id,null,null);
		}
	}
	/**
	 * 根据行键列表删除某一列的值 
	 * @param id
	 * @param fieldName DO那列对应的field名
	 * @throws Exception
	 */
	public void deleteByIdsRestrictCol(Collection<String> id,String fieldName) throws Exception{
		HbaseColCouple tmpCouple = fieldName_couple_mapping.get(fieldName);
		if(tmpCouple!=null){
			deleteByIds(id,tmpCouple.getFamily(),tmpCouple.getQualifier());
		}else{
			deleteByIds(id,null,null);
		}
	}
	
	private void deleteByIds(Collection<String> id,String family,String qualifier) throws Exception{
		if(id!=null){
			hbaseService.deletes(tableName, id, family, qualifier);  
		}
	}
	
	protected T parseToEntity(HbaseRow row) throws Exception{
		if(row!=null){
			T entity = (T) type.newInstance();
			//赋值主键
			rowKeyField.set(entity, row.getRowKey());
			//赋值列
			for(HbaseCell cell:row.getCells()){
				Field field = couple_field_mapping.get(cell.getColCouple());
				if(field==null){	//如果是没指定列限定符的列
					HbaseColCouple tmpCouple = new HbaseColCouple(cell.getFamily());
					field = couple_field_mapping.get(tmpCouple);
					if(field!=null&&field.getType().equals(Map.class)){	//field不为空且类型为Map才合法
						Object fieldValue = field.get(entity);
						if(fieldValue!=null){ //不是第一个值。直接把值放到map里
							Map<String,String> qualifier_values = (Map<String,String>)fieldValue;
							qualifier_values.put(cell.getQualifier(), cell.getValue());
						}else{	//是第一个值，新建map，赋值到field
							Map<String,String> qualifier_values = new HashMap<String,String>();
							qualifier_values.put(cell.getQualifier(), cell.getValue());
							field.set(entity, qualifier_values);
						}
					}
				}else{	//如果指定了直接赋值
					field.set(entity, cell.getValue());
				}
			}
			getJoinTable(entity,positiveField);
			return entity;
		}
		return null;
	}
	
	protected void getJoinTable(T entity,List<Field> fieldList) throws Exception{
		for(Field field:fieldList){
			HbaseOneToMany oneToMany = field.getAnnotation(HbaseOneToMany.class);
			if(oneToMany!=null){
				//1：joinField不得为空
				Field joinField = fieldName_field_mapping.get(oneToMany.joinField());
				if(joinField!=null&&joinField.getType().equals(Map.class)){
					//建dao
					Class<?> daoType = oneToMany.joinTableDao();
					BaseHbaseDao dao = hbaseDaoFactory.getDao(daoType);
					//获取值
					Map<String,String> key_values = (Map<String,String>) joinField.get(entity);
					//建结果
					TreeMap<String,List> joinResult = new TreeMap<String,List>();
					for(Entry<String,String> entry:key_values.entrySet()){
						String[] rowKeys = entry.getValue().split(oneToMany.splitSign());
						List<String> rowKeySet = Arrays.asList(rowKeys);
						List joinEntities = dao.findByIds(rowKeySet);
						joinResult.put(entry.getKey(), joinEntities);
					}
					field.set(entity, joinResult);	//把值赋到对象上
				}
			}

			HbaseOneToOne oneToOne = field.getAnnotation(HbaseOneToOne.class);
			if(oneToOne!=null){
				//1：joinField不得为空
				Field joinField = fieldName_field_mapping.get(oneToOne.joinField());
				if(joinField!=null&&joinField.getType().equals(Map.class)){
					//建dao
					Class<?> daoType = oneToOne.joinTableDao();
					BaseHbaseDao dao = hbaseDaoFactory.getDao(daoType);
					//获取值
					Map<String,String> key_values = (Map<String,String>) joinField.get(entity);
					boolean changeKey = oneToOne.changeKey();
					String orderKey = oneToOne.orderKey();
					//建结果
					TreeMap joinResult = new TreeMap();
					for(Entry<String,String> entry:key_values.entrySet()){
						//得到值
						Object joinEntity = dao.findById(entry.getValue());
						//得到key
						String key = entry.getKey();
						if(changeKey){
							key = (String)dao.getFieldValue(joinEntity, orderKey);
						}
						joinResult.put(key, joinEntity);
					}
					field.set(entity, joinResult);	//把值赋到对象上
				}
			}
		}
	}
	
	protected HbaseRow parseToHbaseRow(T entity) throws IllegalArgumentException, IllegalAccessException{
		if(entity!=null){
			String rowKey = String.valueOf(rowKeyField.get(entity));
			HbaseRow hbaseRow = new HbaseRow(rowKey);
			Field[] fields = type.getDeclaredFields();
			for(Field field:fields){
				//存入条件
				//1：不是主键
				if(!field.equals(rowKeyField)){
					HbaseColumn annotation = field.getAnnotation(HbaseColumn.class);
					//2：有HbaseColumn注释，别的注释不会转换
					if(annotation!=null){
						Object origin_value = field.get(entity);
						//3：值不为空
						if(origin_value!=null){
							String family = annotation.family();
							String qualifier = annotation.qualifier();
							if(!qualifier.equals("")){	//指定了列标示符的固定列
								String value = String.valueOf(origin_value);
								if(StringUtil.isNotBlank(value)){	//3：再次检查不为空或不为空白值
									//达阵：增加列
									HbaseCell cell = new HbaseCell(family, qualifier, value);
									hbaseRow.addCell(cell);
								}
							}else if(field.getType().equals(Map.class)){	//没指定列标识符
								Map<String,String> qualifier_values = (Map<String,String>)origin_value;
								for(Entry<String,String> qualifier_value:qualifier_values.entrySet()){
									//3'：值不为空(不指定标示符的map)
									if(StringUtil.isNotBlank(qualifier_value.getValue())){
										//达阵：增加列
										HbaseCell cell = new HbaseCell(family, qualifier_value.getKey(), qualifier_value.getValue());
										hbaseRow.addCell(cell);
									}
								}
							}
						}
					}
				}
			}
			return hbaseRow;
		}
		return null;
	}
	
	protected Collection<HbaseServiceConditonModel> parseCondition(Collection<HbaseConditonModel> conditions){
		List<HbaseServiceConditonModel> result = new ArrayList<HbaseServiceConditonModel>();
		if(conditions!=null){
			for(HbaseConditonModel con:conditions){
				HbaseColCouple tmpCouple = fieldName_couple_mapping.get(con.getField());
				if(tmpCouple!=null){
					result.add(new HbaseServiceConditonModel(
							tmpCouple.getFamily(),tmpCouple.getQualifier(),con.getValue(),con.getOp()));
				}
			}
			return result;
		}
		return null;
	}
	
	protected List<String> getListFromString(String str){
		List<String> list = new ArrayList<String>();
		list.add(str);
		return list;
	}
	
	//根据成员变量名获取成员变量值
	public Object getFieldValue(T entity,String fieldName) throws Exception{
		//已加载过的值
		Field field = fieldName_field_mapping.get(fieldName);
		if(field!=null){
			return field.get(entity);
		}
		//未加载过得值
		field = lazy_fieldName_field_mapping.get(fieldName);
		if(field!=null){
			//先获取
			getJoinTable(entity,Arrays.asList(field));
			//再取值
			return field.get(entity);
		}
		return null;
	}
	
	//获取主键值
	public String getRowKey(T entity) throws IllegalArgumentException, IllegalAccessException{
		return (String) rowKeyField.get(entity);
	}
}
