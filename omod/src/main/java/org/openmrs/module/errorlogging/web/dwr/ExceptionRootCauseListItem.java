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

import org.openmrs.module.errorlogging.ExceptionRootCause;

public class ExceptionRootCauseListItem {
	
	private Integer exceptionRootCauseId;
	
	private String exceptionClass;
	
	private String exceptionMessage;
	
	public ExceptionRootCauseListItem(ExceptionRootCause excRootCause) {
		this.exceptionRootCauseId = excRootCause.getExceptionRootCauseId();
		this.exceptionClass = excRootCause.getExceptionClass();
		this.exceptionMessage = excRootCause.getExceptionMessage();
	}
	
	/**
	 * @return the exceptionRootCauseId
	 */
	public Integer getExceptionRootCauseId() {
		return exceptionRootCauseId;
	}
	
	/**
	 * @param exceptionRootCauseId the exceptionRootCauseId to set
	 */
	public void setExceptionRootCauseId(Integer exceptionRootCauseId) {
		this.exceptionRootCauseId = exceptionRootCauseId;
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
}
