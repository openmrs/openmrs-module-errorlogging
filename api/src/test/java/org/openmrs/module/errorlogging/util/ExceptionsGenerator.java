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

import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;

/**
 * Class that uses for testing ExceptionUtil in ExcetionUtilTest. 
 * This class generates exceptions
 */
public class ExceptionsGenerator {
	
	private final GeneratorService generatorService = new GeneratorService();
	
	public void alpha() {
		beta();
	}
	
	private void beta() {
		gamma();
	}
	
	private void gamma() {
		try {
			generatorService.delta();
		}
		catch (Exception e) {
			throw new APIException("Sorry, try again later", e);
		}
	}
	
	public class InnerClass {
		
		public void alphaInner() {
			try {
				generatorService.delta();
			}
			catch (Exception e) {
				throw new APIException("Sorry, try again later", e);
			}
		}
	}
}

class GeneratorService {
	
	private final GeneratorDAO generatorDao = new GeneratorDAO();
	
	public void delta() {
		epsilon();
	}
	
	private void epsilon() {
		zeta();
	}
	
	private void zeta() {
		try {
			generatorDao.eta();
		}
		catch (Exception e) {
			throw new APIException("Unable to save order", e);
		}
	}
}

class GeneratorDAO {
	
	public void eta() {
		theta();
	}
	
	private void theta() {
		iota();
	}
	
	public void iota() {
		try {
			throw new DAOException("Omega server not available");
		}
		catch (Exception e) {
			throw new DAOException("Database problem", e);
		}
	}
}
