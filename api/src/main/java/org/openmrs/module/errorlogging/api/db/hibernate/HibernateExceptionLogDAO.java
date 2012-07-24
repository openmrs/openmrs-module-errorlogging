/**
 * The contents of this file are subject to the OpenMRS Public License Version
 * 1.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 *
 * Copyright (C) OpenMRS, LLC. All Rights Reserved.
 */
package org.openmrs.module.errorlogging.api.db.hibernate;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.db.ExceptionLogDAO;
import org.openmrs.module.errorlogging.util.ExceptionLogUtil;

/**
 * It is a default implementation of  {@link ExceptionLogDAO}.
 */
public class HibernateExceptionLogDAO implements ExceptionLogDAO {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/**
	 * @see {@link ExceptionLogDAOe#saveExceptionLog(ExceptionLog)}
	 */
	@Override
	public ExceptionLog saveExceptionLog(ExceptionLog exceptionLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(exceptionLog);
		return exceptionLog;
	}
	
	/**
	 * @see {@link ExceptionLogDAO#deleteExceptionLog())}
	 */
	@Override
	public void deleteExceptionLog(ExceptionLog exceptionLog) {
		sessionFactory.getCurrentSession().delete(exceptionLog);
	}
	
	/**
	 * @see {@link ExceptionLogDAO#getExceptionLog(Integer)}
	 */
	@Override
	public ExceptionLog getExceptionLog(Integer exceptionLogId) {
		return (ExceptionLog) sessionFactory.getCurrentSession().get(ExceptionLog.class, exceptionLogId);
	}
	
	/**
	 * @see {@link ExceptionLogDAO#getExceptionLogs(String, Date, Date, Integer, Integer)}
	 */
	@Override
	public List<ExceptionLog> getExceptionLogs(String exceptionClass, Date startExceptionDateTime,
	                                           Date endExceptionDateTime, Integer start, Integer length) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExceptionLog.class);
		if (exceptionClass != null) {
			criteria.add(Restrictions.eq("exceptionClass", exceptionClass));
		}
		if (startExceptionDateTime != null) {
			criteria.add(Restrictions.ge("dateCreated", startExceptionDateTime));
		}
		if (endExceptionDateTime != null) {
			criteria.add(Restrictions.le("dateCreated", endExceptionDateTime));
		}
		criteria.addOrder(Order.desc("dateCreated"));
		if (start != null && start >= 0) {
			criteria.setFirstResult(start);
		}
		if (length != null && length >= 0) {
			criteria.setMaxResults(length);
		}
		return criteria.list();
	}
	
	/**
	 * @see {@link ExceptionLogDAO#getCountOfExceptionLogs(String, Date, Date)}
	 */
	@Override
	public Integer getCountOfExceptionLogs(String exceptionClass, Date startExceptionDateTime, Date endExceptionDateTime) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExceptionLog.class);
		if (exceptionClass != null) {
			criteria.add(Restrictions.eq("exceptionClass", exceptionClass));
		}
		if (startExceptionDateTime != null) {
			criteria.add(Restrictions.ge("dateCreated", startExceptionDateTime));
		}
		if (endExceptionDateTime != null) {
			criteria.add(Restrictions.le("dateCreated", endExceptionDateTime));
		}
		criteria.setProjection(Projections.rowCount());
		Object count = criteria.uniqueResult();
		if (count instanceof Integer) {
			return (Integer) count;
		} else {
			return ExceptionLogUtil.convertToInteger((Long) count);
		}
	}
}
