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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.ExceptionRootCause;
import org.openmrs.module.errorlogging.ExceptionRootCauseDetail;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.Verifies;

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
	@Verifies(value = "should create new object when exceptionLog id is null", method = "saveExceptionLog(ExceptionLog)")
	public void saveExceptionLog_shouldCreateNewObjectWhenExceptionLogIdIsNull() {
		User user = Context.getAuthenticatedUser();
		ExceptionLog excLog = new ExceptionLog();
		excLog.setDateThrown(new Date());
		excLog.setExceptionClass("Exc Test Class");
		excLog.setExceptionMessage("Exc Test Message");
		excLog.setUuid("mf28662u-i183-4mw5-a15a-8gr9d5234r13");
		excLog.setExceptionThrownBy(user);
		assertNull(excLog.getExceptionLogId());
		service.saveExceptionLog(excLog);
		assertNotNull(excLog.getExceptionLogId());
	}
	
	/**
	 * @see {@link ExceptionLogService#saveExceptionLog(ExceptionLog)}
	 */
	@Test
	@Verifies(value = "should update existing object when exceptionLog id is not null", method = "saveExceptionLog(ExceptionLog)")
	public void saveExceptionLog_shouldUpdateExistingObjectWhenExceptionLogIdIsNotNull() {
		String excUuid = "1f28g62t-q173-test-v70q-9gi9d5034r13";
		ExceptionLog excLogSaved = service.getExceptionLog(1);
		assertNotNull(excLogSaved.getExceptionLogId());
		excLogSaved.setUuid(excUuid);
		ExceptionLog excLogUpdated = service.saveExceptionLog(excLogSaved);
		assertEquals(excUuid, excLogUpdated.getUuid());
	}
	
	/**
	 * @see {@link ExceptionLogService#saveExceptionLog(ExceptionLog)}
	 */
	@Test
	@Verifies(value = "should save cascade ExceptionLog->(ExceptionLogDetail & ExceptionRootCause->ExceptionRootCauseDetail)", method = "saveExceptionLog(ExceptionLog)")
	public void saveExceptionLog_shouldSaveCascade() {
		ExceptionLog excLog = new ExceptionLog();
		excLog.setDateThrown(new Date());
		excLog.setExceptionClass("Exception Test Class");
		excLog.setExceptionMessage("Exception Test Message");
		excLog.setUuid("mf28662u-i183-4mw5-a15a-8gr9d5234r13");
		excLog.setExceptionThrownBy(Context.getAuthenticatedUser());
		assertNull(excLog.getId());
		
		ExceptionLogDetail excLogDetail = new ExceptionLogDetail();
		excLogDetail.setClassName("Exception Log Detail Test Class Name");
		excLogDetail.setFileName("Exception Log Detail Test File Name");
		excLogDetail.setMethodName("Exception Log Detail Test Method Name");
		excLogDetail.setLineNumber(17);
		excLogDetail.setExceptionLog(excLog);
		excLog.setExceptionLogDetail(excLogDetail);
		excLogDetail.setUuid("pf18962u-o083-1pv1-a17a-1yr9d5304r13");
		assertNull(excLogDetail.getId());
		
		ExceptionRootCause excRootCase = new ExceptionRootCause();
		excRootCase.setExceptionClass("Exception Root Cause Test Class");
		excRootCase.setExceptionMessage("Exception Root Cause Test Message");
		excRootCase.setExceptionLog(excLog);
		excLog.setExceptionRootCause(excRootCase);
		excRootCase.setUuid("op15962a-i034-1ww1-a17w-1or8d9301r05");
		assertNull(excRootCase.getId());
		
		ExceptionRootCauseDetail excRootCauseDetail = new ExceptionRootCauseDetail();
		excRootCauseDetail.setClassName("Exception Root Cause Test Class Name");
		excRootCauseDetail.setFileName("Exception Root Cause Test File Name");
		excRootCauseDetail.setMethodName("Exception Root Causel Test Method Name");
		excRootCauseDetail.setLineNumber(11);
		excRootCauseDetail.setExceptionRootCause(excRootCase);
		excRootCase.setExceptionRootCauseDetail(excRootCauseDetail);
		excRootCauseDetail.setUuid("te17942a-i014-1di5-a17w-1ma0d8307k05");
		assertNull(excRootCauseDetail.getId());
		
		ExceptionLog excLogSaved = service.saveExceptionLog(excLog);
		
		assertEquals("mf28662u-i183-4mw5-a15a-8gr9d5234r13", excLogSaved.getUuid());
		assertNotNull(excLog.getId());
		assertNotNull(excLogDetail.getId());
		assertNotNull(excRootCase.getId());
		assertNotNull(excRootCauseDetail.getId());
		assertEquals(excLogSaved.getUuid(), service.getExceptionLog(excLogSaved.getId()).getUuid());
		assertEquals(excLogDetail.getUuid(), service.getExceptionLog(excLogSaved.getId()).getExceptionLogDetail().getUuid());
		
		ExceptionRootCause excRootCauseSaved = service.getExceptionLog(excLogSaved.getId()).getExceptionRootCause();
		
		assertEquals(excRootCase.getUuid(), excRootCauseSaved.getUuid());
		assertEquals(excRootCauseDetail.getUuid(), excRootCauseSaved.getExceptionRootCauseDetail().getUuid());
	}
	
	/**
	 * @see {@link ExceptionLogService#getExceptionLogs(String, Date, Integer, Integer)}
	 */
	@Test
	@Verifies(value = "should return all exception logs by exception class and datetime", method = "getExceptionLogs(String, Date, Integer, Integer)")
	public void getExceptionLogs_shouldReturnAllExceptionLogsByClassAndDateTime() {
		List<ExceptionLog> excLogList = service.getExceptionLogs(null, null, 0, 10);
		assertEquals(excLogList.size(), 3);
		
		excLogList = service.getExceptionLogs("Exception Log Class2", null, 0, 10);
		assertEquals(excLogList.size(), 2);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = (Date) format.parse("2012-12-06 22:12:00.0");
		}
		catch (ParseException ex) {
			log.error("Cannot parse date", ex);
		}
		
		excLogList = service.getExceptionLogs(null, date, 0, 10);
		assertEquals(excLogList.size(), 1);
		
		excLogList = service.getExceptionLogs("Exception Log Class2", date, 0, 10);
		assertEquals(excLogList.size(), 1);
	}
	
	/**
	 * @see {@link ExceptionLogService#deleteExceptionLog())}
	 */
	@Test
	@Verifies(value = "should delete cascade exception log", method = "deleteExceptionLog()")
	public void deleteExceptionLog_shouldDeleteCascade() {
		ExceptionLog excLog = service.getExceptionLog(1);
		assertNotNull(excLog);
		ExceptionLogDetail excLogDetail = excLog.getExceptionLogDetail();
		assertNotNull(excLogDetail);
		ExceptionRootCause excRooCause = excLog.getExceptionRootCause();
		assertNotNull(excRooCause);
		ExceptionRootCauseDetail excRootCauseDetail = excRooCause.getExceptionRootCauseDetail();
		assertNotNull(excRootCauseDetail);
		
		service.deleteExceptionLog(excLog);
		
		assertNull(service.getExceptionLog(1));
		assertNull(service.getExceptionLog(1).getExceptionLogDetail());
		assertNull(service.getExceptionLog(1).getExceptionRootCause());
		assertNull(service.getExceptionLog(1).getExceptionRootCause().getExceptionRootCauseDetail());
	}
}
