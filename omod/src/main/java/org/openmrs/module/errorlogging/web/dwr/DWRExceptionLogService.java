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
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.ExceptionLogService;
import org.openmrs.module.errorlogging.util.ExceptionLogUtil;

/**
 * This class exposes some of the methods in {@link ExceptionLogService} via the dwr package
 */
public class DWRExceptionLogService {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private ExceptionLogService exceptionLogService;
	
	public DWRExceptionLogService() {
		exceptionLogService = Context.getService(ExceptionLogService.class);
	}
	
	/**
	 * Get the list of exception logs by class name that thrown since minExceptionDate and minExceptionTime
	 * 
	 * @param exceptionClass class name of the exception
	 * @param minExceptionDate date since which exceptions thrown
	 * @param minExceptionTime time since which exceptions thrown
	 * @param start starting from the "start" record
	 * @param length retrieve the next "length" records from database
	 * @return list of exception logs
	 */
	public List<ExceptionLogListItem> getExceptionLogs(String exceptionClass, String minExceptionDate,
	                                                   String minExceptionTime, Integer start, Integer length) {
		if (StringUtils.isBlank(exceptionClass)) {
			exceptionClass = null;
		} else {
			exceptionClass = exceptionClass.trim();
		}
		Date minExceptionDateTime = getDateTime(minExceptionDate, minExceptionTime);
		List<ExceptionLog> exceptionLogs = exceptionLogService.getExceptionLogs(exceptionClass, minExceptionDateTime, start,
		    length);
		List<ExceptionLogListItem> exceptionLogItems = new Vector<ExceptionLogListItem>();
		for (ExceptionLog exLog : exceptionLogs) {
			exceptionLogItems.add(new ExceptionLogListItem(exLog));
		}
		return exceptionLogItems;
	}
	
	/**
	 * Get the number of exception logs matching a search class name, the minExceptionDate and the minExceptionTime
	 * 
	 * @param exceptionClass class name of the exception
	 * @param minExceptionDate date since which exceptions thrown
	 * @param minExceptionTime time since which exceptions thrown
	 * @return number of exception logs
	 */
	public Integer getCountOfExceptionLogs(String exceptionClass, String minExceptionDate, String minExceptionTime) {
		if (StringUtils.isBlank(exceptionClass)) {
			exceptionClass = null;
		} else {
			exceptionClass = exceptionClass.trim();
		}
		Date minExceptionDateTime = getDateTime(minExceptionDate, minExceptionTime);
		return exceptionLogService.getCountOfExceptionLogs(exceptionClass, minExceptionDateTime);
	}
	
	/**
	 * Get exception log detail
	 * 
	 * @param excLogId exception log id
	 * @return exception log detail
	 */
	public ExceptionLogDetailListItem getExceptionLogdetail(Integer excLogId) {
		ExceptionLog excLog = exceptionLogService.getExceptionLog(excLogId);
		if (excLog != null && excLog.getExceptionLogDetail() != null) {
			ExceptionLogDetailListItem excLogDetailItem = new ExceptionLogDetailListItem(excLog.getExceptionLogDetail());
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
	public ExceptionRootCauseListItem getExceptionRootCause(Integer excLogId) {
		ExceptionLog excLog = exceptionLogService.getExceptionLog(excLogId);
		if (excLog != null && excLog.getExceptionRootCause() != null) {
			ExceptionRootCauseListItem excLogRootCauseItem = new ExceptionRootCauseListItem(excLog.getExceptionRootCause());
			return excLogRootCauseItem;
		} else {
			return null;
		}
	}
	
	/**
	 * Save errors that have to be ignored
	 * 
	 * @param errors that have to be ignored
	 * @return true in the case of a successful saving, otherwise - false
	 */
	public boolean saveIgnoredErrors(String errors) {
		GlobalProperty glProp = Context.getAdministrationService().getGlobalPropertyObject(
		    ExceptionLogUtil.ERRROR_LOGGING_GP_IGNORED_EXCEPTION);
		if (glProp != null) {
			glProp.setPropertyValue(errors);
			GlobalProperty saved = Context.getAdministrationService().saveGlobalProperty(glProp);
			if (saved != null && saved.getPropertyValue().equals(errors)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get ignored errors
	 * 
	 * @return string which include ignored errors
	 */
	public String getIgnoredErrors() {
		GlobalProperty glProp = Context.getAdministrationService().getGlobalPropertyObject(
		    ExceptionLogUtil.ERRROR_LOGGING_GP_IGNORED_EXCEPTION);
		if (glProp != null) {
			System.out.println(glProp.getPropertyValue());
			return glProp.getPropertyValue();
		}
		return null;
	}
	
	/**
	 * Convert input string arguments to Date
	 * 
	 * @param minExceptionDate date string
	 * @param minExceptionTime time string
	 * @return converted date
	 */
	private Date getDateTime(String minExceptionDate, String minExceptionTime) {
		Date minExceptionDateTime = null;
		if (StringUtils.isNotBlank(minExceptionDate)) {
			minExceptionDate = minExceptionDate.trim();
			if (StringUtils.isNotBlank(minExceptionTime)) {
				minExceptionTime = minExceptionTime.trim();
			} else {
				minExceptionTime = "00:00:00";
			}
			String dateTime = minExceptionDate + " " + minExceptionTime;
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			try {
				minExceptionDateTime = (Date) format.parse(dateTime);
			}
			catch (ParseException ex) {
				log.error("Cannot parse date", ex);
			}
		}
		return minExceptionDateTime;
	}
}
