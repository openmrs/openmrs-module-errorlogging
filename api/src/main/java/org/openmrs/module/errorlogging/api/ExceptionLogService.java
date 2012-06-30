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
package org.openmrs.module.errorlogging.api;

import java.util.Date;
import java.util.List;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml. <p> It can be accessed
 * only via Context:<br>
 * <code>
 * Context.getService(ExceptionLogService.class).someMethod();
 * </code>
 *
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface ExceptionLogService extends OpenmrsService {
	
	/**
	 * Create or update exception log, including detail information:
	 * @see ExceptionLogDetail
	 * @see ExceptionRootCause
	 * @see ExceptionRootCauseDetail
	 * Saves the given <code>exceptionLog</code> to the database
	 * 
	 * @param exceptionLog exception log to be created or updated
	 * @return exception log that was created or updated
	 */
	public ExceptionLog saveExceptionLog(ExceptionLog exceptionLog);
	
	/**
	 * Deletes a exception log and all detail information related with it from the database
	 * 
	 * @param exceptionLog exception log to be deleted from the database
	 */
	public void purgeExceptionLog(ExceptionLog exceptionLog);
	
	/**
	 * Get exception log by its exceptionLogId
	 * 
	 * @param exceptionLogId id of exception log
	 * @return exception log
	 */
	public ExceptionLog getExceptionLog(Integer exceptionLogId);
	
	/**
	 * Get the list of exception logs by class name that thrown since minExceptionDateTime
	 * 
	 * @param exceptionClass class name of the exception
	 * @param minExceptionDateTime date since which exceptions thrown
	 * @param start starting from the "start" record
	 * @param length retrieve the next "length" records from database
	 * @return list of exception logs
	 */
	public List<ExceptionLog> getExceptionLogs(String exceptionClass, Date minExceptionDateTime, Integer start,
	                                           Integer length);
	
	/**
	 * Return the number of exception logs matching a search class name and the minExceptionDateTime
	 * 
	 * @param exceptionClass class name of the exception
	 * @param minExceptionDateTime date since which exceptions thrown
	 * @return the number of exception logs matching the search arguments
	 */
	public Integer getCountOfExceptionLogs(String exceptionClass, Date minExceptionDateTime);
}
