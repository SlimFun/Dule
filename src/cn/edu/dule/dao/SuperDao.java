package cn.edu.dule.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.WhereJPQL;
import cn.edu.dule.exception.DataNotExistException;

public interface SuperDao<T> {

	public abstract void save(T entity);

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	T find(Class<T> entityClass, Serializable id);

	void update(T entity);

	void delete(T entity);

	void deleteEntities(Collection<T> entities);

	void delete(Class<T> entityClass, Serializable id);

	void deleteEntities(Class<T> entityClass,
			Collection<? extends Serializable> ids);
	
	void deleteEntities(Class<T> entityClass, WhereJPQL whereJPQL) throws DataNotExistException;
	
	QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, WhereJPQL whereJpql, LinkedHashMap<String, String> orderBy);

	QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy);
	
	QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult, WhereJPQL whereJpql);
	
	QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex, int maxResult);

	QueryResult<T> getScrollData(Class<T> entityClass, WhereJPQL whereJpql);
	
	QueryResult<T> getScrollData(Class<T> entityClass);
	
	long getNumberOfData(Class<T> entityClass, WhereJPQL whereJpql);
	
	long getNumberOfData(Class<T> entityClass);
	
}