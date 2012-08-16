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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.*;
import org.openmrs.util.OpenmrsConstants;

/**
 * Contains utility methods for the module.
 */
public class ExceptionLogUtil {
	
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
		ExceptionLog excLog = new ExceptionLog(exception.getClass().getName(), exception.getMessage(),
		        OpenmrsConstants.OPENMRS_VERSION_SHORT);
		boolean isJspException = false;
		if (exception.getClass().getName().equals("javax.servlet.jsp.JspException")
		        || exception.getClass().getName().equals("org.apache.jasper.JasperException")) {
			isJspException = parseJspExceptionMessage(excLog, exception.getMessage());			
		}
		if (!isJspException) {
			StackTraceElement[] stTrElements = exception.getStackTrace();
			if (stTrElements != null && stTrElements.length > 0) {
				ExceptionLogDetail excLogDetail = new ExceptionLogDetail(stTrElements[0].getFileName(), stTrElements[0]
				        .getClassName(), stTrElements[0].getMethodName(), stTrElements[0].getLineNumber());
				excLog.setExceptionLogDetail(excLogDetail);
			}
		}
		
		Throwable rootCause = ExceptionUtils.getRootCause(exception);
		if (rootCause != null) {
			ExceptionRootCause excRootCause = new ExceptionRootCause(rootCause.getClass().getName(), rootCause.getMessage());
			excLog.setExceptionRootCause(excRootCause);
			
			StackTraceElement[] stTrRootCauseElements = rootCause.getStackTrace();
			if (stTrRootCauseElements != null && stTrRootCauseElements.length > 0) {
				ExceptionRootCauseDetail excRootCauseDetail = new ExceptionRootCauseDetail(stTrRootCauseElements[0]
				        .getFileName(), stTrRootCauseElements[0].getClassName(), stTrRootCauseElements[0].getMethodName(),
				        stTrRootCauseElements[0].getLineNumber());
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
		if (StringUtils.isNotBlank(ieString)) {
			ieString = ieString.replaceAll("\\s+", "");
			return ieString.split(",");
		}
		return null;
	}
	
	/**
	 * Try to get filename and line number of jsp exception. 
	 * If filename and line number cannot be got return false, otherwise true
	 * 
	 * @param excLog exception log
	 * @param message exception message for parsing
	 * @return true if message matches patterns, otherwise false
	 */
	private static boolean parseJspExceptionMessage(ExceptionLog excLog, String message) {
		Pattern pattern = Pattern.compile(ErrorLoggingConstants.JSP_LINE_FILE_PATTERN);
		Matcher matcher = pattern.matcher(message);
		String line = null;
		String jsp = null;
		if (matcher.find()) {
			line = matcher.group(1);
			jsp = matcher.group(2);
			
		}
		if (line != null && jsp != null) {
			Integer lineNum = Integer.valueOf(line);
			ExceptionLogDetail excLogDetail = new ExceptionLogDetail(jsp, "", "", lineNum);
			excLog.setExceptionLogDetail(excLogDetail);
			return true;
		} else {
			pattern = Pattern.compile(ErrorLoggingConstants.JSP_FILE_LINE_PATTERN);
			matcher = pattern.matcher(message);
			if (matcher.find()) {
				jsp = matcher.group(1);
				line = matcher.group(2);
				if (line != null && jsp != null) {
					Integer lineNum = Integer.valueOf(line);
					ExceptionLogDetail excLogDetail = new ExceptionLogDetail(jsp, "", "", lineNum);
					excLog.setExceptionLogDetail(excLogDetail);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if exception is in the default ignore list
	 * 
	 * @param exception exception that need to be checked
	 * @return true if exception is in the ignore list, otherwise - false
	 */
	public static boolean isDefaultIgnoredException(Exception exception) {
		String exceptionClassFull = exception.getClass().getName();
		for (String dfie : ErrorLoggingConstants.ERRROR_LOGGING_DEDAULT_IGNORED_EXCEPTION) {
			if (dfie.equals(exceptionClassFull)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if exception is in the ignore list
	 * 
	 * @param exception exception that need to be checked
	 * @return true if exception is in the ignore list, otherwise - false
	 */
	public static boolean isIgnoredException(Exception exception) {
		if (isDefaultIgnoredException(exception)) {
			return true;
		}
		String exceptionClassFull = exception.getClass().getName();
		GlobalProperty ignoredExcProp = Context.getAdministrationService().getGlobalPropertyObject(
		    ErrorLoggingConstants.ERRROR_LOGGING_GP_IGNORED_EXCEPTION);
		if (ignoredExcProp != null) {
			String ignoredExc = ignoredExcProp.getPropertyValue();
			String[] ignoredExceptions = parseIgnoredException(ignoredExc);
			if (ignoredExceptions != null && ignoredExceptions.length > 0) {
				for (String ie : ignoredExceptions) {
					if (ie.equals(exceptionClassFull)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
