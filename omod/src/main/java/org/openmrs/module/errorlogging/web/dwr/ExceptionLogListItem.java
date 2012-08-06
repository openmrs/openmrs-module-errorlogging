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
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;

public class ExceptionLogListItem {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private Integer id;
	
	private String uuid;
	
	//For ExceptionRootCause
	private String exceptionClass;
	
	private String exceptionMessage;
	
	private boolean hasRootCauseDetail;
	
	//For ExceptionLogDetail and ExceptionRootCauseDetail
	
	private String fileName;
	
	private String className;
	
	private String methodName;
	
	private Integer lineNumber;
	
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
		if (excRootCause.getExceptionRootCauseDetail() != null) {
			hasRootCauseDetail = true;
		}
	}
	
	/**
	 * Constructor for ExceptionLogDetail
	 * 
	 * @param excLogDetail 
	 */
	public ExceptionLogListItem(ExceptionLogDetail excLogDetail) {
		this.id = excLogDetail.getExceptionLogDetailId();
		this.uuid = excLogDetail.getUuid();
		this.fileName = excLogDetail.getFileName();
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
		this.fileName = excRootCaouseDetail.getFileName();
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	/**
	 * @return the hasRootCauseDetail
	 */
	public boolean isHasRootCauseDetail() {
		return hasRootCauseDetail;
	}
	
	/**
	 * @param hasRootCauseDetail the hasRootCauseDetail to set
	 */
	public void setHasRootCauseDetail(boolean hasRootCauseDetail) {
		this.hasRootCauseDetail = hasRootCauseDetail;
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
}
