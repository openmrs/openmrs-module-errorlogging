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

import javax.servlet.http.HttpServletRequest;
import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;
import org.openmrs.module.Extension.MEDIA_TYPE;
import org.openmrs.module.errorlogging.ExceptionLog;
import org.openmrs.module.errorlogging.api.ExceptionLogService;
import org.openmrs.module.errorlogging.util.ExceptionLogUtil;
import org.openmrs.module.errorlogging.web.filter.ExceptionLogRequestFilter;

public class UncaughtExceptionExtension extends Extension {
	
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
			}
		}
		return "";
	}
}
