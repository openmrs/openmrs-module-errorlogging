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
import java.util.Date;
import org.openmrs.Auditable;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class ExceptionLog extends BaseOpenmrsObject implements Auditable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer exceptionLogId;
	
	private String exceptionClass;
	
	private String exceptionMessage;
	
	private String openmrsVersion;
	
	private User creator;
	
	private Date dateCreated;
	
	private User changedBy;
	
	private Date dateChanged;
	
	private ExceptionLogDetail exceptionLogDetail;
	
	private ExceptionRootCause exceptionRootCause;
	
	/**
	 * Default constructor without parameters
	 */
	public ExceptionLog() {
	}
	
	/**
	 * Convenience constructor with parameters
	 * 
	 * @param exceptionClass exception class
	 * @param exceptionMessage exception message
	 * @param openmrsVersion version of OpenMRS
	 */
	public ExceptionLog(String exceptionClass, String exceptionMessage, String openmrsVersion) {
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
		this.openmrsVersion = openmrsVersion;
	}
	
	@Override
	public Integer getId() {
		return getExceptionLogId();
	}
	
	@Override
	public void setId(Integer id) {
		this.setExceptionLogId(id);
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
	 * @return the exceptionLogDetail
	 */
	public ExceptionLogDetail getExceptionLogDetail() {
		return exceptionLogDetail;
	}
	
	/**
	 * @param exceptionLogDetail the exceptionLogDetail to set
	 */
	public void setExceptionLogDetail(ExceptionLogDetail exceptionLogDetail) {
		this.exceptionLogDetail = exceptionLogDetail;
		if (exceptionLogDetail != null) {
			exceptionLogDetail.setExceptionLog(this);
		}
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
		if (exceptionRootCause != null) {
			exceptionRootCause.setExceptionLog(this);
		}
	}
	
	/**
	 * @return the exceptionDateTime
	 */
	public Date getExceptionDateTime() {
		return getDateCreated();
	}
	
	/**
	 * @param exceptionDateTime the exceptionDateTime to set
	 */
	public void setExceptionDateTime(Date exceptionDateTime) {
		this.setDateCreated(exceptionDateTime);
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return getCreator();
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.setCreator(user);
	}
	
	/**
	 * @return the creator
	 */
	@Override
	public User getCreator() {
		return creator;
	}
	
	/**
	 * @param creator the creator to set
	 */
	@Override
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	/**
	 * @return the dateCreated
	 */
	@Override
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @param dateCreated dateCreated to set
	 */
	@Override
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	/**
	 * @return the changedBy
	 */
	@Override
	public User getChangedBy() {
		return changedBy;
	}
	
	/**
	 * @param changedBy the changedBy to set
	 */
	@Override
	public void setChangedBy(User changedBy) {
		this.changedBy = changedBy;
	}
	
	/**
	 * @return the dateChanged
	 */
	@Override
	public Date getDateChanged() {
		return dateChanged;
	}
	
	/**
	 * @param dateChanged the dateChanged to set
	 */
	@Override
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	
	@Override
	public int hashCode() {
		if (getUuid() == null)
			return super.hashCode();
		return getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExceptionLog)) {
			return false;
		}
		ExceptionLog other = (ExceptionLog) object;
		if (getUuid() == null) {
			return false;
		}
		return getUuid().equals(other.getUuid());
	}
	
	@Override
	public String toString() {
		return "ExceptionLog[ exceptionLogId=" + exceptionLogId + "; exceptionClass=" + exceptionClass
		        + "; exceptionMessage=" + exceptionMessage + "]";
	}
}
