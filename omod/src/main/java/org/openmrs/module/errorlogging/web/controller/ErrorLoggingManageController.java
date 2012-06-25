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
package org.openmrs.module.errorlogging.web.controller;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.ExceptionLogDetail;
import org.openmrs.module.errorlogging.api.ExceptionLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The main controller.
 */
@Controller
public class ErrorLoggingManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@ModelAttribute("exceptionLogList")
	public List<ExceptionLog> getExceptionLogs() {
		ExceptionLogService service = Context.getService(ExceptionLogService.class);
		service.getExceptionLogs(null, null, 0, 10);
		return service.getExceptionLogs(null, null, 0, 10);
	}
	
	@ModelAttribute("exceptionLogDetail")
	public ExceptionLogDetail getExceptionLogDetail() {
		ExceptionLogService service = Context.getService(ExceptionLogService.class);
		service.getExceptionLogs(null, null, 0, 10);
		List<ExceptionLog> excList = service.getExceptionLogs(null, null, 0, 10);
		if (excList != null) {
			return excList.get(0).getExceptionLogDetail();
		} else {
			return null;
		}
	}
	
	@RequestMapping(value = "/module/errorlogging/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
	@RequestMapping(value = "module/errorlogging/viewErrors", method = RequestMethod.GET)
	public void showForm() {
		
	}
}
