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
package org.openmrs.module.errorlogging.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.util.OpenmrsConstants;

/**
 * Tests {@link ${ExceptionLogService}}.
 */
public class ExceptionLogServiceTest extends BaseModuleContextSensitiveTest {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private ExceptionLogService service;
	
	@Before
	public void shouldSetupContext() throws Exception {
		executeDataSet("moduleTestData.xml");
		service = Context.getService(ExceptionLogService.class);
	}
	
	/**
	 * @see {@link ExceptionLogService#saveExceptionLog(ExceptionLog)}
	 */
	@Test
	public void saveExceptionLog_shouldCreateNewObjectWhenExceptionLogIdIsNull() {
		User user = Context.getAuthenticatedUser();
		ExceptionLog excLog = new ExceptionLog();
		excLog.setExceptionDateTime(new Date());
		excLog.setExceptionClass("Exc Test Class");
		excLog.setOpenmrsVersion(OpenmrsConstants.OPENMRS_VERSION_SHORT);
		excLog.setExceptionMessage("Exc Test Message");
		excLog.setUuid("mf28662u-i183-4mw5-a15a-8gr9d5234r13");
		excLog.setUser(user);
		assertNull(excLog.getExceptionLogId());
		service.saveExceptionLog(excLog);
		assertNotNull(excLog.getExceptionLogId());
	}
	
	/**
	 * @see {@link ExceptionLogService#saveExceptionLog(ExceptionLog)}
	 */
	@Test
	public void saveExceptionLog_shouldUpdateExistingObjectWhenExceptionLogIdIsNotNull() {
		String excClass = "Exception Test Class123";
		ExceptionLog excLogSaved = service.getExceptionLog(1);
		assertNotNull(excLogSaved.getExceptionLogId());
		assertEquals(excLogSaved.getExceptionClass(), "Exception Log Class");
		excLogSaved.setExceptionClass(excClass);
		ExceptionLog excLogUpdated = service.saveExceptionLog(excLogSaved);
		assertEquals(excClass, excLogUpdated.getExceptionClass());
	}
	
	/**
	 * @see {@link ExceptionLogService#getExceptionLogs(String, Date, Integer, Integer)}
	 */
	@Test
	public void getExceptionLogs_shouldReturnAllExceptionLogsByClassAndDateTime() {
		List<ExceptionLog> excLogList = service.getExceptionLogs(null, null, 0, 10);
		assertEquals(excLogList.size(), 3);
		
		excLogList = service.getExceptionLogs("Exception Log Class2", null, 0, 10);
		assertEquals(excLogList.size(), 2);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = (Date) format.parse("2012-13-06 22:12:00.0");
		}
		catch (ParseException ex) {
			log.error("Cannot parse date", ex);
		}
		
		excLogList = service.getExceptionLogs(null, date, 0, 10);
		assertEquals(excLogList.size(), 2);
		
		excLogList = service.getExceptionLogs("Exception Log Class2", date, 0, 10);
		assertEquals(excLogList.size(), 2);
	}
	
	/**
	 * @see {@link ExceptionLogService#purgeExceptionLog())}
	 */
	@Test
	public void purgeExceptionLog_shouldDeleteExceptionLog() {
		ExceptionLog excLog = service.getExceptionLog(1);
		assertNotNull(excLog);
		service.purgeExceptionLog(excLog);
		assertNull(service.getExceptionLog(1));
	}
}
