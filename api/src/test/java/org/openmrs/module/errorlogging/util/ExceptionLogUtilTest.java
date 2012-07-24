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

import static org.junit.Assert.*;
import org.junit.Test;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Contains test methods for {@link ExceptionLogUtil}.
 */
public class ExceptionLogUtilTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void parseException_shouldCreateNewObjectWhenExceptionThrown() {
		ExceptionsGenerator excGenerator = new ExceptionsGenerator();
		ExceptionLog excLog = null;
		try {
			excGenerator.throwException();
		}
		catch (Exception exception) {
			excLog = ExceptionLogUtil.parseException(exception);
		}
		assertNotNull(excLog);
	}
	
	@Test
	public void parseException_shouldCreateExceptionLogDetailWithInnerClassName() {
		ExceptionsGenerator.InnerClass innerClass = new ExceptionsGenerator().new InnerClass();
		ExceptionLog excLog = null;
		try {
			innerClass.throwExceptionInner();
		}
		catch (Exception exception) {
			excLog = ExceptionLogUtil.parseException(exception);
		}
		assertNotNull(excLog);
		
		ExceptionLogDetail excLogDetail = excLog.getExceptionLogDetail();
		assertNotNull(excLogDetail);
		assertEquals(innerClass.getClass().getName(), excLogDetail.getClassName());
	}
	
	@Test
	public void parseException_shouldCreateExceptionLogWithDetailsAndRootCauseOfException() {
		ExceptionsGenerator excGenerator = new ExceptionsGenerator();
		ExceptionLog excLog = null;
		try {
			excGenerator.throwException();
		}
		catch (Exception exception) {
			excLog = ExceptionLogUtil.parseException(exception);
		}
		assertNotNull(excLog);
		assertEquals(APIException.class.getName(), excLog.getExceptionClass());
		assertEquals("APIException from ExceptionsGenerator", excLog.getExceptionMessage());
		
		ExceptionLogDetail excLogDetail = excLog.getExceptionLogDetail();
		assertNotNull(excLogDetail);
		assertEquals(ExceptionsGenerator.class.getName(), excLogDetail.getClassName());
		assertEquals("throwException", excLogDetail.getMethodName());
		assertEquals(new Integer(28), excLogDetail.getLineNumber());
		
		ExceptionRootCause excRootCause = excLog.getExceptionRootCause();
		assertNotNull(excRootCause);
		assertEquals(DAOException.class.getName(), excRootCause.getExceptionClass());
		assertEquals("DAOException from ExceptionsGenerator", excRootCause.getExceptionMessage());
		
		ExceptionRootCauseDetail excRootCauseDetail = excRootCause.getExceptionRootCauseDetail();
		assertNotNull(excRootCauseDetail);
		assertEquals(ExceptionsGenerator.class.getName(), excRootCauseDetail.getClassName());
		assertEquals("throwException", excRootCauseDetail.getMethodName());
		assertEquals(new Integer(25), excRootCauseDetail.getLineNumber());
	}
	
	@Test
	public void parseIgnoredException_shouldReturnArrayOfExceptionClasses() {
		String str = "  org.openmrs.api.APIAuthenticationException, org.openmrs.api.context.ContextAuthenticationException,org.openmrs.api.db.DAOException ";
		String[] expectedResult = new String[] { "org.openmrs.api.APIAuthenticationException",
		        "org.openmrs.api.context.ContextAuthenticationException", "org.openmrs.api.db.DAOException" };
		String[] result = ExceptionLogUtil.parseIgnoredException(str);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void isIgnoredException_shouldReturnTrueIfExceptionIsIgnored() throws Exception {
		executeDataSet("moduleTestData.xml");
		APIAuthenticationException apiException = new APIAuthenticationException("DAOException");
		DAOException daoException = new DAOException("DAOException");
		assertTrue("APIAuthenticationException should be ignored as default", ExceptionLogUtil
		        .isIgnoredException(apiException));
		assertTrue("DAOException should be ignored", ExceptionLogUtil.isIgnoredException(daoException));
	}
}
