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
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;

public class ExceptionLogListItem {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private Integer id;
	
	private String uuid;
	
	//For ExceptionLog and ExceptionRootCause
	private String exceptionClass;
	
	private String exceptionMessage;
	
	//For ExceptionLog
	private String openmrsVersion;
	
	private String exceptionDateTime;
	
	private String user;
	
	//For ExceptionLogDetail and ExceptionRootCauseDetail
	private String className;
	
	private String methodName;
	
	private Integer lineNumber;
	
	/**
	 * Constructor for ExceptionLog
	 * 
	 * @param exceptionLog 
	 */
	public ExceptionLogListItem(ExceptionLog exceptionLog) {
		this.id = exceptionLog.getExceptionLogId();
		this.uuid = exceptionLog.getUuid();
		this.exceptionClass = exceptionLog.getExceptionClass();
		this.exceptionMessage = exceptionLog.getExceptionMessage();
		setExceptionDateTime(exceptionLog.getExceptionDateTime());
		this.openmrsVersion = exceptionLog.getOpenmrsVersion();
		this.user = exceptionLog.getUser().getUsername();
	}
	
	/**
	 * Constructor for ExceptionRootCause
	 * 
	 * @param excRootCause 
	 */
	public ExceptionLogListItem(ExceptionRootCause excRootCause) {
		this.id = excRootCause.getExceptionRootCauseId();
		this.uuid = excRootCause.getUuid();
		this.exceptionClass = excRootCause.getExceptionClass();
		this.exceptionMessage = excRootCause.getExceptionMessage();
	}
	
	/**
	 * Constructor for ExceptionLogDetail
	 * 
	 * @param excLogDetail 
	 */
	public ExceptionLogListItem(ExceptionLogDetail excLogDetail) {
		this.id = excLogDetail.getExceptionLogDetailId();
		this.uuid = excLogDetail.getUuid();
		this.className = excLogDetail.getClassName();
		this.methodName = excLogDetail.getMethodName();
		this.lineNumber = excLogDetail.getLineNumber();
	}
	
	/**
	 * Constructor for ExceptionRootCauseDetail
	 * 
	 * @param excRootCaouseDetail 
	 */
	public ExceptionLogListItem(ExceptionRootCauseDetail excRootCaouseDetail) {
		this.id = excRootCaouseDetail.getExceptionRootCauseDetailId();
		this.uuid = excRootCaouseDetail.getUuid();
		this.className = excRootCaouseDetail.getClassName();
		this.methodName = excRootCaouseDetail.getMethodName();
		this.lineNumber = excRootCaouseDetail.getLineNumber();
	}
	
	/**
	 * @return the exceptionLogId
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	/**
	 * @return the lineNumber
	 */
	public Integer getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@Override
	public int hashCode() {
		if (getUuid() == null)
			return super.hashCode();
		return getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExceptionLogListItem)) {
			return false;
		}
		ExceptionLogListItem other = (ExceptionLogListItem) object;
		if (getUuid() == null) {
			return false;
		}
		return getUuid().equals(other.getUuid());
	}
	
	/**
	 * @param exceptionDateTime the exceptionDateTime to set
	 */
	public void setExceptionDateTime(Date exceptionDateTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(exceptionDateTime);
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		day = addZero(day);
		String month = Integer.toString(c.get(Calendar.MONTH) + 1);
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
}
