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
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.db.ExceptionLogDAO;

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
	
	@Override
	public ExceptionLog saveExceptionLog(ExceptionLog exceptionLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(exceptionLog);
		return exceptionLog;
	}
	
	@Override
	public void deleteExceptionLog(ExceptionLog exceptionLog) {
		sessionFactory.getCurrentSession().delete(exceptionLog);
	}
	
	@Override
	public ExceptionLog getExceptionLog(Integer exceptionLogId) {
		return (ExceptionLog) sessionFactory.getCurrentSession().get(ExceptionLog.class, exceptionLogId);
	}
	
	@Override
	public List<ExceptionLog> getExceptionLogs(String exceptionClass, Date exceptionDateTime, Integer start, Integer length) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExceptionLog.class);
		if (exceptionClass != null) {
			criteria.add(Restrictions.eq("exceptionClass", exceptionClass));
		}
		if (exceptionDateTime != null) {
			criteria.add(Restrictions.eq("dateThrown", exceptionDateTime));
		}
		criteria.setFirstResult(start);
		criteria.setMaxResults(length);
		return criteria.list();
	}
}
