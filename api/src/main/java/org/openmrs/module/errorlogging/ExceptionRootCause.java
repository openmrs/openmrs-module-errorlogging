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
import java.util.UUID;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class ExceptionRootCause extends BaseOpenmrsObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer exceptionRootCauseId;
	
	private String exceptionClass;
	
	private String exceptionMessage;
	
	private ExceptionLog exceptionLog;
	
	private ExceptionRootCauseDetail exceptionRootCauseDetail;
	
	/**
	 * Default constructor without parameters
	 */
	public ExceptionRootCause() {
		if (getUuid() == null) {
			setUuid(UUID.randomUUID().toString());
		}
	}
	
	/**
	 * Convenience constructor with parameters
	 * 
	 * @param exceptionClass exception class
	 * @param exceptionMessage exception message
	 */
	public ExceptionRootCause(String exceptionClass, String exceptionMessage) {
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
		if (getUuid() == null) {
			setUuid(UUID.randomUUID().toString());
		}
	}
	
	@Override
	public Integer getId() {
		return getExceptionRootCauseId();
	}
	
	@Override
	public void setId(Integer id) {
		setExceptionRootCauseId(id);
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
	
	/**
	 * @return the exceptionLog
	 */
	public ExceptionLog getExceptionLog() {
		return exceptionLog;
	}
	
	/**
	 * @param exceptionLog the exceptionLog to set
	 */
	public void setExceptionLog(ExceptionLog exceptionLog) {
		this.exceptionLog = exceptionLog;
	}
	
	/**
	 * @return the exceptionRootCauseDetail
	 */
	public ExceptionRootCauseDetail getExceptionRootCauseDetail() {
		return exceptionRootCauseDetail;
	}
	
	/**
	 * @param exceptionRootCauseDetail the exceptionRootCauseDetail to set
	 */
	public void setExceptionRootCauseDetail(ExceptionRootCauseDetail exceptionRootCauseDetail) {
		this.exceptionRootCauseDetail = exceptionRootCauseDetail;
		if (exceptionRootCauseDetail != null) {
			exceptionRootCauseDetail.setExceptionRootCause(this);
		}
	}
	
	@Override
	public int hashCode() {
		if (getUuid() == null)
			return super.hashCode();
		return getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExceptionRootCause)) {
			return false;
		}
		ExceptionRootCause other = (ExceptionRootCause) object;
		if (getUuid() == null) {
			return false;
		}
		return getUuid().equals(other.getUuid());
	}
	
	@Override
	public String toString() {
		return "ExceptionRootCause[ exceptionRootCauseId=" + exceptionRootCauseId + "; exceptionClass=" + exceptionClass
		        + "exceptionMessage=" + exceptionMessage + " ]";
	}
}
