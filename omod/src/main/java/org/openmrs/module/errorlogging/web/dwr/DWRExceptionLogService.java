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
package org.openmrs.module.errorlogging.web.dwr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.ErrorLoggingConstants;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.ExceptionLogService;

/**
 * This class exposes some of the methods in {@link ExceptionLogService} via the dwr package.
 */
public class DWRExceptionLogService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private ExceptionLogService exceptionLogService;
	
	public DWRExceptionLogService() {
		exceptionLogService = Context.getService(ExceptionLogService.class);
	}
	
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
	public List<ExceptionLogListItem> getExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                                   String openmrsVersion, String fileName, String methodName,
	                                                   Integer lineNum, String startExceptionDateTime,
	                                                   String endExceptionDateTime, Integer start, Integer length) {
		username = processString(username);
		exceptionClass = processString(exceptionClass);
		exceptionMessage = processString(exceptionMessage);
		openmrsVersion = processString(openmrsVersion);
		fileName = processString(fileName);
		methodName = processString(methodName);
		Date startDateTime = getDateTime(startExceptionDateTime);
		Date endDateTime = getDateTime(endExceptionDateTime);
		List<ExceptionLog> exceptionLogs = exceptionLogService.getExceptionLogs(username, exceptionClass, exceptionMessage,
		    openmrsVersion, fileName, methodName, lineNum, startDateTime, endDateTime, start, length);
		List<ExceptionLogListItem> exceptionLogItems = new Vector<ExceptionLogListItem>();
		for (ExceptionLog exLog : exceptionLogs) {
			exceptionLogItems.add(new ExceptionLogListItem(exLog));
		}
		return exceptionLogItems;
	}
	
	/**
	 * Get the number of exception logs matching a search class name, the
	 * minExceptionDate and the minExceptionTime
	 *
	 * @param username user who experienced the exception
	 * @param exceptionClass class name of the exception
	 * @param exceptionMessage message on the exception
	 * @param openmrsVersion version of the OpenMRS
	 * @param fileName file name where the exception occurred
	 * @param lineNum line number of the file where the exception occurred
	 * @param startExceptionDateTime date since which exceptions thrown
	 * @param endExceptionDateTime date to which exceptions thrown
	 * @return number of exception logs
	 */
	public Integer getCountOfExceptionLogs(String username, String exceptionClass, String exceptionMessage,
	                                       String openmrsVersion, String fileName, String methodName, Integer lineNum,
	                                       String startExceptionDateTime, String endExceptionDateTime) {
		username = processString(username);
		exceptionClass = processString(exceptionClass);
		exceptionMessage = processString(exceptionMessage);
		openmrsVersion = processString(openmrsVersion);
		fileName = processString(fileName);
		methodName = processString(methodName);
		Date startDateTime = getDateTime(startExceptionDateTime);
		Date endDateTime = getDateTime(endExceptionDateTime);
		return exceptionLogService.getCountOfExceptionLogs(username, exceptionClass, exceptionMessage, openmrsVersion,
		    fileName, methodName, lineNum, startDateTime, endDateTime);
	}
	
	/**
	 * Get exception log detail
	 *
	 * @param excLogId exception log id
	 * @return exception log detail
	 */
	public ExceptionLogListItem getExceptionLogDetail(Integer excLogId) {
		ExceptionLog excLog = exceptionLogService.getExceptionLog(excLogId);
		if (excLog != null && excLog.getExceptionLogDetail() != null) {
			ExceptionLogListItem excLogDetailItem = new ExceptionLogListItem(excLog.getExceptionLogDetail());
			return excLogDetailItem;
		} else {
			return null;
		}
	}
	
	/**
	 * Get root cause of exception
	 *
	 * @param excLogId exception log id
	 * @return root cause of exception
	 */
	public ExceptionLogListItem getExceptionRootCause(Integer excLogId) {
		ExceptionLog excLog = exceptionLogService.getExceptionLog(excLogId);
		if (excLog != null && excLog.getExceptionRootCause() != null) {
			ExceptionLogListItem excLogRootCauseItem = new ExceptionLogListItem(excLog.getExceptionRootCause());
			return excLogRootCauseItem;
		} else {
			return null;
		}
	}
	
	/**
	 * Get root cause detail of exception
	 *
	 * @param excLogId exception log id
	 * @return root cause detail of exception
	 */
	public ExceptionLogListItem getExceptionRootCauseDetail(Integer excLogId) {
		ExceptionLog excLog = exceptionLogService.getExceptionLog(excLogId);
		if (excLog != null && excLog.getExceptionRootCause() != null) {
			if (excLog.getExceptionRootCause() != null
			        && excLog.getExceptionRootCause().getExceptionRootCauseDetail() != null) {
				ExceptionLogListItem excLogRootCauseDetailItem = new ExceptionLogListItem(excLog.getExceptionRootCause()
				        .getExceptionRootCauseDetail());
				return excLogRootCauseDetailItem;
			}
		}
		return null;
	}
	
	/**
	 * Delete exception logs
	 *
	 * @param ids ids of exception logs
	 * @return true true in the case of a successful deleting, otherwise - false
	 */
	public boolean purgeExceptionLogs(Integer... ids) {
		for (Integer id : ids) {
			ExceptionLog excLog = exceptionLogService.getExceptionLog(id);
			if (excLog == null) {
				return false;
			}
			exceptionLogService.purgeExceptionLog(excLog);
		}
		return true;
	}
	
	/**
	 * Convert input string arguments to Date
	 *
	 * @param exceptionDateTime date string
	 * @return converted date
	 */
	private Date getDateTime(String exceptionDateTime) {
		Date stExceptionDateTime = null;
		if (StringUtils.isNotBlank(exceptionDateTime)) {
			exceptionDateTime = exceptionDateTime.trim();
			
			String dateTime = exceptionDateTime + ":00";
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			try {
				stExceptionDateTime = (Date) format.parse(dateTime);
			}
			catch (ParseException ex) {
				log.error("Cannot parse date", ex);
			}
		}
		return stExceptionDateTime;
	}
	
	private String processString(String str) {
		String resultStr = null;
		if (StringUtils.isNotBlank(str)) {
			resultStr = str.trim();
		}
		return resultStr;
	}
}
