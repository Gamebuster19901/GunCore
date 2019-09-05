/*
 * GunCore Copyright 2019 Gamebuster19901
 * 
 * All Rights Reserved.
 * 
 */

package com.gamebuster19901.guncore.exception;

import org.apache.commons.lang3.NotImplementedException;

@SuppressWarnings("serial")
public class NoImplementationProvidedException extends NotImplementedException{

	public NoImplementationProvidedException() {
		super("");
	}
	
    public NoImplementationProvidedException(String message) {
        super(message);
    }

    public NoImplementationProvidedException(Throwable cause) {
        super(cause);
    }

    public NoImplementationProvidedException(String message, final Throwable cause) {
        super(message, cause);
    }
	
}
