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
import org.openmrs.annotation.Authorized;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.errorlogging.*;
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
	 * Delete a exception log and all detail information related with it from the database
	 * 
	 * @param exceptionLog exception log to be deleted from the database
	 */
	@Authorized( { ErrorLoggingConstants.PRIV_VIEW_ERROR_LOGGING })
	public void purgeExceptionLog(ExceptionLog exceptionLog);
	
	/**
	 * Get exception log by its exceptionLogId
	 * 
	 * @param exceptionLogId id of exception log
	 * @return exception log
	 */
	@Authorized( { ErrorLoggingConstants.PRIV_VIEW_ERROR_LOGGING })
	public ExceptionLog getExceptionLog(Integer exceptionLogId);
	
	/**
	 * Get the list of exception logs by input parameters
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
	 * @return list of exception logs
	 */
	@Authorized( { ErrorLoggingConstants.PRIV_VIEW_ERROR_LOGGING })
	public List<ExceptionLog> getExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                           String openmrsVersion, String fileName, String methodName, Integer lineNum,
	                                           Date startExceptionDateTime, Date endExceptionDateTime, Integer start,
	                                           Integer length);
	
	/**
	 * Return the number of exception logs by input parameters
	 * 
	 * @param username user who experienced the exception
	 * @param exceptionClass class name of the exception
	 * @param exceptionMessage message on the exception
	 * @param openmrsVersion version of the OpenMRS
	 * @param fileName file name where the exception occurred
	 * @param lineNum line number of the file where the exception occurred
	 * @param startExceptionDateTime date since which exceptions thrown
	 * @param endExceptionDateTime date to which exceptions thrown
	 * @return the number of exception logs matching the search arguments
	 */
	@Authorized( { ErrorLoggingConstants.PRIV_VIEW_ERROR_LOGGING })
	public Integer getCountOfExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                       String openmrsVersion, String fileName, String methodName, Integer lineNum,
	                                       Date startExceptionDateTime, Date endExceptionDateTime);
}
