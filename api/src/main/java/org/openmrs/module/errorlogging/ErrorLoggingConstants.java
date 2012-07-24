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

public class ErrorLoggingConstants {
	
	public static final String ERRROR_LOGGING_GP_IGNORED_EXCEPTION = "errorlogging.ignore.errors";
	
	public static final String[] ERRROR_LOGGING_DEDAULT_IGNORED_EXCEPTION = new String[] {
	        "org.openmrs.api.APIAuthenticationException", "org.openmrs.api.context.ContextAuthenticationException" };
}
