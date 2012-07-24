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
package org.openmrs.module.errorlogging.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.RequestContextFilter;

public class ExceptionLogRequestFilter extends RequestContextFilter {
	
	protected final static ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>();
	
	/**
	 * @see
	 * org.springframework.web.filter.RequestContextFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
		requests.set(request);
		try {
			super.doFilterInternal(request, response, filterChain);
		}
		finally {
			requests.remove();
		}
	}
	
	/**
	 * Gets the request object for the calling thread
	 *
	 * @return the request object
	 */
	public static HttpServletRequest getRequest() {
		return requests.get();
	}
}
