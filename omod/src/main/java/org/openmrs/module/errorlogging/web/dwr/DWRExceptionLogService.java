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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
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
}
