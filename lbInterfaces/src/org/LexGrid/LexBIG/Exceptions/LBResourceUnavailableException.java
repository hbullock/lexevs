/*
 * Copyright: (c) 2004-2010 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 */
package org.LexGrid.LexBIG.Exceptions;

/**
 * Thrown when a resource required by the requested LexBIG operation
 * cannot be located or resolved.
 * 
 */
public class LBResourceUnavailableException extends LBException {
	private static final long serialVersionUID = -690663064337397771L;

	public LBResourceUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public LBResourceUnavailableException(String message) {
		super(message);
	}

}