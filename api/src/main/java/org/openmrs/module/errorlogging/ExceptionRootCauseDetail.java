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
package org.openmrs.module.errorlogging;

import java.io.Serializable;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class ExceptionRootCauseDetail extends BaseOpenmrsObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer exceptionRootCauseDetailId;
	
	private String fileName;
	
	private String className;
	
	private String methodName;
	
	private Integer lineNumber;
	
	private ExceptionRootCause exceptionRootCause;
	
	/**
	 * Default constructor without parameters
	 */
	public ExceptionRootCauseDetail() {
	}
	
	/**
	 * Convenience constructor with parameters
	 * 
	 * @param className name of class which thrown an exception
	 * @param methodName name of method which thrown an exception
	 * @param lineNumber number of line where exception was thrown
	 */
	public ExceptionRootCauseDetail(String fileName, String className, String methodName, Integer lineNumber) {
		this.fileName = fileName;
		this.className = className;
		this.methodName = methodName;
		this.lineNumber = lineNumber;
	}
	
	@Override
	public Integer getId() {
		return getExceptionRootCauseDetailId();
	}
	
	@Override
	public void setId(Integer id) {
		setExceptionRootCauseDetailId(id);
	}
	
	/**
	 * @return the exceptionRootCauseDetailId
	 */
	public Integer getExceptionRootCauseDetailId() {
		return exceptionRootCauseDetailId;
	}
	
	/**
	 * @param exceptionRootCauseDetailId the exceptionRootCauseDetailId to set
	 */
	public void setExceptionRootCauseDetailId(Integer exceptionRootCauseDetailId) {
		this.exceptionRootCauseDetailId = exceptionRootCauseDetailId;
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
	 * @return the exceptionRootCause
	 */
	public ExceptionRootCause getExceptionRootCause() {
		return exceptionRootCause;
	}
	
	/**
	 * @param exceptionRootCause the exceptionRootCause to set
	 */
	public void setExceptionRootCause(ExceptionRootCause exceptionRootCause) {
		this.exceptionRootCause = exceptionRootCause;
	}
	
	@Override
	public int hashCode() {
		if (getUuid() == null)
			return super.hashCode();
		return getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExceptionRootCauseDetail)) {
			return false;
		}
		ExceptionRootCauseDetail other = (ExceptionRootCauseDetail) object;
		if (getUuid() == null) {
			return false;
		}
		return getUuid().equals(other.getUuid());
	}
	
	@Override
	public String toString() {
		return "ExceptionRootCauseDetail[ exceptionRootCauseDetailId=" + exceptionRootCauseDetailId + "; className="
		        + className + "; methodName=" + methodName + "; lineNumber" + lineNumber + " ]";
	}
}
