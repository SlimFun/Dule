package cn.edu.dule.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.WhereJPQL;
import cn.edu.dule.dao.SuperDao;
import cn.edu.dule.exception.DataNotExistException;

@Repository
public class SuperDaoImpl <T> extends HibernateDaoSupport implements SuperDao<T>{
	
	protected HibernateTemplate hibernateTemplate;
	
	public SuperDaoImpl() {
	}

	@Resource(name="sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
		hibernateTemplate = getHibernateTemplate();
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#save(java.lang.T)
	 */
	public void save(T entity){
		hibernateTemplate.save(entity);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#find(java.lang.Class, java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T find(Class<T> entityClass, Serializable id){
		return (T) hibernateTemplate.get(entityClass, id);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#update(java.lang.T)
	 */
	public void update(T entity){
//		hibernateTemplate.getSessionFactory().openSession().merge(entity);
//		hibernateTemplate.merge(entity);
		hibernateTemplate.update(entity);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#delete(java.lang.T)
	 */
	public void delete(T entity){
		hibernateTemplate.delete(entity);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#delete(java.util.Collection)
	 */
	public void  deleteEntities(Collection<T> entities){
		hibernateTemplate.deleteAll(entities);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#delete(java.lang.Class, java.io.Serializable)
	 */
	public void delete(Class<T> entityClass, Serializable id){
		T entity = find(entityClass, id);
		delete(entity);
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.dule.dao.impl.SuperDao#delete(java.lang.Class, java.util.Collection)
	 */
	public void deleteEntities(Class<T> entityClass, Collection<? extends Serializable> ids){
		for(Serializable id : ids){
			T entity = find(entityClass, id);
			delete(entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	public QueryResult<T> getScrollData(Class<T> entityClass, final int firstIndex,
			final int maxResult, final WhereJPQL whereJpql,
			LinkedHashMap<String, String> orderBy) {
		QueryResult<T> result = new QueryResult<T>();
		final String entityName = getEntityName(entityClass);
		final String orderBySQL = generateOrderBySQL(orderBy);
		List<T> data = (List<T>) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery("select o from " + entityName + " o" + (whereJpql == null?"":whereJpql.generateWhereJPQL()) + orderBySQL);
				if (firstIndex != -1 && maxResult != -1){
					query.setFirstResult(firstIndex)
						.setMaxResults(maxResult);
				}
				return query.list();
			}
		});
		result.setResultList(data);
		Long totalRecord = (Long) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select count(o) from " + entityName + " o")
						.uniqueResult();
			}
		});
		result.setTotalRecord(totalRecord);
		return result;
	}
	
	public QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex,
			int maxResult, LinkedHashMap<String, String> orderBy) {
		return getScrollData(entityClass, firstIndex, maxResult, null, orderBy);
	}

	public QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex,
			int maxResult) {
		return getScrollData(entityClass, firstIndex, maxResult, null, null);
	}

	public QueryResult<T> getScrollData(Class<T> entityClass, int firstIndex,
			int maxResult, WhereJPQL whereJpql) {
		return getScrollData(entityClass, firstIndex, maxResult, whereJpql, null);
	}

	public QueryResult<T> getScrollData(Class<T> entityClass) {
		return getScrollData(entityClass, -1, -1, null, null);
	}
	
	protected String generateOrderBySQL(LinkedHashMap<String, String> orderBy){
		if (orderBy == null || orderBy.size() == 0){
			return "";
		}
		StringBuffer orderBySQL = new StringBuffer();
		orderBySQL.append(" order by ");
		for (String key : orderBy.keySet()){
			// order by o.key1 DESC,o.key2 ASC
			orderBySQL.append("o.")
					.append(key)
					.append(" ")
					.append(orderBy.get(key))
					.append(",");
		}
		orderBySQL.deleteCharAt(orderBySQL.length() - 1);
		return orderBySQL.toString();
	}
	
	protected String getEntityName(Class<T> entityClass){
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		// @Entity(name="xxx") xxx 不为空，则数据库的表名为 xxx，否则为表名为类名
		if (entity.name() != null && !"".equals(entity.name())){
			entityName = entity.name();
		}
		return entityName;
	}

	@Override
	public QueryResult<T> getScrollData(Class<T> entityClass,
			WhereJPQL whereJpql) {
		return getScrollData(entityClass, -1 ,-1, whereJpql);
	}

	@Override
	public long getNumberOfData(Class<T> entityClass) {
		return getNumberOfData(entityClass, null);
	}

	@Override
	public long getNumberOfData(Class<T> entityClass, final WhereJPQL whereJpql) {
		// TODO Auto-generated method stub
		final String entityName = getEntityName(entityClass);
		return (Long) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createQuery("select count(o) from " + entityName + " o" + (whereJpql == null?"":whereJpql.generateWhereJPQL()))
						.uniqueResult();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteEntities(Class<T> entityClass, final WhereJPQL whereJpql) throws DataNotExistException {
		// TODO Auto-generated method stub
		final String entityName = getEntityName(entityClass);
		List<T> data = (List<T>) hibernateTemplate.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				
				return session.createQuery("select o from " + entityName + " o" + (whereJpql == null?"":whereJpql.generateWhereJPQL())).list();
			}
		});
		if(data != null && data.size() != 0)
			getHibernateTemplate().deleteAll(data);
		else
			throw new DataNotExistException();
	}

}
