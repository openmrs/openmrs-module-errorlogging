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
import org.hibernate.criterion.*;
import org.openmrs.User;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
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
	 * @see {@link ExceptionLogDAO#getExceptionLogs(String, String, String, String, Integer, Date, Date, Integer, Integer)}
	 */
	@Override
	public List<ExceptionLog> getExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                           String openmrsVersion, String fileName, String methodName, Integer lineNum,
	                                           Date startExceptionDateTime, Date endExceptionDateTime, Integer start,
	                                           Integer length) {
		Criteria criteria = createExceptionLogSearchCriteria(username, exceptionClass, exceptionMessage, openmrsVersion,
		    fileName, methodName, lineNum, startExceptionDateTime, endExceptionDateTime);
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
	 * @see {@link ExceptionLogDAO#getCountOfExceptionLogs(String, String, String, String, String, Integer, Date, Date)}
	 */
	@Override
	public Integer getCountOfExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                       String openmrsVersion, String fileName, String methodName, Integer lineNum,
	                                       Date startExceptionDateTime, Date endExceptionDateTime) {
		Criteria criteria = createExceptionLogSearchCriteria(username, exceptionClass, exceptionMessage, openmrsVersion,
		    fileName, methodName, lineNum, startExceptionDateTime, endExceptionDateTime);
		criteria.setProjection(Projections.rowCount());
		Object count = criteria.uniqueResult();
		if (count instanceof Integer) {
			return (Integer) count;
		} else {
			return ExceptionLogUtil.convertToInteger((Long) count);
		}
	}
	
	/**
	 * Utility method that returns a criteria for searching for exception logs
	 * that match the specified search phrase and arguments
	 *
	 * @param username user who experienced the exception
	 * @param exceptionClass class name of the exception
	 * @param exceptionMessage message on the exception
	 * @param openmrsVersion version of the OpenMRS
	 * @param fileName file name where the exception occurred
	 * @param lineNum line number of the file where the exception occurred
	 * @param startExceptionDateTime date since which exceptions thrown
	 * @param endExceptionDateTime date to which exceptions thrown
	 * @param start starting from the "start" record
	 * @param length retrieve the next "length" records from database
	 * @return the generated criteria object
	 */
	private Criteria createExceptionLogSearchCriteria(String username, String exceptionClass, String exceptionMessage,
	                                                  String openmrsVersion, String fileName, String methodName,
	                                                  Integer lineNum, Date startExceptionDateTime, Date endExceptionDateTime) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ExceptionLog.class);
		Conjunction junction = Restrictions.conjunction();
		if (username != null) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(User.class);
			subCriteria.add(Restrictions.like("username", username));
			subCriteria.setProjection(Projections.property("userId"));
			junction.add(Subqueries.propertyEq("creator.userId", subCriteria));
		}
		if (exceptionClass != null) {
			criteria.add(Restrictions.like("exceptionClass", exceptionClass));
		}
		if (exceptionMessage != null) {
			criteria.add(Restrictions.like("exceptionMessage", exceptionMessage, MatchMode.ANYWHERE));
		}
		if (openmrsVersion != null) {
			criteria.add(Restrictions.like("openmrsVersion", openmrsVersion));
		}
		if (fileName != null) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(ExceptionLogDetail.class);
			subCriteria.add(Restrictions.like("fileName", fileName));
			subCriteria.setProjection(Projections.property("exceptionLogDetailId"));
			junction.add(Subqueries.propertyIn("exceptionLogId", subCriteria));
		}
		if (methodName != null) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(ExceptionLogDetail.class);
			subCriteria.add(Restrictions.like("methodName", methodName));
			subCriteria.setProjection(Projections.property("exceptionLogDetailId"));
			junction.add(Subqueries.propertyIn("exceptionLogId", subCriteria));
		}
		if (lineNum != null) {
			DetachedCriteria subCriteria = DetachedCriteria.forClass(ExceptionLogDetail.class);
			subCriteria.add(Restrictions.eq("lineNumber", lineNum));
			subCriteria.setProjection(Projections.property("exceptionLogDetailId"));
			junction.add(Subqueries.propertyIn("exceptionLogId", subCriteria));
		}
		if (startExceptionDateTime != null) {
			criteria.add(Restrictions.ge("dateCreated", startExceptionDateTime));
		}
		if (endExceptionDateTime != null) {
			criteria.add(Restrictions.le("dateCreated", endExceptionDateTime));
		}
		criteria.add(junction);
		return criteria;
	}
}
