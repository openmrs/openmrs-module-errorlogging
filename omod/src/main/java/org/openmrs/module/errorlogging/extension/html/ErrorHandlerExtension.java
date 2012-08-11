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
package org.openmrs.module.errorlogging.extension.html;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.openmrs.ImplementationId;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.Extension;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.ExceptionLogService;
import org.openmrs.module.errorlogging.util.ExceptionLogUtil;
import org.openmrs.module.errorlogging.web.filter.ExceptionLogRequestFilter;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebConstants;

public class ErrorHandlerExtension extends Extension {
	
	@Override
	public MEDIA_TYPE getMediaType() {
		return MEDIA_TYPE.html;
	}
	
	@Override
	public String getOverrideContent(String bodyContent) {
		HttpServletRequest request = ExceptionLogRequestFilter.getRequest();
		if (request != null) {
			Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
			if (exception != null && !ExceptionLogUtil.isIgnoredException(exception)) {
				ExceptionLog excLog = ExceptionLogUtil.parseException(exception);
				if (excLog != null) {
					ExceptionLogService service = Context.getService(ExceptionLogService.class);
					service.saveExceptionLog(excLog);
				}
				return createReportBtn(exception, request);
			}
		}
		return "";
	}
	
	private String createReportBtn(Exception exception, HttpServletRequest request) {
		UserContext userContext = (UserContext) request.getSession().getAttribute(
		    WebConstants.OPENMRS_USER_CONTEXT_HTTPSESSION_ATTR);
		if (userContext != null || userContext.getAuthenticatedUser() != null) {
			String reportBugUrl = Context.getAdministrationService().getGlobalProperty(
			    OpenmrsConstants.GLOBAL_PROPERTY_REPORT_BUG_URL);
			String openmrsVersion = OpenmrsConstants.OPENMRS_VERSION;
			String serverInfo = request.getSession().getServletContext().getServerInfo();
			String username = Context.getAuthenticatedUser().getUsername();
			if (StringUtils.isBlank(username)) {
				username = Context.getAuthenticatedUser().getSystemId();
			}
			ImplementationId id = Context.getAdministrationService().getImplementationId();
			String implementationId = "";
			if (id != null) {
				implementationId = id.getImplementationId();
				implementationId += " = " + id.getName();
			}
			StringBuilder sb = new StringBuilder();
			boolean isFirst = true;
			for (Module module : ModuleFactory.getStartedModules()) {
				if (isFirst) {
					sb.append(module.getModuleId()).append(" v").append(module.getVersion());
					isFirst = false;
				} else
					sb.append(", ").append(module.getModuleId()).append(" v").append(module.getVersion());
			}
			String startedModules = sb.toString();
			String errorMessage = exception.toString();
			StackTraceElement[] elements;
			
			if (exception instanceof ServletException) {
				// It's a ServletException: we should extract the root cause
				ServletException sEx = (ServletException) exception;
				Throwable rootCause = sEx.getRootCause();
				if (rootCause == null)
					rootCause = sEx;
				elements = rootCause.getStackTrace();
			} else {
				// It's not a ServletException, so we'll just show it
				elements = exception.getStackTrace();
			}
			// Collect stack trace for reporting bug description
			StringBuilder description = new StringBuilder("Stack trace:\n");
			for (StackTraceElement element : elements) {
				description.append(element).append("\n");
			}
			String stackTrace = OpenmrsUtil.shortenedStackTrace(description.toString());
			String reportBtn = "<form action=\"" + reportBugUrl + "\" target=\"_blank\" method=\"POST\">"
			        + "<input type=\"hidden\" name=\"openmrs_version\" value=\"" + openmrsVersion + "\" />"
			        + "<input type=\"hidden\" name=\"server_info\" value=\"" + serverInfo + "\" />"
			        + "<input type=\"hidden\" name=\"username\" value=\"" + username + "\" />"
			        + "<input type=\"hidden\" name=\"implementationId\" value=\"" + implementationId + "\" />"
			        + "<input type=\"hidden\" name=\"startedModules\" value=\"" + startedModules + "\" />"
			        + "<input type=\"hidden\" name=\"errorMessage\" value=\"" + errorMessage + "\" />"
			        + "<input type=\"hidden\" name=\"stackTrace\" value=\"" + stackTrace + "\" />"
			        + "<br/><input type=\"submit\" value=\"Report Problem\"></form>";
			return reportBtn;
		}
		return "";
	}
}
