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
package org.openmrs.module.errorlogging.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;
import org.openmrs.util.OpenmrsConstants;

/**
 * Contains utility methods for the module.
 */
public class ExceptionLogUtil {
	
	public static final String ERRROR_LOGGING_GP_IGNORED_EXCEPTION = "errorlogging.ignore.errors";
	
	/**
	 * Gets information about exception and fills {@link ExceptionLog}  and {@link ExceptionLogDetail}.
	 * If exception has a root cause fills {@link ExceptionLogRootCause} and {@link ExceptionLogRootCauseDetail}.
	 * 
	 * @param exception exception for parsing
	 * @return exception log
	 */
	public static ExceptionLog parseException(Exception exception) {
		if (exception == null) {
			return null;
		}
		ExceptionLog excLog = new ExceptionLog();
		excLog.setExceptionClass(exception.getClass().getName());
		excLog.setExceptionMessage(exception.getMessage());
		excLog.setOpenmrsVersion(OpenmrsConstants.OPENMRS_VERSION_SHORT);
		
		StackTraceElement[] stTrElements = exception.getStackTrace();
		if (stTrElements != null) {
			ExceptionLogDetail excLogDetail = new ExceptionLogDetail();
			excLogDetail.setClassName(stTrElements[0].getClassName());
			excLogDetail.setMethodName(stTrElements[0].getMethodName());
			excLogDetail.setLineNumber(stTrElements[0].getLineNumber());
			excLogDetail.setExceptionLog(excLog);
			excLog.setExceptionLogDetail(excLogDetail);
		}
		
		Throwable rootCause = ExceptionUtils.getRootCause(exception);
		if (rootCause != null) {
			ExceptionRootCause excRootCause = new ExceptionRootCause();
			excRootCause.setExceptionClass(rootCause.getClass().getName());
			excRootCause.setExceptionMessage(rootCause.getMessage());
			excRootCause.setExceptionLog(excLog);
			excLog.setExceptionRootCause(excRootCause);
			
			StackTraceElement[] stTrRootCauseElements = rootCause.getStackTrace();
			if (stTrRootCauseElements != null) {
				ExceptionRootCauseDetail excRootCauseDetail = new ExceptionRootCauseDetail();
				excRootCauseDetail.setClassName(stTrRootCauseElements[0].getClassName());
				excRootCauseDetail.setMethodName(stTrRootCauseElements[0].getMethodName());
				excRootCauseDetail.setLineNumber(stTrRootCauseElements[0].getLineNumber());
				excRootCauseDetail.setExceptionRootCause(excRootCause);
				excRootCause.setExceptionRootCauseDetail(excRootCauseDetail);
			}
		}
		return excLog;
	}
	
	/**
	 * This method converts the given Long value to an Integer. If the Long value will not fit in an
	 * Integer an exception is thrown
	 * 
	 * @param longValue the value to convert
	 * @return the long value in integer form.
	 * @throws IllegalArgumentException if the long value does not fit into an integer
	 */
	public static Integer convertToInteger(Long longValue) {
		if (longValue < Integer.MIN_VALUE || longValue > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(longValue + " cannot be cast to Integer without changing its value.");
		}
		return longValue.intValue();
	}
	
	/**
	 * Retrieves an array of ignored exceptions from input string
	 * 
	 * @param ieString a string that includes ignored exceptions separated by a comma
	 * @return an array of ignored exceptions
	 */
	public static String[] parseIgnoredException(String ieString) {
		ieString = ieString.replaceAll("\\s+", "");
		if (ieString != null && !ieString.equals("")) {
			return ieString.split(",");
		} else {
			return null;
		}
	}
	
	/**
	 * Check if exception is in the ignore list
	 * 
	 * @param exception exception that need to be checked
	 * @return true if exception is in the ignore list, otherwise - false
	 */
	public static boolean isIgnoredException(Exception exception) {
		String exceptionClassFull = exception.getClass().getName();
		String exceptionClassSimple = exception.getClass().getSimpleName();
		GlobalProperty ignoredExcProp = Context.getAdministrationService().getGlobalPropertyObject(
		    ERRROR_LOGGING_GP_IGNORED_EXCEPTION);
		if (ignoredExcProp != null) {
			String ignoredExc = ignoredExcProp.getPropertyValue();
			String[] ignoredExceptions = parseIgnoredException(ignoredExc);
			if (ignoredExceptions != null && ignoredExceptions.length > 0) {
				for (String ie : ignoredExceptions) {
					if (ie.equals(exceptionClassSimple) || ie.equals(exceptionClassFull)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}