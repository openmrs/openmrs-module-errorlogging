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

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.errorlogging.ExceptionLog;

public class ExceptionLogListItem {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private Integer exceptionLogId;
	
	private String exceptionClass;
	
	private String exceptionMessage;
	
	private String openmrsVersion;
	
	private String exceptionDateTime;
	
	private String user;
	
	public ExceptionLogListItem(ExceptionLog exceptionLog) {
		this.exceptionLogId = exceptionLog.getId();
		this.exceptionClass = exceptionLog.getExceptionClass();
		this.exceptionMessage = exceptionLog.getExceptionMessage();
		setExceptionDateTime(exceptionLog.getExceptionDateTime());
		this.openmrsVersion = exceptionLog.getOpenmrsVersion();
		this.user = exceptionLog.getUser().getUsername();
	}
	
	/**
	 * @return the exceptionLogId
	 */
	public Integer getExceptionLogId() {
		return exceptionLogId;
	}
	
	/**
	 * @param exceptionLogId the exceptionLogId to set
	 */
	public void setExceptionLogId(Integer exceptionLogId) {
		this.exceptionLogId = exceptionLogId;
	}
	
	/**
	 * @return the exceptionClass
	 */
	public String getExceptionClass() {
		return exceptionClass;
	}
	
	/**
	 * @param exceptionClass the exceptionClass to set
	 */
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	
	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	/**
	 * @return the openmrsVersion
	 */
	public String getOpenmrsVersion() {
		return openmrsVersion;
	}
	
	/**
	 * @param openmrsVersion the openmrsVersion to set
	 */
	public void setOpenmrsVersion(String openmrsVersion) {
		this.openmrsVersion = openmrsVersion;
	}
	
	/**
	 * @return the exceptionDateTime
	 */
	public String getExceptionDateTime() {
		return exceptionDateTime;
	}
	
	/**
	 * @param exceptionDateTime the exceptionDateTime to set
	 */
	public void setExceptionDateTime(Date exceptionDateTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(exceptionDateTime);
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		day = addZero(day);
		String month = Integer.toString(c.get(Calendar.MONTH));
		month = addZero(month);
		String year = Integer.toString(c.get(Calendar.YEAR));
		year = addZero(year);
		String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
		hour = addZero(hour);
		String minute = Integer.toString(c.get(Calendar.MINUTE));
		minute = addZero(minute);
		String second = Integer.toString(c.get(Calendar.SECOND));
		second = addZero(second);
		
		this.exceptionDateTime = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second;
	}
	
	/**
	 * 
	 * @param value
	 * @return 
	 */
	private String addZero(String value) {
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
}
