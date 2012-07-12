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
			excGenerator.alpha();
		}
		catch (Exception exception) {
			excLog = ExceptionLogUtil.parseException(exception);
		}
		assertNotNull(excLog);
	}
	
	@Test
	public void parseException_shouldCreateExceptionLogDetailWithInnerClassName() {
		ExceptionsGenerator.InnerClass innerClass = new ExceptionsGenerator().new InnerClass();
		try {
			innerClass.alphaInner();
		}
		catch (Exception exception) {
			ExceptionLog excLog = ExceptionLogUtil.parseException(exception);
			assertNotNull(excLog);
			
			ExceptionLogDetail excLogDetail = excLog.getExceptionLogDetail();
			assertNotNull(excLogDetail);
			assertEquals(innerClass.getClass().getName(), excLogDetail.getClassName());
		}
	}
	
	@Test
	public void parseException_shouldCreateExceptionLogWithDetailsAndRootCauseOfException() {
		ExceptionsGenerator excGenerator = new ExceptionsGenerator();
		ExceptionLog excLog = null;
		try {
			excGenerator.alpha();
		}
		catch (Exception exception) {
			excLog = ExceptionLogUtil.parseException(exception);
		}
		assertNotNull(excLog);
		assertEquals(APIException.class.getName(), excLog.getExceptionClass());
		assertEquals("Sorry, try again later", excLog.getExceptionMessage());
		
		ExceptionLogDetail excLogDetail = excLog.getExceptionLogDetail();
		assertNotNull(excLogDetail);
		assertEquals(ExceptionsGenerator.class.getName(), excLogDetail.getClassName());
		assertEquals("gamma", excLogDetail.getMethodName());
		assertEquals(new Integer(38), excLogDetail.getLineNumber());
		
		ExceptionRootCause excRootCause = excLog.getExceptionRootCause();
		assertNotNull(excRootCause);
		assertEquals(DAOException.class.getName(), excRootCause.getExceptionClass());
		assertEquals("Omega server not available", excRootCause.getExceptionMessage());
		
		ExceptionRootCauseDetail excRootCauseDetail = excRootCause.getExceptionRootCauseDetail();
		assertNotNull(excRootCauseDetail);
		assertEquals(GeneratorDAO.class.getName(), excRootCauseDetail.getClassName());
		assertEquals("iota", excRootCauseDetail.getMethodName());
		assertEquals(new Integer(89), excRootCauseDetail.getLineNumber());
	}
	
	@Test
	public void parseIgnoredException_shouldReturnArrayOfExceptionClasses() {
		String str = "  org.openmrs.api.APIAuthenticationException, ContextAuthenticationException,DAOException ";
		String[] expectedResult = new String[] { "org.openmrs.api.APIAuthenticationException",
		        "ContextAuthenticationException", "DAOException" };
		String[] result = ExceptionLogUtil.parseIgnoredException(str);
		assertArrayEquals(expectedResult, result);
	}
	
	@Test
	public void isIgnoredException_shouldReturnTrueIfExceptionIsIgnored() throws Exception {
		executeDataSet("moduleTestData.xml");
		APIAuthenticationException exception = new APIAuthenticationException("APIAuthenticationException");
		assertTrue("APIAuthenticationException should be ignored", ExceptionLogUtil.isIgnoredException(exception));
	}
}
