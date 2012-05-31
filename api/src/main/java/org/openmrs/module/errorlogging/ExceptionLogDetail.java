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
public class ExceptionLogDetail extends BaseOpenmrsObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer exceptionLogDetailId;
    private String fileName;
    private String className;
    private String methodName;
    private Integer lineNumber;

    @Override
    public Integer getId() {
        return getExceptionLogDetailId();
    }

    @Override
    public void setId(Integer id) {
        setExceptionLogDetailId(id);
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
}