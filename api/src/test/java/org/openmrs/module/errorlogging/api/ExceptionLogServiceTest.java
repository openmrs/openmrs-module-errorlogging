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
		ExceptionLog excLog = new ExceptionLog();
		excLog.setExceptionDateTime(new Date());
		excLog.setExceptionClass("Exc Test Class");
		excLog.setOpenmrsVersion(OpenmrsConstants.OPENMRS_VERSION_SHORT);
		excLog.setExceptionMessage("Exc Test Message");
		excLog.setUuid("mf28662u-i183-4mw5-a15a-8gr9d5234r13");
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
	 * @see {@link ExceptionLogService#getExceptionLogs(String, Date, Date, Integer, Integer)}
	 */
	@Test
	public void getExceptionLogs_shouldReturnAllExceptionLogsByClassAndDateTime() {
		List<ExceptionLog> excLogList = service.getExceptionLogs(null, null, null, 0, 10);
		assertEquals("Should return all exception logs since 0 to 9", excLogList.size(), 10);
		
		excLogList = service.getExceptionLogs(null, null, null, 10, 10);
		assertEquals("Should return all exception logs since 10 to 19", excLogList.size(), 1);
		
		excLogList = service.getExceptionLogs("Exception Log Class2", null, null, 0, 10);
		assertEquals("Should all exeption logs with exceptionClass \"Exception Log Class2\"", excLogList.size(), 3);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = (Date) format.parse("2012-15-06 19:10:11.0");
			endDate = (Date) format.parse("2012-24-06 10:14:29.0");
		}
		catch (ParseException ex) {
			log.error("Cannot parse date", ex);
		}
		
		excLogList = service.getExceptionLogs(null, startDate, null, 0, 10);
		assertEquals("Should return all exception logs since date 2012-15-06 19:10:11", excLogList.size(), 8);
		
		excLogList = service.getExceptionLogs(null, startDate, endDate, 0, 10);
		assertEquals("Should return all exception logs since 2012-15-06 19:10:11 to 2012-24-06 10:14:29", excLogList.size(),
		    7);
		
		excLogList = service.getExceptionLogs("Exception Log Class3", startDate, null, 0, 10);
		assertEquals("Should return all exception logs with exceptionClass \"Exception Log Class3\" "
		        + "and since date 2012-15-06 19:10:11", excLogList.size(), 2);
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
	
	/**
	 * @see {@link ExceptionLogService#getCountOfExceptionLogs(String, Date))}
	 */
	@Test
	public void getCountOfExceptionLogs_shouldReturnCountOfExceptionLogsByClassAndDateTime() {
		int count = service.getCountOfExceptionLogs(null, null, null);
		assertEquals("Should return count of all exception logs", 11, count);
		
		count = service.getCountOfExceptionLogs("Exception Log Class2", null, null);
		assertEquals("Should return count of all exception logs with with exceptionClass \"Exception Log Class2\"", 3, count);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = (Date) format.parse("2012-16-06 21:11:02.0");
			endDate = (Date) format.parse("2012-24-06 10:14:29.0");
		}
		catch (ParseException ex) {
			log.error("Cannot parse date", ex);
		}
		
		count = service.getCountOfExceptionLogs(null, startDate, null);
		assertEquals("Should return count of all exception logs since date 2012-16-06 21:11:02", 6, count);
		
		count = service.getCountOfExceptionLogs(null, startDate, endDate);
		assertEquals("Should return count of all exception logs since 2012-16-06 21:11:02 to 2012-24-06 10:14:29", 5, count);
		
		count = service.getCountOfExceptionLogs("Exception Log Class3", startDate, null);
		assertEquals("Should return count of all exception logs with with exceptionClass \"Exception Log Class3\" "
		        + "and since date 2012-16-06 21:11:02.0", 1, count);
	}
}
