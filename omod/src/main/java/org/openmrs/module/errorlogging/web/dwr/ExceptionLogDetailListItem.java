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

import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;

public class ExceptionLogDetailListItem {
	
	private Integer exceptionLogDetailId;
	
	private String className;
	
	private String methodName;
	
	private Integer lineNumber;
	
	public ExceptionLogDetailListItem(ExceptionLogDetail excLogDetail) {
		this.className = excLogDetail.getClassName();
		this.methodName = excLogDetail.getMethodName();
		this.lineNumber = excLogDetail.getLineNumber();
	}
	
	public ExceptionLogDetailListItem(ExceptionRootCauseDetail excRootCaouseDetail) {
		this.className = excRootCaouseDetail.getClassName();
		this.methodName = excRootCaouseDetail.getMethodName();
		this.lineNumber = excRootCaouseDetail.getLineNumber();
	}
	
	/**
	 * @return the exceptionLogDetailId
	 */
	public Integer getExceptionLogDetailId() {
		return exceptionLogDetailId;
	}
	
	/**
	 * @param exceptionLogDetailId the exceptionLogDetailId to set
	 */
	public void setExceptionLogDetailId(Integer exceptionLogDetailId) {
		this.exceptionLogDetailId = exceptionLogDetailId;
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
}
